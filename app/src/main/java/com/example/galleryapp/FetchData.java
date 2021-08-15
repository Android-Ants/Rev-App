package com.example.galleryapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.classes.ParentFireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class FetchData {

    private DatabaseReference databaseReference, databaseReference2;
    private List<String> parentId = new ArrayList<>();
    private List<FireBaseCount> fireBaseCounts = new ArrayList<>();
    private ArrayList<FireBaseCount> baseCounts = new ArrayList<>();
    private RequestQueue queue;
    private final String TAG = "Drive";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    private Context context;

    public FetchData ( Context context )
    {
        Paper.init(context);
        this.context = context;
        queue = Volley.newRequestQueue(context);
        databaseReference = FirebaseDatabase.getInstance().getReference("Photos");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Parent");
        sharedPreferences = context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void fetchingAllPhotos(  ) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Fetching all photos from drive");
        progressDialog.setMessage("This May take some time but will occur only once .");
        progressDialog.show();
        databaseReference.removeValue();
        get_bearer_token();
    }


    private void get_bearer_token ()
    {
        StringRequest request = new StringRequest(Request.Method.POST, "https://oauth2.googleapis.com/token", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    editor.putString("bearer token" ,jsonObject.get("access_token").toString());
                    editor.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,response);
                pngFileSearch();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        })
        {

            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();
                map.put("client_id",context.getString(R.string.client_id));
                map.put("grant_type","refresh_token");
                map.put("refresh_token",sharedPreferences.getString("refresh token",""));
                return map;
            }
        };
        queue.add(request);
    }

    private void pngFileSearch() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://www.googleapis.com/drive/v3/files?fields=kind,incompleteSearch,nextPageToken, files(id, name,webContentLink,parents)&q=mimeType='image/png'",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("files");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                FireBaseCount fireBaseCount = new FireBaseCount();
                                fireBaseCount.setId(jsonArray.getJSONObject(i).get("id").toString());
                                fireBaseCount.setName(jsonArray.getJSONObject(i).get("name").toString());

                                fireBaseCount.setUrl(jsonArray.getJSONObject(i).get("webContentLink").toString());
                                try {
                                    String str = jsonArray.getJSONObject(i).get("parents").toString();
                                    str = str.replace("[", "");
                                    str = str.replace("]", "");
                                    str = str.replace("\\", "");
                                    str = str.replace("\"", "");
                                    fireBaseCount.setParentsId(str);
                                } catch (JSONException e) {
                                    fireBaseCount.setParentsId("drive");
                                }

                                baseCounts.add(fireBaseCount);

                                uploadToFirebase(fireBaseCount);
                            }

                            jpgFileSearch();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + sharedPreferences.getString("bearer token", ""));
                return map;
            }
        };
        queue.add(request);
    }

    private void jpgFileSearch() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://www.googleapis.com/drive/v3/files?fields=kind,incompleteSearch,nextPageToken, files(id, name,webContentLink,parents)&q=mimeType='image/jpg'",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("files");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                FireBaseCount fireBaseCount = new FireBaseCount();
                                fireBaseCount.setId(jsonArray.getJSONObject(i).get("id").toString());
                                fireBaseCount.setName(jsonArray.getJSONObject(i).get("name").toString());

                                fireBaseCount.setUrl(jsonArray.getJSONObject(i).get("webContentLink").toString());
                                try {
                                    String str = jsonArray.getJSONObject(i).get("parents").toString();
                                    str = str.replace("[", "");
                                    str = str.replace("]", "");
                                    str = str.replace("\\", "");
                                    str = str.replace("\"", "");
                                    fireBaseCount.setParentsId(str);
                                } catch (JSONException e) {
                                    fireBaseCount.setParentsId("drive");
                                }

                                baseCounts.add(fireBaseCount);

                                uploadToFirebase(fireBaseCount);
                            }
                            jpegFileSearch();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + sharedPreferences.getString("bearer token", ""));
                return map;
            }
        };
        queue.add(request);
    }

    private void jpegFileSearch() {

        StringRequest request = new StringRequest(Request.Method.GET, "https://www.googleapis.com/drive/v3/files?fields=kind,incompleteSearch,nextPageToken, files(id, name,webContentLink,parents)&q=mimeType='image/jpeg'",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("files");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                FireBaseCount fireBaseCount = new FireBaseCount();
                                fireBaseCount.setId(jsonArray.getJSONObject(i).get("id").toString());
                                fireBaseCount.setName(jsonArray.getJSONObject(i).get("name").toString());

                                fireBaseCount.setUrl(jsonArray.getJSONObject(i).get("webContentLink").toString());
                                try {
                                    String str = jsonArray.getJSONObject(i).get("parents").toString();
                                    str = str.replace("[", "");
                                    str = str.replace("]", "");
                                    str = str.replace("\\", "");
                                    str = str.replace("\"", "");
                                    fireBaseCount.setParentsId(str);
                                } catch (JSONException e) {
                                    fireBaseCount.setParentsId("drive");
                                }
                                baseCounts.add(fireBaseCount);
                                Log.d("jjjjjjjjjjj", String.valueOf(0));
                                Log.d("jjjjjjjjjj", baseCounts.get(0).getId());
                                uploadToFirebase(fireBaseCount);
                            }
                            parents_profile_fetch();
                            //progressDialog.dismiss();
                            for (FireBaseCount f : baseCounts) {
                                Paper.book("ImagesAll").write(f.getId(), f);
                            }
                            Paper.book("ImagesAll").write("images",baseCounts);
//                            Toast.makeText(MainActivity.this, "Profile fetching completed", Toast.LENGTH_SHORT).show();
//
//                            Thread.sleep(1000);
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragment_container, new HomeFragment(MainActivity.this))
//                                    .commit();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + sharedPreferences.getString("bearer token", ""));
                return map;
            }
        };
        queue.add(request);
    }

    private void uploadToFirebase(FireBaseCount fireBaseCount) {

        databaseReference.child(fireBaseCount.getId()).setValue(fireBaseCount);
        if (!parentId.contains(fireBaseCount.getParentsId()))
            parentId.add(fireBaseCount.getParentsId());

    }

    private void parents_profile_fetch() {
        for (int i = 0; i < parentId.size(); i++) {
            ParentFireBase parentFireBase = new ParentFireBase();
            parentFireBase.setParentId(parentId.get(i));
            get_parent_name(parentFireBase);
            Log.d("jjjjjjjjjjj", String.valueOf(i));
            Log.d("jjjjjjjjjj", baseCounts.get(i).getName());
        }
    }

    private void get_parent_name(ParentFireBase parentFireBase) {
        String url = "https://www.googleapis.com/drive/v3/files/" + parentFireBase.getParentId();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    parentFireBase.setName(jsonObject.get("name").toString());
                    update_on_firebase(parentFireBase);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + sharedPreferences.getString("bearer token", ""));
                return map;
            }
        };
        queue.add(request);
    }

    private void update_on_firebase(ParentFireBase parentFireBase) {
        for (int i = 0; i < baseCounts.size(); i++) {
            if (baseCounts.get(i).getParentsId().equalsIgnoreCase(parentFireBase.getParentId())) {
                fireBaseCounts.add(baseCounts.get(i));
            }
        }
        parentFireBase.setChilds(fireBaseCounts);
        Paper.book("Folders").write(parentFireBase.getParentId(), parentFireBase);
        if (!sharedPreferences.contains(parentFireBase.getParentId())) {
            editor.putBoolean(parentFireBase.getParentId(), parentFireBase.getBlocked());
            editor.commit();
        }
        databaseReference2.child(parentFireBase.getParentId()).setValue(parentFireBase);
        fireBaseCounts.clear();
        if (parentFireBase.getParentId().equalsIgnoreCase(parentId.get(parentId.size() - 1))) {
            progressDialog.dismiss();
            Toast.makeText(context, "Profile fetching completed", Toast.LENGTH_SHORT).show();
        }
    }


}
