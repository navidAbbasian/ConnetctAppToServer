package com.example.connetctapptoserver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    //use constructor for get list from MainActivity
    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students=students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false));
    }

    public void addStudent(Student student){
        this.students.add(0,student);
        notifyItemInserted(0);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView fullNameTv;
        private TextView firstCharacterTv;
        private TextView courseTv;
        private TextView scoreTv;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTv=itemView.findViewById(R.id.tv_student_fullName);
            firstCharacterTv=itemView.findViewById(R.id.tv_student_firstCharacter);
            courseTv=itemView.findViewById(R.id.tv_student_course);
            scoreTv=itemView.findViewById(R.id.tv_student_score);
        }

        //pass students
        public void bind(Student student) {

            fullNameTv.setText(student.getFirstName()+" "+student.getLastName());
            courseTv.setText(student.getCourse());
            scoreTv.setText(String.valueOf(student.getScore()));
            firstCharacterTv.setText(student.getFirstName().substring(0,1));

        }
    }
}
