package com.example.galleryapp.sign_in;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.galleryapp.R;
import com.example.galleryapp.activities.MainActivity;
import com.example.galleryapp.databinding.ActivitySecondScreenBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SecondScreen extends AppCompatActivity implements View.OnClickListener {

    private ActivitySecondScreenBinding binding;
    private String TAG = "Drive";
    ClipboardManager clipboardManager;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupUI(binding.getRoot());

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        binding.signIn.setOnClickListener(this::onClick);

        sharedPreferences = getSharedPreferences("Drive", MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.signIn:

                if(binding.signIn.getText().toString().equalsIgnoreCase("paste"))
                {
                    ClipData clipData = clipboardManager.getPrimaryClip();
                    binding.editText.setText(clipData.getItemAt(0).getText().toString());
                    binding.signIn.setText("Submit");

                }else if(binding.signIn.getText().toString().equalsIgnoreCase("submit"))
                {
                    if (binding.editText.getText().toString().isEmpty()) {
                        binding.editText.setError("Enter the code");
                    } else {
                        String code = binding.editText.getText().toString();

                        get_bearer_token(code);
                    }
                }

                break;

        }
    }

    public void get_bearer_token(String code) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, "https://accounts.google.com/o/oauth2/token", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Log.d(TAG, response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    editor.putString("bearer token", jsonObject.get("access_token").toString());
                    editor.putString("refresh token", jsonObject.get("refresh_token").toString());
                    editor.commit();

                    Intent intent = new Intent(SecondScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Nullable
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("code", code);
                map.put("client_id", getString(R.string.client_id));
                map.put("redirect_uri", getString(R.string.redirect_uri));
                map.put("grant_type", "authorization_code");
                return map;
            }
        };
        queue.add(request);

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(SecondScreen.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

}