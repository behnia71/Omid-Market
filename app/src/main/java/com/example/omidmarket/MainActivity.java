package com.example.omidmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    FrameLayout fragment_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        fragment_container = findViewById(R.id.fragment_container);

        bottom_navigation.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , new HomeFragment()).commit();
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.account:
                        selectedFragment = new AccountFragment();
                        break;
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.Cart:
                        selectedFragment = new CartFragment();
                        break;
                    case R.id.category:
                        selectedFragment = new CategoryFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container , selectedFragment).commit();

                return true;
            }
        });


    }
}