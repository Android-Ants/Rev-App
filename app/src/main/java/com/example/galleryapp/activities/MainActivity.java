package com.example.galleryapp.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.galleryapp.ApiCalls;
import com.example.galleryapp.App;
import com.example.galleryapp.FetchData;
import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.R;
import com.example.galleryapp.classes.FireBaseCount;
import com.example.galleryapp.classes.ParentFireBase;
import com.example.galleryapp.databinding.ActivityMainBinding;
import com.example.galleryapp.fragments.FavoritesFragment;
import com.example.galleryapp.fragments.FoldersFragment;
import com.example.galleryapp.fragments.HomeFragment;
import com.example.galleryapp.fragments.RecentFragment;
import com.example.galleryapp.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseReference databaseReference, databaseReference2;
    private List<String> parentId = new ArrayList<>();
    private List<FireBaseCount> fireBaseCounts = new ArrayList<>();
    private ArrayList<FireBaseCount> baseCounts = new ArrayList<>();
    private RequestQueue queue;
    private final String TAG = "Drive";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;

    private ApiCalls apiCalls;
    private ImagesViewModel imagesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setContext(MainActivity.this);

        Paper.init(getApplicationContext());


        String date = getCurrentDateAndTime();



        this.imagesViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);

        apiCalls = new ApiCalls(MainActivity.this);
        apiCalls.get_bearer_token();
        sharedPreferences = getSharedPreferences("Drive", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("firstLogin", "yes").equalsIgnoreCase("yes")) {
            editor.putString("firstLogin", "no").commit();
            editor.putString("date",date).commit();
            FetchData fetchData = new FetchData(this);
            fetchData.fetchingAllPhotos();
        }else if(sharedPreferences.getString("firstLogin", "yes").equalsIgnoreCase("no")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(MainActivity.this))
                    .commit();
            editor.putInt("Fragment Id",R.id.navigation_home).commit();
            databaseReference = FirebaseDatabase.getInstance().getReference("Photos");
            databaseReference2 = FirebaseDatabase.getInstance().getReference("Parent");
            databaseReference.removeValue();
            databaseReference2.removeValue();
        }


        Log.d("hhhhhhhhhh", sharedPreferences.getString("fetch", ""));
        binding.bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.navigation_favourite:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new FavoritesFragment(MainActivity.this))
                                .commit();
                        editor.putInt("FragmentId", R.id.navigation_favourite).commit();
                        break;
                    case R.id.navigation_recent:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new RecentFragment(MainActivity.this))
                                .commit();

                        editor.putInt("FragmentId", R.id.navigation_recent).commit();
                        break;
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment(MainActivity.this))
                                .commit();
                        editor.putInt("FragmentId", R.id.navigation_home).commit();
                        break;
                    case R.id.navigation_files:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new FoldersFragment(MainActivity.this, getLayoutInflater(), getApplication()))
                                .commit();
                        editor.putInt("FragmentId", R.id.navigation_files).commit();
                        break;
                    case R.id.navigation_setting:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new SettingsFragment(MainActivity.this, parentId.size()))
                                .commit();
                        editor.putInt("FragmentId", R.id.navigation_setting).commit();
                        break;
                }
                return true;
            }
        });

    }

    private void fetchingAllPhotos() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Fetching all photos from drive");
        progressDialog.setMessage("This May take some time but will occur only once .");
        progressDialog.show();

        queue = Volley.newRequestQueue(MainActivity.this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Photos");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Parent");

        pngFileSearch();
    }

    private void uploadToFirebase(FireBaseCount fireBaseCount) {

        if (!sharedPreferences.getString("clear", "").equalsIgnoreCase("done")) {
            databaseReference.removeValue();
            editor.putString("clear", "done");
            editor.commit();
        }


        databaseReference.child(fireBaseCount.getId()).setValue(fireBaseCount);
        if (!parentId.contains(fireBaseCount.getParentsId()))
            parentId.add(fireBaseCount.getParentsId());

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
                                Log.d("jjjjjjjjjjj", String.valueOf(0));
                                Log.d("jjjjjjjjjj", baseCounts.get(0).getId());


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
                            progressDialog.dismiss();
                            for (FireBaseCount f : baseCounts) {
                                Paper.book("ImagesAll").write(f.getId(), f);
                            }
                            //Paper.book("ImagesAll").write("images",baseCounts);
                            Toast.makeText(MainActivity.this, "Profile fetching completed", Toast.LENGTH_SHORT).show();

                            Thread.sleep(1000);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, new HomeFragment(MainActivity.this))
                                    .commit();
                        } catch (JSONException | InterruptedException e) {

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

    public void parents_profile_fetch() {
        for (int i = 0; i < parentId.size(); i++) {
            ParentFireBase parentFireBase = new ParentFireBase();
            parentFireBase.setParentId(parentId.get(i));
            get_parent_name(parentFireBase);
            Log.d("jjjjjjjjjjj", String.valueOf(i));
            Log.d("jjjjjjjjjj", baseCounts.get(i).getName());
        }
    }

    public void get_parent_name(ParentFireBase parentFireBase) {
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

    public void update_on_firebase(ParentFireBase parentFireBase) {
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
            Toast.makeText(MainActivity.this, "Profile fetching completed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int FragmentId = sharedPreferences.getInt("FragmentId", 0);
        switch (FragmentId) {
            case R.id.navigation_favourite:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FavoritesFragment(MainActivity.this))
                        .commit();
                binding.bottomNavigationView.setSelectedItemId(FragmentId);
                break;
            case R.id.navigation_recent:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new RecentFragment(MainActivity.this))
                        .commit();
                binding.bottomNavigationView.setSelectedItemId(FragmentId);
                break;
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment(MainActivity.this))
                        .commit();
                binding.bottomNavigationView.setSelectedItemId(FragmentId);
                break;
            case R.id.navigation_files:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new FoldersFragment(MainActivity.this, getLayoutInflater(), getApplication()))
                        .commit();
                binding.bottomNavigationView.setSelectedItemId(FragmentId);
                break;
            case R.id.navigation_setting:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SettingsFragment(MainActivity.this, parentId.size()))
                        .commit();
                binding.bottomNavigationView.setSelectedItemId(FragmentId);
                break;
        }
    }

    public static String getCurrentDateAndTime(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c);
        return formattedDate;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
//        editor.putString("fetch", "yes");
//        editor.putString("clear", "");
//        editor.putInt("FragmentId", 0);
//        editor.commit();

    }
}