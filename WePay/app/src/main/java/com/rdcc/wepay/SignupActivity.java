package com.rdcc.wepay;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.Cloud.User;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_signup)
    Button _signupButton;
    @InjectView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        //the following code will take the inputs the user entered and store it into strings.
        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();


        // TODO: Implement your own signup logic here.


        //the following code will check for repetition during signup and determine whether or not the user is allowed to register

        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
        query.whereEqualTo("Email", email); //Under the "Email" column in the database, we will see if what the email the user entered exists
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {   //the email the user is trying to register doesn't exist so they're allowed to use it
//                   // onSignupSuccess();
                    Toast.makeText(getBaseContext(), "Registration Successful!!", Toast.LENGTH_LONG).show();
                    final ParseObject User = new ParseObject("UserData");
                    User.put("Name", name);
                    User.put("Email", email);
                    User.put("password", password);
                    try{
                        User.save();
                    }catch(Exception f){
                        f.printStackTrace();
                    }
                    CloudHandler cloud = new CloudHandler();
                    User user = new User();
                    user.setID(User.getObjectId());
                    user.setName(name);
                    user.setEmail(email);
                    cloud.storeUserinDB(user);

                    Intent b = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(b);

                } else { //if it does exist, we will check if the password the user entered matches.

                    Toast.makeText(getBaseContext(), "Email you want to register with already exists...", Toast.LENGTH_LONG).show();

                }
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss(); //this will determine how long the progress dialog is ran for
                    }
                }, 3000);
    }


    public void onSignupSuccess() {//this function is called when signup is sucessful
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {//this function is called when signup has failed
        Toast.makeText(getBaseContext(), "Sign Up Fail", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() { //this function will handle any errors in terms of user inputs
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}