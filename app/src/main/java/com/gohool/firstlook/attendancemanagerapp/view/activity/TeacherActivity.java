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
import com.gohool.firstlook.attendancemanagerapp.view.fragment.TeacherFragment;
import com.google.android.material.navigation.NavigationView;

public class TeacherActivity extends AppCompatActivity {
    private NavigationView teacher_nav_view;
    private DrawerLayout teacher_nav_drawer;
    private Toolbar teacher_toolbar;
    private String intententId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Intent intent1=getIntent();
        intententId=intent1.getStringExtra("TEACHERID");

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.teacher_fragment_container,new TeacherFragment())
                    .commit();
        }

        teacher_toolbar =findViewById(R.id.teacher_toolbar_id);
        setSupportActionBar(teacher_toolbar);

        teacher_nav_drawer =findViewById(R.id.teacher_nav_drawer_id);
        teacher_nav_view =findViewById(R.id.teacher_nav_view_id);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, teacher_nav_drawer, teacher_toolbar,R.string.nav_drawer_open, R.string.nav_drawer_close);

        teacher_nav_drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        teacher_nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_item_logout:
                        Intent intent=new Intent(TeacherActivity.this,MainActivity.class);
                        SaveUser saveUser=new SaveUser();
                        saveUser.teacher_saveData(TeacherActivity.this,false);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                }
                return true;
            }
        });


    }
    public String getData(){
        return intententId;
    }
}
