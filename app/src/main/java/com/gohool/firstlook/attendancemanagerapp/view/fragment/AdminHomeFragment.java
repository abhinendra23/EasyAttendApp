package com.gohool.firstlook.attendancemanagerapp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.view.activity.CourseDeptctivity;
import com.gohool.firstlook.attendancemanagerapp.view.activity.DeptActivity;
import com.gohool.firstlook.attendancemanagerapp.view.activity.SelectStudentActivity;


public class AdminHomeFragment extends Fragment {
    private CardView studentListCV,teacherListCV,courseListCV,attendanceListCV;

    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin_home, container, false);

        studentListCV=view.findViewById(R.id.studentListCV);
        teacherListCV=view.findViewById(R.id.teacherListCV);
        courseListCV=view.findViewById(R.id.courseListCV);


        studentListCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SelectStudentActivity.class);
                startActivity(intent);
            }
        });

        teacherListCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), DeptActivity.class);
                startActivity(intent);
            }
        });   courseListCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), CourseDeptctivity.class);
                startActivity(intent);
            }
        });

        return  view;

    }

}
