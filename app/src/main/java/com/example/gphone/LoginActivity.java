package com.example.gphone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gphone.DataClass.WardenDetails;
import com.google.firebase.database.*;

public class LoginActivity extends AppCompatActivity {

    EditText PhoneNumber;
    EditText Password;
    Button SignIn;
    String phoneNumber, password;
    FirebaseDatabase databases = FirebaseDatabase.getInstance();
    DatabaseReference database = databases.getReference();
    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login",Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean ph = sharedPreferences.getBoolean("login",false);
        setContentView(R.layout.activity_login);
        Password = (EditText) findViewById(R.id.password);
        PhoneNumber = (EditText) findViewById(R.id.phonenum);
        SignIn = (Button) findViewById(R.id.sigin);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = PhoneNumber.getText().toString().trim();
                password = Password.getText().toString().trim();
                final DatabaseReference wardenDatabase = database.child("databases").child("wardendatabase").child(phoneNumber);
                wardenDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        WardenDetails wardenDetails = dataSnapshot.getValue(WardenDetails.class);
                        if (dataSnapshot.getValue() != null) {
                            if (wardenDetails.getPassword().equals(password)) {
                                Intent i = new Intent(getBaseContext(), WardenActivity.class);
                                i.putExtra("name", wardenDetails.getName());
                                i.putExtra("balanceamount", wardenDetails.getAmountCredited());
                                i.putExtra("rechargedamount", wardenDetails.getAmountRecharged());
                                i.putExtra("phonenumber", phoneNumber);
                                i.putExtra("schoolname", wardenDetails.getSchoolname());
                                startActivity(i);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("phonenum", phoneNumber);
                                editor.apply();
                                finish();
                            } else {
                                Password.setError("Incorrect Password");
                                Password.setFocusable(true);
                            }
                        } else {
                            PhoneNumber.setFocusable(true);
                            PhoneNumber.setError("Enter valid Phone no.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getBaseContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
