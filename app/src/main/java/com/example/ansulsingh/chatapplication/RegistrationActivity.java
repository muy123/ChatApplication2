package com.example.ansulsingh.chatapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText mDisplayName,mEmail,mPassword;
    private Button mCreateBtn;

    private Toolbar mToolbar;
    private FirebaseAuth mAuth;    // Firebase
    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mToolbar = (Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);        // ye back janne ke liye arrow ke liye h iski bakchodi manifest file m parent
                                                                      // attribute m bhi ki h

        mRegProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mDisplayName = (TextInputEditText)findViewById(R.id.reg_display_name);
        mEmail = (TextInputEditText)findViewById(R.id.reg_email);
        mPassword = (TextInputEditText)findViewById(R.id.reg_password);
        mCreateBtn = (Button)findViewById(R.id.reg_create_btn);
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name = mDisplayName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(!TextUtils.isEmpty(display_name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    mRegProgress.setTitle("Register User");
                    mRegProgress.setMessage("We entertain your new account request");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    register_user(display_name,email,password);
                }

            }
        });
    }
    private void register_user(String display_name, String email, String password)   // Firebase code
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            mRegProgress.dismiss();
                            Intent mainIntent = new Intent(RegistrationActivity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                        else
                        {
                            mRegProgress.hide();
                            Toast.makeText(RegistrationActivity.this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}