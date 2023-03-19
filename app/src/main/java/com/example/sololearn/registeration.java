package com.example.sololearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registeration extends AppCompatActivity {

    EditText email,pass1,pass2;
    Button regBtn;
    TextView regText;
    String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog pDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUSer;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        email = findViewById(R.id.email);
        pass1 = findViewById(R.id.password1);
        pass2 = findViewById(R.id.password2);
        regBtn = findViewById(R.id.regButton);
        pDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUSer = mAuth.getCurrentUser();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }

            private void PerforAuth() {
                String eId = email.getText().toString();
                String password = pass1.getText().toString();
                String confirmPassword = pass2.getText().toString();
                if(!eId.matches(emailPattern)){
                    email.setError("Enter Correct Email");
                }else if(password.isEmpty() || password.length() < 6){
                    pass1.setError("Enter Proper Password");
                }else if(!password.equals(confirmPassword)){
                    pass2.setError("Password Does Not Matched");
                }else{
                    pDialog.setMessage("Please Wait While Registration");
                    pDialog.setTitle("Registration");
                    pDialog.setCanceledOnTouchOutside(false);
                    pDialog.show();

                    mAuth.createUserWithEmailAndPassword(eId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                pDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(registeration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            }else{
                                pDialog.dismiss();
                                Toast.makeText(registeration.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });






        regText = findViewById(R.id.regText);
        regText.setOnClickListener(view -> {
            Toast.makeText(registeration.this, "Please Login Here!", Toast.LENGTH_SHORT).show();
            Intent reg = new Intent(registeration.this,MainActivity.class);
            reg.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(reg);
        });



    }

    private void sendUserToNextActivity() {
        Intent newInt = new Intent(registeration.this,MainActivity.class);
        newInt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newInt);
    }
}