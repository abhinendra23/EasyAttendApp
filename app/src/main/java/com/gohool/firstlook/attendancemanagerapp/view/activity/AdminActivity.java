package com.gohool.firstlook.attendancemanagerapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.storage.SaveUser;
import com.gohool.firstlook.attendancemanagerapp.view.fragment.AdminHomeFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    private NavigationView admin_nav_view;
    private DrawerLayout admin_nav_drawer;
    private Toolbar admin_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        admin_toolbar=findViewById(R.id.admin_toolbar_id);
        setSupportActionBar(admin_toolbar);

        admin_nav_drawer=findViewById(R.id.admin_nav_drawer_id);
        admin_nav_view=findViewById(R.id.admin_nav_view_id);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, admin_nav_drawer, admin_toolbar,R.string.nav_drawer_open, R.string.nav_drawer_close);

        admin_nav_drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,new AdminHomeFragment()).commit();
        }
        admin_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_item_logout:
                        Intent intent=new Intent(AdminActivity.this,MainActivity.class);
                        SaveUser saveUser=new SaveUser();
                        saveUser.admin_saveData(AdminActivity.this,false);
                        startActivity(intent);
                        finish();

                }
                return true;
            }
        });


    }
}
