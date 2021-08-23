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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<String> parentId = new ArrayList<>();
    private final String TAG = "Drive";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private ImagesViewModel imagesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        App.setContext(MainActivity.this);

        Paper.init(getApplicationContext());

        this.imagesViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);

        sharedPreferences = getSharedPreferences("Drive", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("firstLogin", "yes").equalsIgnoreCase("yes")) {
            editor.putString("firstLogin", "no");
            FetchData fetchData = new FetchData(this);
            fetchData.fetchingAllPhotos();
            editor.commit();
        }

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
}