package com.example.gphone;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gphone.DataClass.StudentDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class AddStudent extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    String schoolname = "";
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference studentDatabase;
    private ZXingScannerView zXingScannerView;
    EditText RegisterNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        Bundle bundle = getIntent().getExtras();
        schoolname = bundle.getString("schoolname");
        RegisterNum = findViewById(R.id.D_reg_num);
        final EditText Num1 = findViewById(R.id.D_num1);
        final EditText Num2 = findViewById(R.id.D_num2);
        final EditText Num3 = findViewById(R.id.D_num3);
        final EditText Num4 = findViewById(R.id.D_num4);
        Button Scan = findViewById(R.id.add_std_scan);
        Button Add = findViewById(R.id.D_add);

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zXingScannerView =new ZXingScannerView(getApplicationContext());
                setContentView(zXingScannerView);
                zXingScannerView.setResultHandler((ZXingScannerView.ResultHandler) getApplicationContext());
                zXingScannerView.startCamera();
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regnum = RegisterNum.getText().toString().trim();
                String num1 = Num1.getText().toString().trim();
                String num2 = Num2.getText().toString().trim();
                String num3 = Num3.getText().toString().trim();
                String num4 = Num4.getText().toString().trim();
                if (!regnum.isEmpty() && !num1.isEmpty() && !num2.isEmpty() && !num3.isEmpty() && !num4.isEmpty()) {
                    StudentDetails studentDetail = new StudentDetails(num1, num2, num3, num4, 50);
                    DatabaseReference studentdet = studentDatabase.child(regnum);
                    studentdet.setValue(studentDetail);
                    Toast.makeText(getBaseContext(), "New Student Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Kindly enter all details", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        RegisterNum.setText(result.getText());
        zXingScannerView.resumeCameraPreview(this);
    }
}
