package com.gohool.firstlook.attendancemanagerapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.adapter.ViewAttendanceAdapter;
import com.gohool.firstlook.attendancemanagerapp.model.Student;
import com.gohool.firstlook.attendancemanagerapp.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendenceActivity extends AppCompatActivity {
    private RecyclerView viewAttendanceRV;
    private String intentded_course,intentDate;
    private DatabaseReference studentRef,deptref,batchRef,attendanceRef;
    private String dept,batch;
    private List<Student> studentList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewAttendanceRV=findViewById(R.id.ViewAttendanceRV);
        Intent intent=getIntent();
        intentded_course=intent.getStringExtra("SC");

        studentRef= FirebaseDatabase.getInstance().getReference().child("Department");

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        dept=dataSnapshot1.getKey();
                        deptref=studentRef.child(new SaveUser().getTeacher(ViewAttendenceActivity.this).getDepartment()).child("Student");
                        deptref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                studentList.clear();
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot ds1:dataSnapshot.getChildren()){
                                        for (DataSnapshot ds2:ds1.child("allstudent").child(new SaveUser().teacher_ShiftLoadData(getApplicationContext())).getChildren()){
                                            if(ds2.hasChildren()){
                                                Student student=ds2.getValue(Student.class);
                                                if(student.getCourse().contains(intentded_course)){
                                                    studentList.add(student);
                                                }
                                            }

                                        }
                                    }

                                    ViewAttendanceAdapter viewAttendanceAdapter=new ViewAttendanceAdapter(ViewAttendenceActivity.this,studentList);

                                    viewAttendanceRV.setLayoutManager(new LinearLayoutManager(ViewAttendenceActivity.this));
                                    viewAttendanceAdapter.notifyDataSetChanged();
                                    viewAttendanceRV.setAdapter(viewAttendanceAdapter);



                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
