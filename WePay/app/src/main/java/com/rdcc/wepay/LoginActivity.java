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


//import com.parse.GetCallback;
//import com.parse.ParseObject;
//import com.parse.ParseQuery;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import junit.framework.Test;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    String useremail ="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();

            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString(); // we're setting email to the email address the user entered in the login screen
        useremail = email;
        final String password = _passwordText.getText().toString(); //we're setting password to the password the user entered in the login screen


        //This will go into our parse database and look for a database called "UserData"
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
        query.whereEqualTo("Email", email); //Under the "Email" column in the database, we will see if what the email the user entered exists
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, com.parse.ParseException e) {
                if (object == null) { // if it doesn't exists, we tell the user that it doesn't exist
                    Log.d("score", "The getFirst request failed.");
                    Toast.makeText(getBaseContext(), "Email entered does not exist...", Toast.LENGTH_LONG).show();

                } else { //if it does exist, we will check if the password the user entered matches.
                    Log.d("score", "Retrieved the object.");
                    String correctPassword = object.getString("password");
                     String name = object.getString("Name");
                    if (password.equals(correctPassword))//if the password does match with the username entered, bring them into the app and show a toast
                    {
                        onLoginSuccess(name);
                    } else { //if the password doesn't match, remain on the login scren and show a toast
                        onLoginFailed();

                    }

                }
            }
        });


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //  onLoginSuccess(); //this will display to the user that they're successfully logged in
                        //     onLoginFailed(); //this will display to the user that they failed to log in.
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP) {
//            if (resultCode == RESULT_OK) {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        // Disable going back to the MainActivity
//        moveTaskToBack(true);
//    }


    public void onLoginSuccess(String name) { //this function is called when login is successful
        Toast.makeText(getBaseContext(), "Login Success, Welcome " + name, Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
        Intent b = new Intent(getApplicationContext(), MainActivity.class);
        Log.d("User Login", useremail);
        b.putExtra("Email", useremail);
        startActivity(b);
        //finish();
    }

    public void onLoginFailed() { //this function is called when login has failed
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() { //this function is error handling for when user enters invalid inputs
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

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
