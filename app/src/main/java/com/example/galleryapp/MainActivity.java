package com.example.galleryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.galleryapp.databinding.ActivityMainBinding;
import com.example.galleryapp.fragments.FavoritesFragment;
import com.example.galleryapp.fragments.HomeFragment;
import com.example.galleryapp.fragments.RecentFragment;
import com.example.galleryapp.fragments.SettingsFragment;
import com.example.galleryapp.fragments.SlideShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_favourite:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new FavoritesFragment())
                                .commit();
                        break;
                    case R.id.navigation_recent:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new RecentFragment())
                                .commit();
                        break;
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment())
                                .commit();
                        break;
                    case R.id.navigation_slide_show:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new SlideShowFragment())
                                .commit();
                        break;
                    case R.id.navigation_setting:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new SettingsFragment())
                                .commit();
                        break;
                }

                return true;
            }
        });

    }
}