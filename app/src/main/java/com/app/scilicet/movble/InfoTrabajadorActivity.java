package com.app.scilicet.movble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class InfoTrabajadorActivity extends AppCompatActivity {

    AppBarLayout Appbar;
    CollapsingToolbarLayout CoolToolbar;
    Toolbar toolbar;

    boolean ExpandedActionBar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_trabajador);

        Appbar = (AppBarLayout)findViewById(R.id.appbar);
        CoolToolbar = (CollapsingToolbarLayout)findViewById(R.id.ctolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset) > 600){
                    ExpandedActionBar = false;
                    invalidateOptionsMenu();
                } else {
                    ExpandedActionBar = true;
                    CoolToolbar.setTitle("");
                    invalidateOptionsMenu();
                }

            }
        });



    }
}
