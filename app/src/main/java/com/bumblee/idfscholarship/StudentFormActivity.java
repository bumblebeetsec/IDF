package com.bumblee.idfscholarship;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumblee.idfscholarship.R;
import com.google.firebase.auth.FirebaseAuth;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentFormActivity extends AppCompatActivity {
    Button btnSubmit;
    EditText etName,etUserName,etIncome,etPhone;
    TextView etDate;
    Spinner spnState,spnReligion,spnDegree,spnCategory,spnCourse;
    CheckBox chkPhysically;
    RadioGroup genderRG;
    private ProgressBar progressBar;


    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        etName=(EditText)findViewById(R.id.etName);
        etUserName=(EditText)findViewById(R.id.etUserName);
        etDate=(TextView)findViewById(R.id.etDate);
        etIncome=(EditText)findViewById(R.id.etIncome);
        etPhone=(EditText)findViewById(R.id.etPhone);
        spnState=(Spinner)findViewById(R.id.spnState);
        spnReligion=(Spinner)findViewById(R.id.spnReligion);
        spnDegree=(Spinner)findViewById(R.id.spnDegree);
        spnCategory=(Spinner)findViewById(R.id.spnCategory);
        spnCourse=(Spinner)findViewById(R.id.spnCourse);
        chkPhysically=(CheckBox)findViewById(R.id.chkPhysically);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        genderRG=(RadioGroup)findViewById(R.id.genderRG);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        String tstate[]={"Select state",
                "Andhra Pradesh",
                "Arunachal Pradesh",
                "Assam",
                "Bihar",
                "Chhattisgarh",
                "Goa",
                "Gujarat",
                "Haryana",
                "Himachal Pradesh",
                "Jammu and Kashmir",
                "Jharkhand",
                "Karnataka",
                "Kerala",
                "Madhya Pradesh",
                "Maharashtra",
                "Manipur",
                "Meghalaya",
                "Mizoram",
                "Nagaland",
                "Odisha",
                "Punjab",
                "Rajasthan",
                "Sikkim",
                "Tamil Nadu",
                "Telangana",
                "Tripura",
                "Uttarakhand",
                "Uttar Pradesh",
                "West Bengal",
                "Andaman and Nicobar Islands",
                "Chandigarh",
                "Dadra and Nagar Haveli",
                "Daman and Diu",
                "Delhi",
                "Lakshadweep",
                "Puducherry"};
        final ArrayList<String> sState = new ArrayList<>();
        int i;
        for (i = 0; i <tstate.length; i++)
            sState.add(tstate[i]);
        ArrayAdapter<String> ft1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sState);
        spnState.setAdapter(ft1);

        String tReligion[]={"Select your religion",
                "Hindu",
                "Muslim",
                "Christian",
                "Sikh",
                "Parsi",
                "Other"};
        final ArrayList<String> sReligion=new ArrayList<>();
        for(i=0;i<tReligion.length;i++)
            sReligion.add(tReligion[i]);
        ArrayAdapter<String> ft2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sReligion);
        spnReligion.setAdapter(ft2);

        String tDegree[]={"Select current degree",
                "KG",
                "Class 1",
                "Class 2",
                "Class 3",
                "Class 4",
                "Class 5",
                "Class 6",
                "Class 7",
                "Class 8",
                "Class 9",
                "Class 10",
                "Class 11",
                "Class 12",
                "Diploma",
                "Graduation",
                "Post",
                "PhD",
                "Post",
                "ITI",
                "Others"};
        final ArrayList<String> sDegree=new ArrayList<>();
        for(i=0;i<tDegree.length;i++)
            sDegree.add(tDegree[i]);
        ArrayAdapter<String> ft3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sDegree);
        spnDegree.setAdapter(ft3);

        String tCategory[]={
                "SC",
                "ST",
                "OBC",
                "GENERAL",
                "ST-PVGT",
                "APST"
        };
        final ArrayList<String> sCategory=new ArrayList<>();
        for(i=0;i<tCategory.length;i++)
            sCategory.add(tCategory[i]);
        ArrayAdapter<String> ft4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sCategory);
        spnCategory.setAdapter(ft4);

        String tCourse[]={"Select desired degree",
                "KG",
                "Class 1",
                "Class 2",
                "Class 3",
                "Class 4",
                "Class 5",
                "Class 6",
                "Class 7",
                "Class 8",
                "Class 9",
                "Class 10",
                "Class 11",
                "Class 12",
                "Diploma",
                "Graduation",
                "Post",
                "PhD",
                "Post",
                "ITI",
                "Others"};

        final ArrayList<String> sCourse=new ArrayList<>();
        for(i=0;i<tCourse.length;i++)
            sCourse.add(tCourse[i]);
        ArrayAdapter<String> ft5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sCourse);
        spnCourse.setAdapter(ft5);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(StudentFormActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });






        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String name=etName.getText().toString();
                String username=etUserName.getText().toString();
                DecimalFormat formatter = new DecimalFormat("00");

                int getmonth = myCalendar.get(Calendar.MONTH) + 01;
                String month = formatter.format(getmonth);
                int getday = myCalendar.get(Calendar.DATE);
                String day = formatter.format(getday);
                int year = myCalendar.get(Calendar.YEAR) ;
                String date = year + "-" + month + "-" + day;
                int pos1 = spnState.getSelectedItemPosition();
                String state = sState.get(pos1).toString();
                int pos2 = spnReligion.getSelectedItemPosition();
                String religion = sReligion.get(pos2).toString();
                int pos3 = spnDegree.getSelectedItemPosition();
                String current_course = sDegree.get(pos3).toString();
                int pos4 = spnCategory.getSelectedItemPosition();
                String category = sCategory.get(pos4).toString();
                int pos5 = spnCourse.getSelectedItemPosition();
                String course_interested_in = sCourse.get(pos5).toString();
                String phonenumber=etPhone.getText().toString();
                String PC = "";
                int id=genderRG.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)findViewById(id);
                String gender=rb.getText().toString();
                if(chkPhysically.isChecked()){
                    PC="Yes";
                }
                else PC = "No";
                String annual_income=etIncome.getText().toString();
                if(name.length()==0)
                {
                    etName.setError("Enter your name");
                    etName.requestFocus();
                    return;
                }
                if(username.length()==0)
                {
                    etUserName.setError("Enter username");
                    etUserName.requestFocus();
                    return;
                }
                if(phonenumber.length()!=10)
                {

                    etPhone.setError("Enter mobile number");
                    etPhone.requestFocus();
                    return;
                }

                btnSubmit.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                JSONObject studentDetails = new JSONObject();
                try {
                    studentDetails.put("uid", uid);
                    studentDetails.put("username", username);
                    studentDetails.put("name", name);
                    studentDetails.put("date_of_birth", date);
                    studentDetails.put("state", state);
                    studentDetails.put("religion", religion);
                    studentDetails.put("phone_number", phonenumber);
                    studentDetails.put("current_course", current_course);
                    studentDetails.put("category", category);
                    studentDetails.put("course_interested_in", course_interested_in);
                    studentDetails.put("gender", gender);
                    studentDetails.put("annual_income", annual_income);
                    studentDetails.put("physically_challenged", PC);

                    Log.d("JSON-DATA", String.valueOf(studentDetails));

                    Call<ResponseBody> call = RetorfitClient
                            .getInstance()
                            .getApi()
                            .registerStudent(studentDetails.toString());

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s = response.body().string();
//                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(StudentFormActivity.this, MainActivity.class));
                                finish();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            btnSubmit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            btnSubmit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

}