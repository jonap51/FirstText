package com.app.scilicet.movble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        List<Fragment> list= new ArrayList<>();
        list.add(new RegistroUnoFragment());
        list.add(new RegistroDosFragment());
        list.add(new RegistroTresFragment());

        ViewPager pager = findViewById(R.id.viewPager);


        PagerAdapter pagerAdapter = new RegisterSliderAdapter(getSupportFragmentManager(), 3, list);

        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager, true);

    }
}