package com.gohool.firstlook.attendancemanagerapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.adapter.TeacherListAdapter;
import com.gohool.firstlook.attendancemanagerapp.model.Teacher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherListActivity extends AppCompatActivity {
    private Toolbar teacherListToolbar;
    private FloatingActionButton addTeacherButton;
    private RecyclerView teacherListRv;
    private String intentedDept,intentedShift;
    private DatabaseReference teacherListRef;
    private List<Teacher> teacherlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        teacherListToolbar=findViewById(R.id.teacherListToolbar);
        setSupportActionBar(teacherListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        teacherListRv=findViewById(R.id.teacherListRV);

        final Intent intent=getIntent();
        intentedDept=intent.getStringExtra("TDEPT");
        intentedShift=intent.getStringExtra("TSHIFT");

        teacherListRef= FirebaseDatabase.getInstance().getReference().child("Department").child(intentedDept).child("Teacher").child(intentedShift);

        teacherListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacherlist.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        if (dataSnapshot1.hasChildren()){
                            Teacher teacher=dataSnapshot1.getValue(Teacher.class);
                            teacherlist.add(teacher);
                        }
                    }

                    TeacherListAdapter teacherListAdapter=new TeacherListAdapter(TeacherListActivity.this,teacherlist);
                    teacherListRv.setLayoutManager(new LinearLayoutManager(TeacherListActivity.this));
                    teacherListAdapter.notifyDataSetChanged();
                    teacherListRv.setAdapter(teacherListAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        addTeacherButton=findViewById(R.id.addTeacherBtn);

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(TeacherListActivity.this,AddTeacherActivity.class);
                intent1.putExtra("TDEPT",intentedDept);
                intent1.putExtra("TSHIFT",intentedShift);

                startActivity(intent1);
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
