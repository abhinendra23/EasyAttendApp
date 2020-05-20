package com.gohool.firstlook.attendancemanagerapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.storage.SaveUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

// Using handler with postDelayed called runnable run method

            @Override

            public void run() {

                if(new SaveUser().introLoad(getApplicationContext())){

                    if(new SaveUser().admin_loadData(getApplicationContext())){
                        startActivity(new Intent(SplashActivity.this, AdminActivity.class));
                        finish();

                    }else if(new SaveUser().teacher_loadData(getApplicationContext())){
                        startActivity(new Intent(SplashActivity.this, TeacherActivity.class));
                        finish();
                    }else if(new SaveUser().Student_loadData(getApplicationContext())){
                        startActivity(new Intent(SplashActivity.this, StudentActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }

                }else {

                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                    finish();

                }


            }

        }, 2*1000);



    }
}
