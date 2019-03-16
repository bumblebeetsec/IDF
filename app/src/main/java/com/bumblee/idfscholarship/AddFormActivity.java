package com.bumblee.idfscholarship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFormActivity extends AppCompatActivity {

    EditText etSname,etIncome3,etDes,etOther, etWebsite;
    Spinner spnState2,spnReligion2,spnCategory2,spnCourse2;
    Button btnAdd;
    CheckBox chkPhysically2,chkGender2;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        etSname=(EditText) findViewById(R.id.etSname);
        etIncome3=(EditText)findViewById(R.id.etIncome3);
        etDes=(EditText)findViewById(R.id.etDes);
        etOther=(EditText)findViewById(R.id.etOther);
        spnState2=(Spinner)findViewById(R.id.spnState2);
        spnCategory2=(Spinner)findViewById(R.id.spnCategory2);
        spnReligion2=(Spinner)findViewById(R.id.spnReligion2);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        chkGender2=(CheckBox)findViewById(R.id.chkGender2);
        chkPhysically2=(CheckBox)findViewById(R.id.chkPhysically2);
        spnCourse2=(Spinner)findViewById(R.id.spnCourse2);
        etWebsite = (EditText) findViewById(R.id.etWebsite);
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
        spnState2.setAdapter(ft1);

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
        spnReligion2.setAdapter(ft2);



        String tCategory[]={"Select your Category",
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
        spnCategory2.setAdapter(ft4);

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
        spnCourse2.setAdapter(ft5);

        btnAdd.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          String gender="",physically="";
                                          String name,des,income,other;
                                          name=etSname.getText().toString();
                                          des=etDes.getText().toString();
                                          income=etIncome3.getText().toString();
                                          other=etOther.getText().toString();

                                          if(name.length()==0)
                                          {
                                              etSname.setError("Enter scholarship name");
                                              etSname.requestFocus();
                                              return;
                                          }

                                          if(chkPhysically2.isChecked())
                                              physically="Yes";
                                          if(chkGender2.isChecked())
                                              gender="Female";
                                          int pos1 = spnState2.getSelectedItemPosition();
                                          String state = sState.get(pos1).toString();
                                          int pos2 = spnReligion2.getSelectedItemPosition();
                                          String religion = sReligion.get(pos2).toString();
                                          int pos4 = spnCategory2.getSelectedItemPosition();
                                          String category = sCategory.get(pos4).toString();
                                          int pos5 = spnCourse2.getSelectedItemPosition();
                                          String course_interested_in = sCourse.get(pos5).toString();
                                          String website = etWebsite.getText().toString();
                                          String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                          btnAdd.setVisibility(View.INVISIBLE);
                                          progressBar.setVisibility(View.VISIBLE);

                                          JSONObject scholarshipDetails = new JSONObject();
                                          try{

                                              scholarshipDetails.put("uid", uid);
                                              scholarshipDetails.put("name", name);
                                              scholarshipDetails.put("state", state);
                                              scholarshipDetails.put("religion", religion);
                                              scholarshipDetails.put("category", category);
                                              scholarshipDetails.put("course", course_interested_in);
                                              scholarshipDetails.put("max_annual_income", income);
                                              scholarshipDetails.put("gender", gender);
                                              scholarshipDetails.put("physically_challenged", physically);
                                              scholarshipDetails.put("scholarship_description", des);
                                              scholarshipDetails.put("form_link", website);
                                              scholarshipDetails.put("other_eligibility_details", other);

                                              Log.d("JSON-DATA", scholarshipDetails.toString());

                                              Call<ResponseBody> call = RetorfitClient
                                                      .getInstance()
                                                      .getApi()
                                                      .addScholarship(scholarshipDetails.toString());

                                              call.enqueue(new Callback<ResponseBody>() {
                                                  @Override
                                                  public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                      try {
                                                          String s = response.body().string();
//                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                                                          startActivity(new Intent(AddFormActivity.this, MainActivity.class));
                                                      } catch (IOException e) {
                                                          e.printStackTrace();
                                                      }

                                                      btnAdd.setVisibility(View.VISIBLE);
                                                      progressBar.setVisibility(View.INVISIBLE);

                                                  }

                                                  @Override
                                                  public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                      Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                                      btnAdd.setVisibility(View.VISIBLE);
                                                      progressBar.setVisibility(View.INVISIBLE);
                                                  }
                                              });

                                          } catch (JSONException e) {
                                              e.printStackTrace();
                                          }

                                          Intent i=new Intent(AddFormActivity.this, MainActivity.class);
                                          startActivity(i);


                                      }
                                  }
        );

    }
}
