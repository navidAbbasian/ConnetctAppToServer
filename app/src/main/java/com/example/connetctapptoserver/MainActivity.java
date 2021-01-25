package com.example.connetctapptoserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final Integer ADD_STUDENT_REQUEST_ID = 1001;
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService=new ApiService(this,TAG);

        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        //move between activities
        View addNewStudentBtn = findViewById(R.id.fab_main_addNewStudent);
        addNewStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this,AddNewStudentFormActivity.class),ADD_STUDENT_REQUEST_ID);
            }
        });

        apiService.getStudents(new ApiService.ListStudentCallBack() {
            @Override
            public void onSuccess(List<Student> students) {


                recyclerView = findViewById(R.id.rv_main_students);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                studentAdapter = new StudentAdapter(students);
                recyclerView.setAdapter(studentAdapter);


            }

            @Override
            public void onError(VolleyError error) {

                Toast.makeText(MainActivity.this,"خطای نامشخص",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==ADD_STUDENT_REQUEST_ID && resultCode==Activity.RESULT_OK){
            if (data!=null && studentAdapter!=null){

                Student student=data.getParcelableExtra("student");
                studentAdapter.addStudent(student);
                recyclerView.smoothScrollToPosition(0);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.cancel();
    }
}
