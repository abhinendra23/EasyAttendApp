package com.gohool.firstlook.attendancemanagerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.model.Student;
import com.gohool.firstlook.attendancemanagerapp.storage.SaveUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendanceListAdapter extends RecyclerView.Adapter<ViewAttendanceListAdapter.ViewAttendanceListViewHolder>
        {
            public List<Student> studentList=new ArrayList<>();
            private List<String> presentList=new ArrayList<>();
            private List<String> absentList=new ArrayList<>();
            public ListView presentLV,absentLV;

            private Context context;
            private DatabaseReference presentRef,absentRef;
            public ViewAttendanceListAdapter(Context context,List<Student> studentList) {
                this.context = context;
                this.studentList=studentList;
            }

            public ViewAttendanceListAdapter(){

            }


            @NonNull
            @Override
            public ViewAttendanceListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                LayoutInflater layoutInflater= LayoutInflater.from(context);
                View view=layoutInflater.inflate(R.layout.view_attendance_card,viewGroup,false);
                return new ViewAttendanceListViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull final ViewAttendanceListViewHolder viewAttendanceListViewHolder, final int i) {

                presentRef = FirebaseDatabase.getInstance().getReference().child("Department").child(studentList.get(viewAttendanceListViewHolder.getAdapterPosition()).getDepartment())
                        .child("Attendance").child(new SaveUser().getTeacher(context).getShift()).child(new SaveUser().teacher_CourseLoadData(context));

                presentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        presentList.clear();
                        absentList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("Present").getChildren()) {

                                    String key = dataSnapshot2.getKey();
                                    if (viewAttendanceListViewHolder.getAdapterPosition() != -1) {
                                        if (key.equals(studentList.get(i).getId())) {
                                            presentList.add(studentList.get(i).getName());
                                        }


                                    }
                                }
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("Absent").getChildren()) {

                                    String key = dataSnapshot2.getValue().toString();
                                    if (key.equals(studentList.get(viewAttendanceListViewHolder.getAdapterPosition()).getId())) {
                                        absentList.add(studentList.get(viewAttendanceListViewHolder.getAdapterPosition()).getName());

                                    }
                                }
                            }
                        }
                        ArrayAdapter<String> presentAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,presentList);
                        ArrayAdapter<String> absentAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,absentList);

                        presentLV.setAdapter(presentAdapter);
                        absentLV.setAdapter(absentAdapter);
                        presentLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public int getItemCount() {
                return studentList.size();
            }

            public class ViewAttendanceListViewHolder extends RecyclerView.ViewHolder {
                public ViewAttendanceListViewHolder(@NonNull View itemView) {
                    super(itemView);
                    presentLV=itemView.findViewById(R.id.PresentAttendanceCardLV);
                    absentLV=itemView.findViewById(R.id.AbsentAttendanceCardLV);
                }

            }
            public void updateCollection(List<Student> studentList){
                this.studentList=studentList;
                notifyDataSetChanged();
            }
        }
