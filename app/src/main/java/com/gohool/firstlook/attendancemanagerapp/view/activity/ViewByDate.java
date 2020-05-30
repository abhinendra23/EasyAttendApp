package com.gohool.firstlook.attendancemanagerapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gohool.firstlook.attendancemanagerapp.R;

import xyz.hasnat.sweettoast.SweetToast;

public class ViewByDate extends AppCompatActivity {

    private String intent_course;
    private Button nextBtn;
    private EditText dateET;
    private ImageButton dateIbtn;
    private String date;
    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_date);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dateET=findViewById(R.id.datetxt);
        dateIbtn=findViewById(R.id.dateIbtn);
        nextBtn=findViewById(R.id.dateNxtBtn);
        Intent intent=getIntent();
        intent_course=intent.getStringExtra("SC");
        dateIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePicker datePicker=new DatePicker(ViewByDate.this);
                int currentDay=datePicker.getDayOfMonth();
                int currentMonth=datePicker.getMonth();
                int currentYear=datePicker.getYear();
                datePickerDialog =new DatePickerDialog(ViewByDate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                StringBuilder stringBuilder=new StringBuilder();
                                stringBuilder.append(dayOfMonth+"-");
                                stringBuilder.append((month+1)+"-");
                                stringBuilder.append(year);
                                dateET.setText(stringBuilder.toString());
                            }
                        },currentYear,currentMonth,currentDay

                );
                datePickerDialog.show();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=dateET.getText().toString();
                if(!date.isEmpty()){
                    Intent intent=new Intent(ViewByDate.this,ViewAttendanceList.class);
                    intent.putExtra("DATE",date);
                    intent.putExtra("SC",intent_course);
                    startActivity(intent);
                }else {
                    SweetToast.warning(getApplicationContext(),"Pick a date first");
                }

            }
        });
    }
}
