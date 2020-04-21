package com.mustafa.picaword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText email_username, password;
    Button register;

    DatabaseReference mReferenceRoot;
    FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_username = findViewById(R.id.username_editText);
        password = findViewById(R.id.password_editText);
        register = findViewById(R.id.register_btn);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceRoot = mDatabase.getReference("Users");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    mAuth.signInWithEmailAndPassword(email_username.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                  if(task.isSuccessful()){
                                      Toast.makeText(LoginActivity.this, "LOGGED IN ", Toast.LENGTH_SHORT).show();
                                  }
                                  else{
                                      Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                  }
                                }
                            });

            }
        });
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
