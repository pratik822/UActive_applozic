package com.uactiv.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.uactiv.R;
import com.uactiv.activity.In_App_Browser;
import com.uactiv.activity.MainActivity;
import com.uactiv.controller.ResponseListener;
import com.uactiv.network.ImageUpLoader;
import com.uactiv.network.RequestHandler;
import com.uactiv.utils.AppConstants;
import com.uactiv.utils.SharedPref;
import com.uactiv.utils.Utility;
import com.uactiv.widgets.CustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Explorar_ extends Fragment implements View.OnClickListener, Response.ErrorListener, ResponseListener,AppConstants.SharedConstants,AppConstants.urlConstants {

    View view;
    private ConstraintLayout buddyup_layout, pickup_layout, rewards_layout, mypickup;
    private CustomTextView tv_cityname;
    private FrameLayout frame;
    ImageView imageView17s, imageView15;
    Bundle bn;
    Map<String, Object> maps;
    Bundle extras;
    String gcm_token = "GCM_token";
    String isStaging = "isStaging";
    String registration = "registration";
    EditText  edt;
    AlertDialog dialog;
    String[] activities;
    ArrayList<String> activity_list = new ArrayList<String>();
    ArrayList<HashMap<String, String>> pickUpAutoList = new ArrayList<HashMap<String, String>>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_explorar_, null);
        setUI();
        extras = this.getArguments();
       // setalertDialog();
        if (extras != null) {
            if( extras.getSerializable("itemparams")!=null){
                Log.d("mydata",new Gson().toJson(extras.getSerializable("itemparams")));
                maps = (Map<String, Object>) extras.getSerializable("itemparams"); //Obtaining data
         //       setalertDialog();
            }
        }
        getActivtyList();

        //get list of activities

        return view;

    }
    private void getActivtyList() {

        if (Utility.isConnectingToInternet(getActivity())) {
            try {
                Map<String, String> stringMap = new HashMap<>();
                RequestHandler.getInstance().stringRequestVolley(getActivity(), AppConstants.getBaseUrl(SharedPref.getInstance().getBooleanValue(getActivity(), isStaging)) + getactivityList, stringMap, this, 3);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void setUI() {
        buddyup_layout = (ConstraintLayout) view.findViewById(R.id.buddyup_layout);
        //pickup_layout = (ConstraintLayout) view.findViewById(R.id.pickup_layouts);
        mypickup = (ConstraintLayout) view.findViewById(R.id.mypickup);
        frame = (FrameLayout) view.findViewById(R.id.frame);
        frame.setOnClickListener(this);
        mypickup.setOnClickListener(this);
        rewards_layout = (ConstraintLayout) view.findViewById(R.id.rewards_layout);
        tv_cityname = (CustomTextView) view.findViewById(R.id.tv_cityname);
        imageView17s = (ImageView) view.findViewById(R.id.imageView17s);
        imageView15 = (ImageView) view.findViewById(R.id.imageView15);
        // imageView15.setImageResource(R.drawable.mumbai);
        imageView17s.setOnClickListener(this);
        buddyup_layout.setOnClickListener(this);
        //pickup_layout.setOnClickListener(this);
        rewards_layout.setOnClickListener(this);
    }


    public void setalertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.register_popup, null);
        Button button6 = (Button) view.findViewById(R.id.button6);
        edt = (EditText) view.findViewById(R.id.edt_email);
        

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edt.getText().toString().isEmpty()){
                    if (maps != null) {
                        Log.e(" send", maps.toString());
                        imageUpload(maps);
                        dialog.dismiss();
                    } 
                }else{
                    Toast.makeText(getActivity(), "please provide email", Toast.LENGTH_SHORT).show();
                }
              
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();


    }

    private void imageUpload(Map<String, Object> param) {
        //Extras
        if (Utility.isNullCheck(SharedPref.getInstance().getStringVlue(getActivity(), gcm_token))) {
            param.put("device_id", SharedPref.getInstance().getStringVlue(getActivity(), gcm_token));
        } else {
            param.put("device_id", "");
        }
        param.put("device_id", SharedPref.getInstance().getStringVlue(getActivity(), gcm_token));
        param.put("device_type", "0"); //For android 0,iOs 1
        param.put("referral_code", "");
        param.put("email",edt.getText().toString());
        Log.d("custparam",new Gson().toJson(param));
        if (Utility.isNullCheck(param.toString())) {

            ImageUpLoader imageUpLoader = new ImageUpLoader(getActivity());
          //  Log.e("Selected Path", ":" + path);
            imageUpLoader.setFile("");
            imageUpLoader.getResponse(AppConstants.getBaseUrl(SharedPref.getInstance().getBooleanValue(getActivity(), isStaging)) + registration, this, param, 0);

        }
    }

