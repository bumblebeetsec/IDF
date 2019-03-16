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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class OrgHomeFragment extends Fragment {

    RecyclerView recyclerView;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> dev = new ArrayList<>();
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_home, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("IDF Scholarships");

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerv_view);


        name = new ArrayList<>();
        dev = new ArrayList<>();



        JSONObject uidJSON = new JSONObject();
        try {
            uidJSON.put("uid", uid);
            Call<ResponseBody> call = RetorfitClient
                    .getInstance()
                    .getApi()
                    .getOrganisationScholarships(uidJSON.toString());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String s = response.body().string();
//                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        Log.d("JSON-RESPONSE", s);

                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = jsonObject.getJSONArray("scholarships");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject o = jsonArray.getJSONObject(i);
                            name.add(o.getString("name"));
                            dev.add(o.getString("description"));
                        }

                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(name, dev, getActivity().getApplicationContext());
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_org, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_scholarship:
                Intent i = new Intent(getActivity().getApplicationContext(), AddFormActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
