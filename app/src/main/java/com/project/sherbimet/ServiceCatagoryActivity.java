package com.project.sherbimet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.sherbimet.Adapter.ServiceAdapter;
import com.project.sherbimet.ApiHelper.JsonField;
import com.project.sherbimet.ApiHelper.WebURL;
import com.project.sherbimet.Model.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceCatagoryActivity extends AppCompatActivity {

    RecyclerView rvService;
    ArrayList<Service> listService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_catagory);

        rvService = findViewById(R.id.rvService);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(ServiceCatagoryActivity.this);
        rvService.setLayoutManager(linearLayoutManager);

        getService();

}

    private void getService() {
        listService = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                WebURL.SERVICE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ServiceCatagoryActivity.this);
        requestQueue.add(stringRequest);

    }


    private void parseJson(String response) {
        Log.d("res",response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonField.SERVICE_FLAG);
            if (flag == 1)
            {
                JSONArray jsonArray = jsonObject.optJSONArray(JsonField.SERVICE_ARRAY);

                if (jsonArray.length() > 0)
                {
                    for(int i = 0;i<jsonArray.length();i++)
                    {
                        JSONObject objService = jsonArray.optJSONObject(i);
                        String serviseId = objService.getString(JsonField.SERVICE_ID);
                        String serviseName = objService.getString(JsonField.SERVICE_NAME);
                        String serviseImage = objService.getString(JsonField.SERVICE_PHOTO_PATH);

                        Service service = new Service();

                        service.setService_id(serviseId);
                        service.setService_name(serviseName);
                        service.setService_photo_path(serviseImage);

                        listService.add(service);

                    }

                    ServiceAdapter serviceAdapter =
                            new ServiceAdapter(ServiceCatagoryActivity.this,listService);
                    rvService.setAdapter(serviceAdapter);

                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}