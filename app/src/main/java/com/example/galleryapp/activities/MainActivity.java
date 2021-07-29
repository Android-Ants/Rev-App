package com.example.galleryapp.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.galleryapp.R;
import com.example.galleryapp.databinding.ActivityMainBinding;
import com.example.galleryapp.fragments.FavoritesFragment;
import com.example.galleryapp.fragments.FoldersFragment;
import com.example.galleryapp.fragments.HomeFragment;
import com.example.galleryapp.fragments.RecentFragment;
import com.example.galleryapp.fragments.SettingsFragment;
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
                                .replace(R.id.fragment_container, new FoldersFragment())
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



// the usage of randomize

//    ArrayList<ModelImage> fake = new ArrayList<>();
//        fake.add(new ModelImage("123","fake1","path1"));
//                fake.add(new ModelImage("124","fake2"));
//                fake.add(new ModelImage("125","fake3"));
//                fake.add(new ModelImage("126","fake4"));
//                fake.add(new ModelImage("127","fake5"));
//                fake.add(new ModelImage("128","fake6"));
//                fake.add(new ModelImage("1274","fake7"));
//
//        Randomize obj = new Randomize(fake);
//        obj.randomize();
//        for(ModelImage str : fake){
//            System.out.println(str.getName());
//        }
//
//        ArrayList<ModelImage> randomList = obj.getRandomized();
//        for(ModelImage str : randomList){
//            System.out.println(str.getName());
//        }
//        ArrayList<ModelImage> randomList2 = obj.getRandomized(fake);
//        for(ModelImage str : randomList2){
//            System.out.println(str.getName());
//        }
//        ArrayList<ModelImage> randomList3 = obj.getPrevRandomized();
//        for(ModelImage str : randomList3){
//            System.out.println(str.getName());
//        }
