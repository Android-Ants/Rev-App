package com.example.galleryapp.trial;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.galleryapp.databinding.ActivitySignInActivityBinding;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // binding.webView.loadUrl("https://github.com/ammarptn/GDrive-Rest-Android/blob/master/app/src/main/java/com/ammarptn/gdriverest/MainActivity.java");
//        binding.webView.getSettings().setUserAgentString("Chrome/56.0.0 Mobile");
//        binding.webView.getSettings().setJavaScriptEnabled(true);
//        binding.webView.loadUrl("https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=841525552075-lmtdplark7r3m4t69pocukvjs6bgeeau.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&state=foobar&scope=https://www.googleapis.com/auth/drive");
//        binding.webView.setWebViewClient(new WebViewClient(){
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//        });

    }
}