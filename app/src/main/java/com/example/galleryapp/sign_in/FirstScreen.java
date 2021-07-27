package com.example.galleryapp.sign_in;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.galleryapp.R;
import com.example.galleryapp.databinding.ActivityFirstScreenBinding;

public class FirstScreen extends AppCompatActivity implements View.OnClickListener {

    private ActivityFirstScreenBinding binding;
    private Boolean isDone = false;

    @Override
    protected void onResume() {
        super.onResume();

        if ( isDone )
        {
            Intent intent = new Intent(this,SecondScreen.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.signInButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.sign_in_button:

                String url = "https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=841525552075-lmtdplark7r3m4t69pocukvjs6bgeeau.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&state=foobar&scope=https://www.googleapis.com/auth/drive";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    startActivity(i);
                    isDone = true;
                } catch (ActivityNotFoundException e) {
                    // Chrome is probably not installed
                    // Try with the default browser
                    i.setPackage(null);
                    startActivity(i);
                }

                break;
        }

    }
}