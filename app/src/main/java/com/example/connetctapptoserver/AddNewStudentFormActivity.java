package com.example.connetctapptoserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewStudentFormActivity extends AppCompatActivity {
    private static final String TAG = "AddNewStudentFormActivi";
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student_form);

        apiService =new ApiService(this,TAG);




        Toolbar toolbar = findViewById(R.id.toolbar_addNewStudent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //change back btn color
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);

        TextInputEditText firstNameEt = findViewById(R.id.et_addNewStudent_firstName);
        TextInputEditText lastNameEt = findViewById(R.id.et_addNewStudent_lastName);
        TextInputEditText courseEt = findViewById(R.id.et_addNewStudent_course);
        TextInputEditText scoreEt = findViewById(R.id.et_addNewStudent_score);

        View saveBtn = findViewById(R.id.fab_addNewStudent_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first validate inputs

                if (firstNameEt.length()>0 &&
                lastNameEt.length()>0 &&
                courseEt.length()>0 &&
                scoreEt.length()>0){

                    apiService.saveStudent(firstNameEt.getText().toString(),
                            lastNameEt.getText().toString(),
                            courseEt.getText().toString(),
                            Integer.parseInt(scoreEt.getText().toString()),
                            new ApiService.SaveStudentCallBack() {
                                @Override
                                public void onSuccess(Student student) {


                                   Intent intent=new Intent();
                                   intent.putExtra("student",student);
                                   setResult(Activity.RESULT_OK,intent);
                                   finish();

                                }

                                @Override
                                public void onError(VolleyError error) {

                                    Toast.makeText(AddNewStudentFormActivity.this,"خطای نامشخص",Toast.LENGTH_SHORT).show();

                                }
                            });

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.cancel();
    }

    //for when don't work our back btn

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId()==android.R.id.home){
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}