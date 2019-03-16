package com.bumblee.idfscholarship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class OrganizationFormActivity extends AppCompatActivity {

    private EditText etName, etUserName, etDesc, etNumber, etLink;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = findViewById(R.id.etName);
        etUserName = findViewById(R.id.etUserName);
        etDesc = findViewById(R.id.etDescription);
        etNumber = findViewById(R.id.etPhone);
        etLink = findViewById(R.id.etWebsite);
        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String username = etUserName.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String phoneNumber = etNumber.getText().toString().trim();
                String websiteLink = etLink.getText().toString().trim();

                JSONObject orgDetails = new JSONObject();
                try {
                    orgDetails.put("username", username);
                    orgDetails.put("name", name);
                    orgDetails.put("description", desc);
                    orgDetails.put("phoneNumber", phoneNumber);
                    orgDetails.put("website", websiteLink);

                    Log.d("JSON-DATA", orgDetails.toString());

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

    }


}
