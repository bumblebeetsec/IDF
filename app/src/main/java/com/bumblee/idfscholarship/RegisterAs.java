package com.bumblee.idfscholarship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterAs extends AppCompatActivity {

    private Button studentRegisterButton;
    private Button orgRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);

        studentRegisterButton = findViewById(R.id.studentRegisterButton);
        orgRegisterButton = findViewById(R.id.orgRegisterButton);

        orgRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterAs.this, OrganizationFormActivity.class);
//                intent.putExtra("name", getIntent().getExtras().getString("userName"));
                startActivity(intent);
                finish();
            }
        });
    }
}
