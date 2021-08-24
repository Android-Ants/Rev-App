package com.example.galleryapp.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.galleryapp.App;
import com.example.galleryapp.FetchData;
import com.example.galleryapp.ImagesViewModel;
import com.example.galleryapp.R;
import com.example.galleryapp.databinding.ActivityMainBinding;
import com.example.galleryapp.fragments.FavoritesFragment;
import com.example.galleryapp.fragments.FoldersFragment;
import com.example.galleryapp.fragments.HomeFragment;
import com.example.galleryapp.fragments.RecentFragment;
import com.example.galleryapp.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


        String date = getCurrentDateAndTime();



        this.imagesViewModel = new ViewModelProvider(this).get(ImagesViewModel.class);

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