package com.gohool.firstlook.attendancemanagerapp.model;

public class Attendance {
    private String date;
    private String name;
    private String course;
    private  String shift;

    public  Attendance()
    {

    }

    public Attendance(String date, String name, String course, String shift) {
        this.date = date;
        this.name = name;
        this.course = course;
        this.shift = shift;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
