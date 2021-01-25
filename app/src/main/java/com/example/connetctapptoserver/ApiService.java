package com.example.connetctapptoserver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.service.autofill.SaveCallback;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String TAG = "ApiService";
    private static RequestQueue requestQueue;
    private static final String BASE_URL="http://expertdevelopers.ir/api/v1/";
    private String requestTag;

    public ApiService(Context context, String requestTag){
        this.requestTag=requestTag;
        if (requestQueue == null)
           requestQueue =Volley.newRequestQueue(context.getApplicationContext());

    }

    public void saveStudent(String firstName, String lastName, String course, int score, SaveStudentCallBack callBack) {

        //put information in the json object
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("first_name",firstName);
            jsonObject.put("last_name",lastName);
            jsonObject.put("course",course);
            jsonObject.put("score",score);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //use  JsonObjectRequest because our request is post method and has json type
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                BASE_URL+"experts/student",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i(TAG, "onResponse: "+response);

                        //change response to java class
                        Student student=new Student();
                        try {

                            student.setFirstName(response.getString("first_name"));
                            student.setLastName(response.getString("last_name"));
                            student.setId(response.getInt("id"));
                            student.setCourse(response.getString("course"));
                            student.setScore(response.getInt("score"));
                            callBack.onSuccess(student);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: "+error.toString());
                        callBack.onError(error);
                    }
                });

        request.setTag(requestTag);

        requestQueue.add(request);

    }

    public void getStudents(ListStudentCallBack callBack){

        //give request
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL+"experts/student",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "onResponse: ");

                        List<Student> students = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            //use  loop for put value in the array
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject studentJsonObject = jsonArray.getJSONObject(i);
                                Student student = new Student();
                                student.setFirstName(studentJsonObject.getString("first_name"));
                                student.setLastName(studentJsonObject.getString("last_name"));
                                student.setCourse(studentJsonObject.getString("course"));
                                student.setId(studentJsonObject.getInt("id"));
                                student.setScore(studentJsonObject.getInt("score"));
                                students.add(student);

                            }
                            Log.i(TAG, "onResponse: " + students.size());
                            callBack.onSuccess(students);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: " + error);

                callBack.onError(error);
            }
        });

        request.setTag(requestTag);

        //add request
        requestQueue.add(request);
        
    }

    public void cancel(){
        requestQueue.cancelAll(requestTag);
    }

    public interface SaveStudentCallBack{

        public void onSuccess(Student student);

        public void onError(VolleyError error);

    }

    public interface ListStudentCallBack{

        public void onSuccess(List<Student> students);

        public void onError(VolleyError error);
    }
}
