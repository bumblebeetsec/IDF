package com.bumblee.idfscholarship;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScholarshipDetailsActivity extends AppCompatActivity {

    private int sid;
    ProgressDialog dialog;
    TextView txtName,txtState,txtReligion,txtCategory,txtIncome,txtCourse,txtGender,txtPhy,txtDes,txtOther, txtLinks;
    Button btnApply;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship_details);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        txtCategory=(TextView)findViewById(R.id.);
        txtName=(TextView)findViewById(R.id.nameTV);
        txtState=(TextView)findViewById(R.id.stateTV);
        txtIncome=(TextView)findViewById(R.id.txtIncome);
        txtCourse=(TextView)findViewById(R.id.courseTV);
        txtGender=(TextView)findViewById(R.id.genderTV);
        txtPhy=(TextView)findViewById(R.id.txtPhy);
        txtDes=(TextView)findViewById(R.id.descTV);
        txtOther=(TextView)findViewById(R.id.txtOther);
        btnApply=(Button)findViewById(R.id.btnApply);
        txtLinks = (TextView) findViewById(R.id.txtLinks);
        txtReligion = (TextView) findViewById(R.id.religionTV);
        progressBar = findViewById(R.id.progressBar);

        if (getIntent().getExtras()!=null){
            sid = getIntent().getExtras().getInt("sid");
        }

        JSONObject sidJSON = new JSONObject();

        try {
            sidJSON.put("sid", sid);

            Call<ResponseBody> call = RetorfitClient
                    .getInstance()
                    .getApi()
                    .getScholarshipDetails(sidJSON.toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String s = response.body().string();
//                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(OrganizationFormActivity.this, MainActivity.class));
                        Log.d("JSON-DATA", s);
                        JSONObject jsonObject = new JSONObject(s);
                        JSONObject o = jsonObject.getJSONObject("scholarship");
                        txtName.setText(o.getString("name"));
                        txtCategory.setText(o.getString("category"));
                        txtCourse.setText(o.getString("course"));
                        txtDes.setText(o.getString("scholarship_description"));
                        txtGender.setText(o.getString("gender"));
                        txtState.setText(o.getString("state"));
                        txtReligion.setText(o.getString("religion"));
                        txtIncome.setText(o.getString("max_annual_income"));
                        txtPhy.setText(o.getString("physically_challenged"));
                        txtOther.setText(o.getString("other_eligibility_details"));
                        txtLinks.setText(o.getString("form_link"));



                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();
//                    submitBtn.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
//                    submitBtn.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                btnApply.setVisibility(View.INVISIBLE);

                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("uid", uid);
                    jsonObject.put("sid", sid);

                    Call<ResponseBody> call = RetorfitClient
                            .getInstance()
                            .getApi()
                            .applyScholarship(jsonObject.toString());

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String s = response.body().toString();
                            Log.d("JSON-STATUS", s);
                            startActivity(new Intent(ScholarshipDetailsActivity.this, MainActivity.class));

                            btnApply.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                               Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            btnApply.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
