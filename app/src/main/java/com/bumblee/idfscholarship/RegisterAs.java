package com.bumblee.idfscholarship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

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
            }
        });

        studentRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterAs.this, StudentFormActivity.class);
//                intent.putExtra("name", getIntent().getExtras().getString("userName"));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.signOut:
                signOutUser();
                break;
        }
        return true;
    }

    public void signOutUser(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(RegisterAs.this, LoginActivity.class));
        finish();
    }
}
