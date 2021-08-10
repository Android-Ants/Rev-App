package com.example.galleryapp.sign_in;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.galleryapp.ApiCalls;
import com.example.galleryapp.R;
import com.example.galleryapp.activities.MainActivity;
import com.example.galleryapp.databinding.ActivitySecondScreenBinding;
import com.example.galleryapp.models.SignInStateModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class SecondScreen extends AppCompatActivity implements View.OnClickListener {

    private ActivitySecondScreenBinding binding;
    private String TAG = "Drive";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signIn.setOnClickListener(this::onClick);

        sharedPreferences = getSharedPreferences("Drive",MODE_PRIVATE);
        editor = sharedPreferences.edit();
//TODO comment this out before commit
        Paper.init(this);
        String code = Paper.book().read(SignInStateModel.code);
        if(code!=null){
            ApiCalls obj = new ApiCalls(SecondScreen.this);
            obj.get_bearer_token();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            //ApiCalls.get_bearer_token();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.sign_in:

                if (binding.editText.getText().toString().isEmpty())
                {
                    binding.editText.setError("Enter the code");
                }
                else
                {
                    String code = binding.editText.getText().toString();
                    Paper.book().write(SignInStateModel.code,code);
                    get_bearer_token(code);
                }

                break;

        }
    }

    public void get_bearer_token( String code )
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, "https://accounts.google.com/o/oauth2/token", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Log.d(TAG,response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    editor.putString("bearer token" ,jsonObject.get("access_token").toString());
                    editor.putString("refresh token" ,jsonObject.get("refresh_token").toString());
                    editor.commit();

                    Intent intent = new Intent(SecondScreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
//                    ApiCalls apiCalls = new ApiCalls(SecondScreen.this);
//                    List<File> fileList =  apiCalls.get_files_list();
//                    Log.d(TAG,String.valueOf(fileList.size()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        })
        {

            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String, String>();
                map.put("code",code);
                map.put("client_id",getString(R.string.client_id));
                map.put("redirect_uri", getString(R.string.redirect_uri));
                map.put("grant_type", "authorization_code");
                return map;
            }
        };
        queue.add(request);

    }

}