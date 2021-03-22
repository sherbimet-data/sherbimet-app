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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.sherbimet.Adapter.SubServiceAdapter;
import com.project.sherbimet.ApiHelper.JsonField;
import com.project.sherbimet.ApiHelper.WebURL;
import com.project.sherbimet.Listner.SubServiceItemClickListner;
import com.project.sherbimet.Model.SubService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubServiceActivity extends AppCompatActivity implements SubServiceItemClickListner {

    RecyclerView rvSubService;
    ArrayList<SubService> listSubService;
    private String id,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_service);

        rvSubService = findViewById(R.id.rvSubService);

        Intent intent = getIntent();
        id = intent.getStringExtra(JsonField.SERVICE_ID);
        Log.d("res",id);
        name = intent.getStringExtra(JsonField.SERVICE_NAME);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SubServiceActivity.this);
        rvSubService.setLayoutManager(linearLayoutManager);

        grtSubService(id);
    }

    private void grtSubService(final String id) {

        listSubService = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.SUB_SERVICES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response);

            }
        }, new Response.ErrorListener() {
           @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(JsonField.SERVICE_ID,id);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(SubServiceActivity.this);
        requestQueue.add(stringRequest);

    }

    private void parseJson(String s) {
        Log.d("res",s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            int success = jsonObject.optInt(JsonField.SERVICE_FLAG);
            if (success == 1)
            {
                JSONArray jsonArray = jsonObject.optJSONArray(JsonField.SUB_SERVICES_ARRAY);
                if (jsonArray.length()>0)
                {
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject objService = jsonArray.optJSONObject(i);
                        String subServiceId = objService.optString(JsonField.SUB_SERVICES_ID);
                        String subServiceName = objService.optString(JsonField.SUB_SERVICES_NAME);

                        SubService subService = new SubService();
                        subService.setService_id(subServiceId);
                        subService.setSub_service_name(subServiceName);
                        listSubService.add(subService);
                    }
                    SubServiceAdapter subServiceAdapter = new SubServiceAdapter(SubServiceActivity.this,listSubService);
                    subServiceAdapter.setSubServiceItemClickListner(SubServiceActivity.this);
                    rvSubService.setAdapter(subServiceAdapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnItemClicked(ArrayList<SubService> listSubService, int position) {

        Intent intent = new Intent(SubServiceActivity.this,PackageActivity.class);
        SubService subService = listSubService.get(position);
        String id = subService.getSub_service_id();
        //Log.d("res",id);
        String name = subService.getSub_service_name();
        intent.putExtra(JsonField.SUB_SERVICES_ID,id);

        intent.putExtra(JsonField.SUB_SERVICES_NAME,name);
        startActivity(intent);

    }
}