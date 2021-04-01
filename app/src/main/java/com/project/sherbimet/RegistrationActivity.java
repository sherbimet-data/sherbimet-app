package com.project.sherbimet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rbRequireService,rbServiceProvider;
    EditText etName,etMobileNumber,etEmail,etPassword,etConfirmPassword;
    Button btnSignUp;
    String UserType;
    Spinner spArea;
    TextView tvArea;

    String areaName[] = {"Select Area","Colaba","Bandra","Borivali","Ghatkopar","Mulun","Vasi","Mahim","Dadar","Sion","Thane","Church Gate","Marins","Juhu"};
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

    private boolean checkPassword() // Validation of Password
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
    }

    private boolean checkConfirmPassword() // Validation of ConfirmPassword
    {
        boolean isConfirmPasswordValid = false;

        if (etConfirmPassword.getText().toString().trim().length() <= 0)
        {
            etConfirmPassword.setError("Enter Confirm Password");
        }
        else if (etConfirmPassword.getText() == etPassword.getText())
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
                    Toast.makeText(this, "Validation Checked....", Toast.LENGTH_SHORT).show();
                }

            break;
        }
    }
}