package com.gohool.firstlook.attendancemanagerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gohool.firstlook.attendancemanagerapp.R;
import com.gohool.firstlook.attendancemanagerapp.model.Student;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.ViewListViewHolder> {

    private List<Student> studentList=new ArrayList<>();
    private List<String> presentList=new ArrayList<>();
    private List<String> absentList=new ArrayList<>();
    private String date;

    private Context context;
    private DatabaseReference presentRef,absentRef;

    public ViewListAdapter(Context context, List<Student> studentList) {

        this.context = context;
        this.studentList = studentList;
    }

    public ViewListAdapter(){

    }

    @NonNull
    @Override
    public ViewListAdapter.ViewListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.single_student_layout,viewGroup,false);
        return new ViewListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewListViewHolder viewlistViewHolder, final int i) {
        viewlistViewHolder.Student_name.setText(studentList.get(viewlistViewHolder.getAdapterPosition()).getName());
        viewlistViewHolder.course_code.setText(studentList.get(viewlistViewHolder.getAdapterPosition()).getCourse_code());
        viewlistViewHolder.id.setText(studentList.get(viewlistViewHolder.getAdapterPosition()).getId());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class ViewListViewHolder extends  RecyclerView.ViewHolder {
        TextView Student_name;
        TextView course_code;
        TextView id;
        public ViewListViewHolder(@NonNull View itemView) {
            super(itemView);
            Student_name=itemView.findViewById(R.id.studentNameTV);
            course_code=itemView.findViewById(R.id.studentCourseTv);
            id=itemView.findViewById(R.id.studentIDv);
        }
    }

    public void updateCollection(List<Student> studentList){
        this.studentList=studentList;
        notifyDataSetChanged();
    }
}
