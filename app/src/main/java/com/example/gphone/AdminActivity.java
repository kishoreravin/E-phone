package com.example.gphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gphone.DataClass.WardenDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    EditText PhoneNumber, Password, Name, Schoolname;
    Button AddWarden;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        PhoneNumber = findViewById(R.id.warden_ph);
        Password = findViewById(R.id.w_password);
        Name = findViewById(R.id.name);
        Schoolname = findViewById(R.id.schoolname);
        AddWarden = findViewById(R.id.add_new_warden);
        AddWarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference wardenRef = databaseReference.child("databases").child("wardendatabase").child(PhoneNumber.getText().toString().trim());
                String ph = PhoneNumber.getText().toString().trim();
                String pass = Password.getText().toString().trim();
                String name = Name.getText().toString().trim();
                String schlname = Schoolname.getText().toString().trim();

                if(!ph.equals("") && !pass.equals("") && !name.equals("") && !schlname.equals("")){
                    WardenDetails wardenDetails = new WardenDetails(name,pass,schlname,0,0);
                    wardenRef.setValue(wardenDetails);
                }else {
                    Toast.makeText(getBaseContext(),"Enter All details corrrectly",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
