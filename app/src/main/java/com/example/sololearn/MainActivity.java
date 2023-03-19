package com.example.sololearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email,pass1;
    String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog pDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUSer;

    EditText username;
    EditText password;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = this.<EditText>findViewById(R.id.username);
        pass1 = findViewById(R.id.password);
        pDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUSer = mAuth.getCurrentUser();


        TextView signupText;
        signupText = findViewById(R.id.signupText);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(MainActivity.this,registeration.class);
                reg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(reg);
            }
        });
    }

    private void perforLogin() {
        String eId = email.getText().toString();
        String password = pass1.getText().toString();
        if(!eId.matches(emailPattern)){
            email.setError("Enter Correct Email");
        }else if(password.isEmpty() || password.length() < 6){
            pass1.setError("Enter Proper Password");
        }else{
            pDialog.setMessage("Please Wait While Login");
            pDialog.setTitle("Login");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

            mAuth.signInWithEmailAndPassword(eId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        pDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else{
                        pDialog.dismiss();
                        Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void sendUserToNextActivity() {
        Intent newInt = new Intent(MainActivity.this,dashboard.class);
        newInt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newInt);
    }
}