package com.bumblee.idfscholarship;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

import com.bumblee.idfscholarship.Adapters.RecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentHomeFragment extends Fragment {

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

            View view = inflater.inflate(R.layout.fragment_student_home, container, false);
            Toolbar toolbar = view.findViewById(R.id.toolbar);
            rGroup = (RadioGroup) view.findViewById(R.id.fetchRG);
            checkedRadioButton = (RadioButton)rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        recyclerView = view.findViewById(R.id.recycler_view);
        name = new ArrayList<String>();
        dev = new ArrayList<String>();
        sid = new ArrayList<Integer>();

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("IDF Scholarships");
        Log.d("CHECKED-BUTTON-1", checkedRadioButton.getText().toString());

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        JSONObject uidObject = new JSONObject();
        try {
            uidObject.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<ResponseBody> call = RetorfitClient
                .getInstance()
                .getApi()
                .getEligible(uidObject.toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String s = response.body().string();
                    Log.d("JSON-DATA", s);
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("scholarships");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);
                        name.add(o.getString("name"));
                        dev.add(o.getString("description"));
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

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    dialog.show();
                    // Changes the textview's text to "Checked: example radiobutton text"
                    Log.d("CHECKED-BUTTON", checkedRadioButton.getText().toString());
                    if (checkedRadioButton.getText().toString().equalsIgnoreCase("Eligible")){

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        JSONObject uidObject = new JSONObject();
                        try {
                            uidObject.put("uid", uid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Call<ResponseBody> call = RetorfitClient
                                .getInstance()
                                .getApi()
                                .getEligible(uidObject.toString());

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {

                                    name = new ArrayList<>();
                                    dev = new ArrayList<>();
                                    sid = new ArrayList<>();
                                    String s = response.body().string();
                                    Log.d("JSON-DATA", s);
                                    JSONObject jsonObject = new JSONObject(s);
                                    JSONArray jsonArray = jsonObject.getJSONArray("scholarships");
                                    for (int i=0; i<jsonArray.length(); i++){
                                        JSONObject o = jsonArray.getJSONObject(i);
                                        name.add(o.getString("name"));
                                        dev.add(o.getString("description"));
                                        sid.add(o.getInt("id"));
                                    }
//                                    adapter.notifyDataSetChanged();
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

                    } else {

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        JSONObject uidObject = new JSONObject();
                        try {
                            uidObject.put("uid", uid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Call<ResponseBody> call = RetorfitClient
                                .getInstance()
                                .getApi()
                                .getAll(uidObject.toString());

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    name = new ArrayList<>();
                                    dev = new ArrayList<>();
                                    sid = new ArrayList<>();

                                    String s = response.body().string();
                                    Log.d("JSON-DATA", s);
                                    JSONObject jsonObject = new JSONObject(s);
                                    JSONArray jsonArray = jsonObject.getJSONArray("scholarships");
                                    for (int i=0; i<jsonArray.length(); i++){
                                        JSONObject o = jsonArray.getJSONObject(i);
                                        name.add(o.getString("name"));
                                        dev.add(o.getString("description"));
                                        sid.add(o.getInt("id"));
                                    }

//                                    adapter.notifyDataSetChanged();
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
                    }
                }
            }
        });

        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        MenuInflater menuInflater = getActivity().getMenuInflater().inflate(R.menu.menu_search, menu);
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.menuSearch) {

            SearchView searchView=(SearchView)item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getActivity().getApplicationContext(),query,Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


        }else if (item.getItemId() == R.id.filter) {
//            final Dialog dialog = new Dialog(getActivity().getApplicationContext());
//            dialog.setContentView(R.layout.activity_dialog);
//
//            // Set dialog title
//            dialog.setTitle("Filter Box");
//            dialog.show();
//            Toast.makeText(getActivity().getApplicationContext(), "App developed by Ashwathy", Toast.LENGTH_SHORT).show();

            showDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    protected void showDialog(){

        final Button btnFilter;
        final EditText etIncome1;
        final TextView etDate1;
        final Spinner spnState1,spnReligion1,spnDegree1,spnCategory1,spnCourse1;
        final CheckBox chkPhysically1;
        final RadioGroup genderRG1;
        final Calendar myCalendar = Calendar.getInstance();
        final ProgressBar dialogProgressBar;

        final Dialog filterDialog = new Dialog(getActivity());
        filterDialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.activity_dialog, null);
        filterDialog.setContentView(view);

        etIncome1=(EditText) view.findViewById(R.id.etIncome1);
        spnState1=(Spinner) view.findViewById(R.id.spnState1);
        spnReligion1=(Spinner) view.findViewById(R.id.spnReligion1);
        spnDegree1=(Spinner) view.findViewById(R.id.spnDegree1);
        spnCategory1=(Spinner) view.findViewById(R.id.spnCategory1);
        spnCourse1=(Spinner) view.findViewById(R.id.spnCourse1);
        chkPhysically1=(CheckBox) view.findViewById(R.id.chkPhysically1);
        btnFilter=(Button) view.findViewById(R.id.btnFilter);
        genderRG1=(RadioGroup) view.findViewById(R.id.genderRG1);
        dialogProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

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
        ArrayAdapter<String> ft1 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, sState);
        spnState1.setAdapter(ft1);

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
        ArrayAdapter<String> ft2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, sReligion);
        spnReligion1.setAdapter(ft2);

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
        ArrayAdapter<String> ft3 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, sDegree);
        spnDegree1.setAdapter(ft3);

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
        ArrayAdapter<String> ft4 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, sCategory);
        spnCategory1.setAdapter(ft4);

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
        ArrayAdapter<String> ft5 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, sCourse);
        spnCourse1.setAdapter(ft5);


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date;

                dialogProgressBar.setVisibility(View.VISIBLE);
                btnFilter.setVisibility(View.INVISIBLE);
                int pos1 = spnState1.getSelectedItemPosition();
                String state = sState.get(pos1).toString();
                int pos2 = spnReligion1.getSelectedItemPosition();
                String religion = sReligion.get(pos2).toString();
                int pos3 = spnDegree1.getSelectedItemPosition();
                String current_course = sDegree.get(pos3).toString();
                int pos4 = spnCategory1.getSelectedItemPosition();
                String category = sCategory.get(pos4).toString();
                int pos5 = spnCourse1.getSelectedItemPosition();
                String course_interested_in = sCourse.get(pos5).toString();
                String PC="";
                int id=genderRG1.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton) genderRG1.findViewById(id);
                String gender=rb.getText().toString();
                if(chkPhysically1.isChecked()){
                    PC="Yes";
                }
                else PC="No";
                String annual_income=etIncome1.getText().toString();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                JSONObject filterDetails = new JSONObject();

                try {
                    filterDetails.put("state", state);
                    filterDetails.put("religion", religion);
                    filterDetails.put("current_course", current_course);
                    filterDetails.put("category", category);
                    filterDetails.put("course", course_interested_in);
                    filterDetails.put("phsyically_challenged", PC);
                    filterDetails.put("gender", gender);
                    filterDetails.put("maximum_annual_income", annual_income);
                    filterDetails.put("uid", uid);

                    Log.d("SUBMISSION", filterDetails.toString());
                    Call<ResponseBody> call = RetorfitClient
                            .getInstance()
                            .getApi()
                            .getScholarshipFilter(filterDetails.toString());

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s = response.body().string();
                                Log.d("JSON-DATA", s);
//                                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            btnFilter.setVisibility(View.VISIBLE);
                            dialogProgressBar.setVisibility(View.INVISIBLE);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            btnFilter.setVisibility(View.VISIBLE);
                            dialogProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                filterDialog.dismiss();

            }
        });

//        TextView edit = (TextView) view.findViewById(R.id.edit);
//        TextView delete = (TextView) view.findViewById(R.id.delete);


//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Do something
//
//            }
//        });
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Do something
//
//            }
//        });



        filterDialog.show();
    }


}




