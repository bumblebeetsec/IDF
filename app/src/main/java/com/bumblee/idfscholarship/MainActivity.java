package com.bumblee.idfscholarship;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.provider.SelfDestructiveThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        JSONObject uidJSON = new JSONObject();
        try {
            uidJSON.put("uid", uid);
            Log.d("JSON-DATA", String.valueOf(uidJSON));

            Call<ResponseBody> call = RetorfitClient
                    .getInstance()
                    .getApi()
                    .getType(uidJSON.toString());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        Fragment selectedFragment = null;
                        String s = response.body().string();
//                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        JSONObject jsonResponse = new JSONObject(s);
                        if (jsonResponse.get("type").equals("student")){
                            Log.d("JSON-DATA", "student");
                            bottomNavigationView.setOnNavigationItemSelectedListener(studentNavListener);
                            selectedFragment = new StudentHomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            setStudentFragments();
                        }else {
                            Log.d("JSON-DATA", "organization");
                            selectedFragment = new OrgHomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            bottomNavigationView.setOnNavigationItemSelectedListener(orgNavListener);
                            setOrganizationFragments();
                        };

                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (dialog.isShowing()
                    ){
                        dialog.dismiss();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setStudentFragments() {

    }

    private void setOrganizationFragments() {

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
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener studentNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new StudentHomeFragment();
                            break;
                        case R.id.nav_alert:

                            break;
                        case R.id.nav_profile:

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    private BottomNavigationView.OnNavigationItemSelectedListener orgNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new OrgHomeFragment();
                            break;
                        case R.id.nav_alert:

                            break;
                        case R.id.nav_profile:

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };



}
