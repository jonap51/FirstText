package com.app.scilicet.movble;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

@SuppressWarnings("ALL")
public class StatusViewpagerAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

     StatusViewpagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

         switch (position){
             case 0:
                 return new EncursoFragment();

             case 1:
                 return new AceptadosFragment();
                 default:
                     return null;
         }
    }

    @Override
    public int getCount() {
            return numOfTabs;
    }

}

