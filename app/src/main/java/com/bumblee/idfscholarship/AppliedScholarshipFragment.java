package com.bumblee.idfscholarship;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumblee.idfscholarship.Adapters.RecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppliedScholarshipFragment extends Fragment {

    RecyclerView recyclerView;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> dev = new ArrayList<>();
    private ArrayList<Integer> sid = new ArrayList<>();
    RadioGroup rGroup;
    RadioButton checkedRadioButton;
    ProgressDialog dialog;
    RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_applied_scholarship, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Enrollments");

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        recyclerView = view.findViewById(R.id.recycler_view);
        name = new ArrayList<String>();
        dev = new ArrayList<String>();
        sid = new ArrayList<Integer>();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        JSONObject uidObject = new JSONObject();

        try {
            uidObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO: 3/17/2019 change
        Call<ResponseBody> call = RetorfitClient
                .getInstance()
                .getApi()
                .getAppliedScholarships(uidObject.toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String s = response.body().string();
                    Log.d("JSON-DATA", s);
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("applications");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        name.add(o.getString("name"));
                        dev.add(o.getString("organisation_name"));
                        sid.add(o.getInt("id"));
                    }

                    adapter = new RecyclerViewAdapter(name, dev, sid, getActivity().getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        switch (item.getItemId()){
            case R.id.signOut:
                signOutUser();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void signOutUser(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
        getActivity().finish();
    }


}
