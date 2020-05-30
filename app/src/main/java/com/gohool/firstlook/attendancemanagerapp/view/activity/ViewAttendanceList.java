package com.gohool.firstlook.attendancemanagerapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.adapter.ViewAttendanceAdapter;
import com.gohool.firstlook.attendancemanagerapp.adapter.ViewListAdapter;
import com.gohool.firstlook.attendancemanagerapp.model.Student;
import com.gohool.firstlook.attendancemanagerapp.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.hasnat.sweettoast.SweetToast;


public class ViewAttendanceList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference studentRef,deptref,batchRef,attendanceRef,presentRef;
    private String date,course;
    private List<Student> studentList=new ArrayList<>();
    private String dept,batch;
    private List<Student> presentList=new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private Map<String,Student>map=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance_list);
        recyclerView=findViewById(R.id.ViewAttendanceListRV);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new ViewListAdapter());
        date = getIntent().getStringExtra("DATE");
        course=getIntent().getStringExtra("SC");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        studentRef= FirebaseDatabase.getInstance().getReference().child("Department");

        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        dept=dataSnapshot1.getKey();
                        deptref=studentRef.child(new SaveUser().getTeacher(ViewAttendanceList.this).getDepartment()).child("Student");
                        deptref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                studentList.clear();
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot ds1:dataSnapshot.getChildren()){
                                        for (DataSnapshot ds2:ds1.child("allstudent").child(new SaveUser().teacher_ShiftLoadData(getApplicationContext())).getChildren()){
                                            if(ds2.hasChildren()){
                                                Student student=ds2.getValue(Student.class);
                                                if(student.getCourse().contains(course)){
                                                    studentList.add(student);
                                                    map.put(student.getId(),student);
                                                }
                                            }

                                        }
                                    }
                                    presentRef = FirebaseDatabase.getInstance().getReference().child("Department").child(new SaveUser().getTeacher(ViewAttendanceList.this).getDepartment())
                                            .child("Attendance").child(new SaveUser().getTeacher(ViewAttendanceList.this).getShift()).child(course).child(date);
                                    presentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            presentList.clear();
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Present").getChildren()) {

                                                    String key = dataSnapshot1.getKey();

                                                    if(map.containsKey(key))
                                                    {
                                                        presentList.add(map.get(key));
                                                    }
                                                    Log.i("Present","Detected");

                                                    ViewListAdapter viewListAdapter = new ViewListAdapter(ViewAttendanceList.this, presentList);
                                                    recyclerView.setLayoutManager(new LinearLayoutManager(ViewAttendanceList.this));
                                                    viewListAdapter.notifyDataSetChanged();
                                                    recyclerView.setAdapter(viewListAdapter);
                                                }

                                            } else
                                            {
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });




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
}