/*    public void setalertDialog() {
        final AlertDialog dialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final ArrayList cityArray = new ArrayList();
        cityArray.add("Mumbai");
        cityArray.add("Pune");
        cityArray.add("Delhi");
        cityArray.add("Gurgaon");
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_listview, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        ListView alert_list = (ListView) view.findViewById(R.id.alert_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, cityArray);
        alert_list.setAdapter(adapter);
        alert_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_cityname.setText(cityArray.get(i) + "");
                if (i == 0) {
                    imageView15.setImageResource(R.drawable.mumbai);
                } else if (i == 1) {
                    imageView15.setImageResource(R.drawable.pune);
                } else if (i == 2) {
                    imageView15.setImageResource(R.drawable.delhi);
                } else if (i == 3) {
                    imageView15.setImageResource(R.drawable.gruru);
                }

                dialog.dismiss();
            }
        });


    }*/


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buddyup_layout:
                Fragment fragment = new Home();
                bn = new Bundle();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    bn.putString("navi", "buddy");
                    bn.putString("isFromExplorar", "true");
                    fragment.setArguments(bn);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
                break;
            case R.id.pickup_Layout:
              /*  Fragment fragment_pickup = new Home();
                bn = new Bundle();
                if (fragment_pickup != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    bn.putString("navi", "pick");
                    bn.putString("isFromExplorar", "true");
                    fragment_pickup.setArguments(bn);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment_pickup).addToBackStack(null);
                    fragmentTransaction.commit();
                }*/
                break;
            case R.id.rewards_layout:
                Utility.setEventTracking(getActivity(), "Soccer League", "open in app browser");
                Intent url = new Intent(getActivity(), In_App_Browser.class);
                url.putExtra("url", "http://uactiv.com/upgrade/sst/cms/");
                url.putExtra("title", "Super Soccer Tournament");
                getActivity().startActivity(url);
                break;

            case R.id.mypickup:
                Fragment fragment_pickup = new Home();
                bn = new Bundle();
                if (fragment_pickup != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    bn.putString("navi", "pick");
                    bn.putString("isFromExplorar", "true");
                    fragment_pickup.setArguments(bn);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment_pickup).addToBackStack(null);
                    fragmentTransaction.commit();
                }
                break;

            case R.id.imageView17s:
                Fragment fragment_pickups = new Home();
                bn = new Bundle();
                if (fragment_pickups != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    bn.putString("navi", "pick");
                    bn.putString("isFromExplorar", "true");
                    fragment_pickups.setArguments(bn);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment_pickups).addToBackStack(null);
                    fragmentTransaction.commit();
                }
                break;

            case R.id.frame:
                // setalertDialog();
                break;
        }

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void successResponse(String jsonObject, int flag) throws JSONException {
  JSONObject j=new JSONObject(jsonObject);
        if (jsonObject != null) {

            if (j.optString(resultcheck).equals(KEY_TRUE)) {

                JSONArray skillistArray = j.optJSONArray(KEY_DETAIL);
                if (skillistArray != null && skillistArray.length() > 0) {
                    SharedPref.getInstance().setSharedValue(getActivity(), Api_skill_list, skillistArray.toString());
                    // searchKeySkills(skillistArray);
                }
            }
        }

    }

    @Override
    public void successResponse(JSONObject jsonObject, int flag) {
        if (jsonObject != null) {

            if (jsonObject.optString(resultcheck).equals(KEY_TRUE)) {

                JSONArray skillistArray = jsonObject.optJSONArray(KEY_DETAIL);
                if (skillistArray != null && skillistArray.length() > 0) {
                    SharedPref.getInstance().setSharedValue(getActivity(), Api_skill_list, skillistArray.toString());
                   // searchKeySkills(skillistArray);
                }
            }
        }
    }

    @Override
    public void errorResponse(String errorResponse, int flag) {

    }

    @Override
    public void removeProgress(Boolean hideFlag) {

    }


    //concate skills to show in buddyup list
    private void searchKeySkills(JSONArray skillistArray) {


        String skillSet = SharedPref.getInstance().getStringVlue(getActivity(), Api_skill_list);

        //  Log.e("skillSet",":"+skillSet);

        if (skillistArray != null) {

            try {
                if (skillistArray != null && skillistArray.length() > 0 && activity_list.size() <= 0) {

                    activity_list.clear();

                    for (int k = 0; k < skillistArray.length(); k++) {
                        activity_list.add(skillistArray.optJSONObject(k).optString("activity"));
                    }

                    activities = new String[activity_list.size()];
                    activities = activity_list.toArray(activities);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            if (activities != null && activities.length > 0) {

                for (int i = 0; i < activities.length; i++) {
                    HashMap<String, String> pickUpMap = new HashMap<String, String>();
                    pickUpMap.put("pickUpNames", activities[i]);
                    pickUpAutoList.add(pickUpMap);
                }

            }
        }


    }
/*
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            }

        } else {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(Explorar_.this);
            builder1.setMessage("Are you sure you want to exit the app?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory(Intent.CATEGORY_HOME);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);
                            dialog.cancel();

                        }
                    });
            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();


        }


    }*/


}
