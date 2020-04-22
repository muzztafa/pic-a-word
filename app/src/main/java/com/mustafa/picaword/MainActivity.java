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

public class MainActivity extends AppCompatActivity {
    EditText email, password, username;
    Button register;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mReferenceRoot;
    DatabaseReference mUsersReference;
    private List<Users> users_list;


    @Override
    protected void onStart() {
        super.onStart();



        if(mAuth.getCurrentUser() != null){
            //handle accordingly
            Toast.makeText(this, "user already logged in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceRoot = mDatabase.getInstance().getReference();
        mUsersReference = mReferenceRoot.child("Users");

        users_list = new ArrayList<Users>();


        email = findViewById(R.id.email_editText);
        password = findViewById(R.id.password_editText);
        username = findViewById(R.id.username_editText);
        register = findViewById(R.id.register_btn);

        mAuth = FirebaseAuth.getInstance();

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
                boolean check = true;

                for (int i = 0; i<users_list.size(); i++){
                    if(users_list.get(i).getUsername().equals(username.getText().toString())){
                        check=false;
                        break;
                    }

                }

                if(check){
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //store relevant details in fireabase database

                                    Users user = new Users(email.getText().toString(),username.getText().toString());

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(MainActivity.this, "USER REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                }
                                else{
                                   Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
                else{
                    username.setError("Not available");
                }
            }

        });






    }
}
