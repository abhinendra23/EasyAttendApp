package com.gohool.firstlook.attendancemanagerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.model.Teacher;

import java.util.List;

public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.TeacherListViewHolder> {

    private List<Teacher> teacherlist;
    private Context context;

    public TeacherListAdapter(Context context, List<Teacher> teacherlist) {

        this.context = context;
        this.teacherlist = teacherlist;
    }

    public TeacherListAdapter(){

    }

    @NonNull
    @Override
    public TeacherListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.single_student_layout,viewGroup,false);
        return new TeacherListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherListViewHolder teacherListViewHolder, int i) {
        teacherListViewHolder.Student_name.setText(teacherlist.get(teacherListViewHolder.getAdapterPosition()).getName());
        teacherListViewHolder.course_code.setText(teacherlist.get(teacherListViewHolder.getAdapterPosition()).getDesignation());
        teacherListViewHolder.teacherEmail.setText(teacherlist.get(teacherListViewHolder.getAdapterPosition()).getEmail());
    }

    @Override
    public int getItemCount() {
        return teacherlist.size();
    }

    class TeacherListViewHolder extends  RecyclerView.ViewHolder {
        TextView Student_name,teacherEmail;
        TextView course_code;
        public TeacherListViewHolder(@NonNull View itemView) {
            super(itemView);
            Student_name=itemView.findViewById(R.id.studentNameTV);
            course_code=itemView.findViewById(R.id.studentCourseTv);
            teacherEmail=itemView.findViewById(R.id.studentIDv);
        }
    }

    public void updateCollection(List<Teacher> studentList){
        this.teacherlist =studentList;
        notifyDataSetChanged();
    }
}
