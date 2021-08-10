package com.example.galleryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.galleryapp.classes.Folder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiCalls {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String TAG = "Drive";
    private RequestQueue queue;
    private List<Folder> folderList = new ArrayList<>();

    public ApiCalls( Context context )
    {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences("Drive", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        queue = Volley.newRequestQueue(context);
    }

    public List<Folder> get_files_list ()
    {
        StringRequest request = new StringRequest(Request.Method.GET, "https://www.googleapis.com/drive/v3/files", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("files");

                    for ( int i =0 ; i < jsonArray.length();i++ )
                    {
                        Folder folder = new Folder(jsonArray.getJSONObject(i).get("kind").toString(),
                                jsonArray.getJSONObject(i).get("id").toString(),jsonArray.getJSONObject(i).get("name").toString(),
                                jsonArray.getJSONObject(i).get("mimeType").toString()) ;
                        folderList.add(folder);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d(TAG,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();
                map.put("Authorization","Bearer " + sharedPreferences.getString("bearer token",""));
                return map;
            }
        };
        queue.add(request);
        return folderList;
    }

    public void get_bearer_token ()
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

}
