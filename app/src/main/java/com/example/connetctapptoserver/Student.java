package com.example.connetctapptoserver;


import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private String firstName;
    private String lastName;
    private String course;
    private int id;
    private int score;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.course);
        dest.writeInt(this.id);
        dest.writeInt(this.score);
    }

    public Student() {
    }

    protected Student(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.course = in.readString();
        this.id = in.readInt();
        this.score = in.readInt();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
