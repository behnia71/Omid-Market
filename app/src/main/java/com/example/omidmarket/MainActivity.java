package com.example.omidmarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    FrameLayout fragment_container;
    Toolbar myToolbar;
    EditText editText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        editText1 = findViewById(R.id.editText1);




        bottom_navigation = findViewById(R.id.bottom_navigation);
        fragment_container = findViewById(R.id.fragment_container);

        bottom_navigation.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.account:
                        SharedPreferences sharedPreferences = getSharedPreferences("login_signUp", MODE_PRIVATE);
                        boolean sign_up = sharedPreferences.getBoolean("sign_up", false);
                        boolean login = sharedPreferences.getBoolean("login", false);
                        if(sign_up == true || login == true){
                            selectedFragment = new UserFragment();
                        }else {
                            selectedFragment = new AccountFragment();
                        }
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