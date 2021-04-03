package com.project.sherbimet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.sherbimet.ApiHelper.JsonField;
import com.project.sherbimet.ApiHelper.WebURL;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rbRequireService,rbServiceProvider;
    EditText etName,etMobileNumber,etEmail,etPassword,etConfirmPassword,etAddress;
    Button btnSignUp;
    String UserType;
    Spinner spArea;
    TextView tvArea;

    String areaName[] = {"Select Area","Colaba","Bandra","Borivali","Ghatkopar","Mulun","Dadar","Thane","Church Gate","Marins","Juhu","Valkeshvar","Andheri","Parel","Malad","Malabar hills","Versova",""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //objects initialisation
        rbRequireService = findViewById(R.id.rbRequireService);
        rbServiceProvider = findViewById(R.id.rbServiceProvider);
        etName = findViewById(R.id.etName);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etAddress = findViewById(R.id.etAddress);
        btnSignUp = findViewById(R.id.btnSignUp);
        spArea = findViewById(R.id.spArea);
        tvArea = findViewById(R.id.tvArea);

        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_list_item_1,areaName);

        spArea.setAdapter(arrayAdapter);

        btnSignUp.setOnClickListener(this);
    }

    private boolean checkUserName() // Validation of UserName
    {
        boolean isUserNameValid = false;
        if (etName.getText().toString().trim().length() <= 0)
        {
            etName.setError("Enter Name");
        }
        else
        {
            isUserNameValid = true;
        }

        return isUserNameValid;
    }

    private boolean checkUserArea()
    {
        boolean isUserAreaValid = false;
        if (String.valueOf(spArea.getSelectedItem()) == "Select Area")
        {
            tvArea.setError("Select Area");
        }
        else
        {
            isUserAreaValid = true;
        }
        return isUserAreaValid;
    }

    private boolean checkUserType() // Validation of UserType
    {
        boolean isUserTypeChecked;
        isUserTypeChecked = rbRequireService.isChecked() || rbServiceProvider.isChecked();

        if (rbServiceProvider.isChecked())
        {
            UserType = "Service Provider";
        }
        else if (rbRequireService.isChecked())
        {
            UserType = "Require Service";
        }

        if (!isUserTypeChecked)
        {
            Toast.makeText(this, "Select User Type", Toast.LENGTH_SHORT).show();
        }

        /*if (rbServiceProvider.isChecked() == false || rbRequireService.isChecked() == false)
        {
            rbServiceProvider.setError("Please Select User Type");
        }
        else
        {
            isUserTypeChecked = true;
        }*/
        return isUserTypeChecked;
    }

    private boolean checkMobileNumber() // Validation of MobileNumber
    {
        boolean isMobileNumberValid = false;
        if (etMobileNumber.getText().toString().trim().length() <= 0)
        {
            etMobileNumber.setError("Enter Mobile Number");
        }
        else if (etMobileNumber.getText().toString().length() == 10)
        {
            isMobileNumberValid = true;
        }
        else
        {
            etMobileNumber.setError("Enter Correct Mobile Number");
        }
        return isMobileNumberValid;
    }

    private boolean checkEmail() // Validation of Email
    {
        boolean isEmailValid = false;
        if (etEmail.getText().toString().trim().length() <= 0)
        {
            etEmail.setError("Enter Email");
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches())
        {
            isEmailValid = true;
        }
        else
        {
            etEmail.setError("Enter Correct Email");
        }
        return isEmailValid;
    }

    public boolean checkPassword() {
        boolean isPasswordValid = false;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$";

        if (etPassword.getText().toString().trim().length() <= 0) {
            etPassword.setError("Enter Password");
        }else if (Pattern.compile(PASSWORD_PATTERN).matcher(etPassword.getText().toString().trim()).matches()) {
            isPasswordValid = true;
        } else {
            etPassword.setError("Enter at least one digit,one lower case letter,one upper case letter,one special character and length must be 5");
        }
        return isPasswordValid;
    }



    /*private boolean checkPassword() // Validation of Password
    {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&*_+-])(?=.*\\s+$).{8,}$";

        boolean isPasswordValid = false;

        if (etPassword.getText().toString().trim().length() <=0)
        {
            etPassword.setError("Enter Password");
        }
        else if (Pattern.compile(PASSWORD_PATTERN).matcher(etPassword.getText().toString().trim()).matches())
        {
            isPasswordValid = true;
        }
        else
        {
            etPassword.setError("Password must be atleast 8 digit, Contains one digit, one upper case, lower case, special character");
        }
        return isPasswordValid;
    }*/ // Validation of Password

    private boolean checkConfirmPassword() // Validation of ConfirmPassword
    {
        boolean isConfirmPasswordValid = false;

        if (etConfirmPassword.getText().toString().trim().length() <= 0)
        {
            etConfirmPassword.setError("Enter Confirm Password");
        }
        else if (etConfirmPassword.getText().toString().equals(etPassword.getText().toString()))
        {
            isConfirmPasswordValid = true;
        }
        else
        {
            etConfirmPassword.setError("Entered Password Doesn't Match");
        }
        return isConfirmPasswordValid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignUp:

                if (checkUserName() && checkMobileNumber() && checkUserType() && checkEmail() && checkPassword() && checkConfirmPassword() && checkUserArea())
                {
//                    Toast.makeText(this, "Validation Checked....", Toast.LENGTH_SHORT).show();
                    sendSignUpRequest();
                }

            break;
        }
    }

    private void sendSignUpRequest() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.SIGN_UP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parseSignUpResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(JsonField.USER_NAME,etName.getText().toString());
                params.put(JsonField.USER_MOBILE,etMobileNumber.getText().toString());
                params.put(JsonField.USER_EMAIL,etEmail.getText().toString());
                params.put(JsonField.USER_PASSWORD,etPassword.getText().toString());
                params.put(JsonField.USER_ADDRESS,etAddress.getText().toString());
                params.put(JsonField.USER_AREA,String.valueOf(spArea.getSelectedItem()));
                Log.d("Area",String.valueOf(spArea.getSelectedItem()));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
        requestQueue.add(stringRequest);

    }

    private void parseSignUpResponse(String response) {
        Log.d("RESPONSE",response);
        String response_flag = 
    }
}