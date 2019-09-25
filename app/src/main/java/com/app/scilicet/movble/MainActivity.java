package com.app.scilicet.movble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btnNav;
    private HomeFragment homeFragment;
    private StatusFragment statusFragment;
    private PerfilFragment perfilFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        statusFragment = new StatusFragment();
        perfilFragment = new PerfilFragment();

        setFragment(homeFragment);

        btnNav = findViewById(R.id.bottom_navigation);
        btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.idHome:
                        setFragment(homeFragment);
                        return true;

                    case R.id.idPerfil:
                        setFragment(perfilFragment);
                        return true;

                    case R.id.idStatus:
                        setFragment(statusFragment);
                        return true;
                        default:
                            return false;

                }
            }
        });
    }

    private void setFragment(Fragment fragment ) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

}