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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText email_username, password;
    Button register;

    DatabaseReference mReferenceRoot;
    DatabaseReference mUsersReference;
    FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private List<Users> users_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        users_list = new ArrayList<Users>();
        email_username = findViewById(R.id.username_editText);
        password = findViewById(R.id.password_editText);
        register = findViewById(R.id.register_btn);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceRoot = mDatabase.getInstance().getReference();
        mUsersReference = mReferenceRoot.child("Users");

        mUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot keyNode: dataSnapshot.getChildren()){
                    int count = 0;
                    Users user = keyNode.getValue(Users.class);
                    users_list.add(user);
                    //Toast.makeText(LoginActivity.this, "name"+user.getUsername()+" COUNT: "+count+" KEY: "+keyNode.getKey(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em=email_username.getText().toString();
               for (int i = 0; i<users_list.size(); i++){
                   if(email_username.getText().toString().equals(users_list.get(i).getUsername())){
                    em = users_list.get(i).getEmail();
                    break;
                   }

               }


                    mAuth.signInWithEmailAndPassword(em,password.getText().toString())
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
