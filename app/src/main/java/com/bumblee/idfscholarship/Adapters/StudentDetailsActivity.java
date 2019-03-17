package com.bumblee.idfscholarship.Adapters;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumblee.idfscholarship.R;
import com.bumblee.idfscholarship.RetorfitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailsActivity extends AppCompatActivity {


    private int sid;
    ProgressDialog dialog;
    TextView txtName,txtState,txtReligion,txtCategory,txtIncome,txtCourse,txtGender,txtPhy,txtDes,txtOther, txtLinks;
    Button btnApply;
    private ProgressBar progressBar;

    private TextView studentTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        studentTextView = findViewById(R.id.studentTextView);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();


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

                        if (!s.equals("")){
                            studentTextView.setText(s);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
