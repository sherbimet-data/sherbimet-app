package com.project.sherbimet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.sherbimet.Adapter.PackageAdapter;
import com.project.sherbimet.ApiHelper.JsonField;
import com.project.sherbimet.ApiHelper.WebURL;
import com.project.sherbimet.Model.Package;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PackageActivity extends AppCompatActivity {

    RecyclerView rvPackage;
    ArrayList<Package> listPackage;
    private String id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        rvPackage = findViewById(R.id.rvPackage);

        Intent intent = getIntent();
        id = intent.getStringExtra(JsonField.SUB_SERVICES_ID);
       // Log.d("res",id);
        name = intent.getStringExtra(JsonField.SUB_SERVICES_NAME);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PackageActivity.this);
        rvPackage.setLayoutManager(linearLayoutManager);

        getPackage(id);


    }

    private void getPackage(String id) {

        listPackage = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.PACKAGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseJson(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> map = new HashMap<>();

                map.put(JsonField.SUB_SERVICES_ID,id);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(PackageActivity.this);
        requestQueue.add(stringRequest);
    }

    private void parseJson(String s) {
Log.d("res",s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int success = jsonObject.optInt(JsonField.SERVICE_FLAG);
            if (success == 1)
            {
                JSONArray jsonArray = jsonObject.optJSONArray(JsonField.PACKAGE_ARRAY);
                if (jsonArray.length()>1)
                {
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject objSubService = jsonArray.optJSONObject(i);
                        String package_id = objSubService.optString(JsonField.PACKAGE_ID);
                        String package_name = objSubService.optString(JsonField.PACKAGE_NAME);
                        String package_details = objSubService.optString(JsonField.PACKAGE_DETAILS);
                        String package_duration = objSubService.optString(JsonField.PACKAGE_DURATION);
                        String package_price = objSubService.optString(JsonField.PACKAGE_PRICE);

                        Package apackage = new Package();
                        apackage.setPackage_details(package_details);
                        apackage.setPackage_duration(package_duration);
                        apackage.setPackage_id(package_id);
                        apackage.setPackage_name(package_name);
                        apackage.setPackage_price(package_price);
                        listPackage.add(apackage);

                    }
                    PackageAdapter packageAdapter = new PackageAdapter(PackageActivity.this,listPackage);
                    rvPackage.setAdapter(packageAdapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}