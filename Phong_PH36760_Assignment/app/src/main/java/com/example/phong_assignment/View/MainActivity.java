package com.example.phong_assignment.View;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.phong_assignment.R;
import com.example.phong_assignment.View.Fragment.FragmentBill;
import com.example.phong_assignment.View.Fragment.FragmentCart;
import com.example.phong_assignment.View.Fragment.FragmentFavorite;
import com.example.phong_assignment.View.Fragment.FragmentHome;
import com.example.phong_assignment.databinding.ActivityMain2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frg_main, new FragmentHome()).commit();
        }


        BottomNavigationView bottomNavigationView = binding.bottomNavigation;
        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.item_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frg_main, new FragmentHome()).commit();
                    //fragment home
                    return true;
                } else if (id == R.id.item_cart) {
                    //fragment cart
                    getSupportFragmentManager().beginTransaction().replace(R.id.frg_main, new FragmentCart()).commit();
                    return true;
                } else if (id == R.id.item_bill) {
                    //fragment menu
                    getSupportFragmentManager().beginTransaction().replace(R.id.frg_main, new FragmentBill()).commit();
                    return true;
                } else if (id == R.id.item_favorite) {
                    //fragment favorite
                    getSupportFragmentManager().beginTransaction().replace(R.id.frg_main, new FragmentFavorite()).commit();
                    return true;
                }
                return false;
            }
        });
    }
}