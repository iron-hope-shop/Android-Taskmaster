package com.example.projecttwo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private ConstraintLayout nestedScrollView;

    private EditText textEditTextEmail;
    private EditText textEditTextPassword;
    private Button buttonLoginButton;
    private Button buttonRegisterButton;
    private UserModel user;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private UserController databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {

        nestedScrollView = (ConstraintLayout) findViewById(R.id.linearLayout);
        textEditTextEmail = (EditText) findViewById(R.id.editTextTextPersonName);
        textEditTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        buttonLoginButton = (Button) findViewById(R.id.button);
        buttonRegisterButton = (Button) findViewById(R.id.button2);
    }

    private void initListeners() {
        buttonLoginButton.setOnClickListener(this);
        buttonRegisterButton.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new UserController(activity);
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                verifyFromSQLite();
                break;
            case R.id.button2:
                user = new UserModel();
                user.setEmail(textEditTextEmail.getText().toString().trim());
                user.setPassword(textEditTextPassword.getText().toString().trim());
                databaseHelper.addUser(user);
                Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                emptyInputEditText();
                break;
        }
    }

    private void verifyFromSQLite() {
        if (textEditTextEmail.getText().toString().trim().length() < 1) {
            Snackbar.make(nestedScrollView, getString(R.string.error_message_email), Snackbar.LENGTH_LONG).show();
        }
        if (textEditTextPassword.getText().toString().trim().length() < 1) {
            Snackbar.make(nestedScrollView, getString(R.string.error_message_password), Snackbar.LENGTH_LONG).show();
        }
        if (databaseHelper.checkUser(textEditTextEmail.getText().toString().trim()
                , textEditTextPassword.getText().toString().trim())) {

            Intent displayDataIntent = new Intent(activity, DataDisplayActivity.class);
            displayDataIntent.putExtra("EMAIL", textEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(displayDataIntent);

        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText() {
        textEditTextEmail.setText(null);
        textEditTextPassword.setText(null);
    }
}
