package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.fragments.AccountFragment;
import com.example.myapplication.fragments.ExploreFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.MenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView mBottomNav;
    public int mPosition;
    private static final String TAG_HOME = "home";
    private static final String TAG_EXPLORE = "explore";
    private static final String TAG_ACCOUNT = "account";
    private static final String TAG_MENU = "menu";
    public static String CURRENT_TAG = TAG_HOME;

    private int clickItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNav = findViewById(R.id.bottom_navigation);

        setupBottomNavigation();

        //position navigation bottom
        mPosition = 0;
        if(savedInstanceState != null){
            mPosition = savedInstanceState.getInt("position", 0);
        }
        setFragment(getFragment(mPosition));
    }

    private void setFragment(Fragment fragment){
        mBottomNav.getMenu().getItem(mPosition).setChecked(true);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_content, fragment, CURRENT_TAG);
        try {
            ft.commit();
        }catch (Exception e){
            ft.commitAllowingStateLoss();
        }

        Menu menu = mBottomNav.getMenu();
        menu.getItem(mPosition).setChecked(true);
    }

    private Fragment getFragment(int position){
        CURRENT_TAG = TAG_HOME;
        switch (position){
            case 0:
                CURRENT_TAG = TAG_HOME;
                return new HomeFragment();
            case 1:
                CURRENT_TAG = TAG_EXPLORE;
                return new ExploreFragment();
            case 2:
                CURRENT_TAG = TAG_ACCOUNT;
                return new AccountFragment();
            case 3:
                CURRENT_TAG = TAG_MENU;
                return new MenuFragment();
        }
        return new HomeFragment();
    }

    public void selectPosition(int position){
        mPosition = position;
        setFragment(getFragment(position));
        mBottomNav.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt("position", mPosition);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void setupBottomNavigation() {
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nv_home:
                        mPosition = 0;
                        break;
                    case R.id.nv_explore:
                        mPosition = 1;
                        break;
                    case R.id.nv_acoount:
                        mPosition = 2;
                        break;
                    case R.id.nv_menu:
                        mPosition = 3;
                        break;
                }
                selectPosition(mPosition);
                item.setChecked(true);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(CURRENT_TAG != TAG_HOME){
            mPosition = 0;
            selectPosition(mPosition);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}