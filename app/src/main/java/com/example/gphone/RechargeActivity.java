package com.example.gphone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gphone.DataClass.RechargeHistory;
import com.example.gphone.DataClass.StudentDetails;
import com.example.gphone.DataClass.WardenDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RechargeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    String regnum, schoolname, phonenumber;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    Integer amount;
    StudentDetails studentDetails;
    Dialog dialog;
    DatabaseReference studentdet;
    Context mContext;
    long credited, recharged;
    Button Scan;
    private ZXingScannerView zXingScannerView;
    EditText RegisterNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        Bundle bundle = getIntent().getExtras();
        schoolname = bundle.getString("schoolname");
        phonenumber = bundle.getString("phonenumber");

        Scan = findViewById(R.id.RD_check);
        RegisterNum = findViewById(R.id.RD_regnum);
        Button Check = findViewById(R.id.RD_check);

        final TextView RegisterNumText = findViewById(R.id.RD_rn_tb);
        final TextView Balance = findViewById(R.id.RD_balance);

        final Spinner Amount = findViewById(R.id.RD_spinner);
        Button Recharge = findViewById(R.id.RD_recharge);

        final LinearLayout DetailsLayout = findViewById(R.id.RD_details_layout);
        DetailsLayout.setVisibility(View.GONE);

        final DatabaseReference wardendet = databaseReference.child("databases").child("wardendatabase").child(phonenumber);

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zXingScannerView =new ZXingScannerView(getApplicationContext());
                setContentView(zXingScannerView);
                zXingScannerView.setResultHandler((ZXingScannerView.ResultHandler) getApplicationContext());
                zXingScannerView.startCamera();
                Scan.setVisibility(View.GONE);
            }
        });

        Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regnum = RegisterNum.getText().toString().trim();
                studentdet = databaseReference.child("databases").child("schooldatabase").child(schoolname).child(regnum);
                if (!regnum.isEmpty()) {
                    studentdet.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            studentDetails = dataSnapshot.getValue(StudentDetails.class);
                            if (dataSnapshot.getValue() != null) {
                                DetailsLayout.setVisibility(View.VISIBLE);
                                RegisterNumText.setText(regnum);
                                Balance.setText("" + studentDetails.getBalance());
                            } else {
                                RegisterNum.setError("Enter valid Reg no.");
                                RegisterNum.setFocusable(true);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getBaseContext(), "Problem in loading Student Details", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        List<Integer> list = new ArrayList<>();
        list.add(50);
        list.add(100);

        final ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getBaseContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Amount.setAdapter(adapter);

        Amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                amount = (Integer) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        Recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference balanceRef = studentdet.child("balance");
                Integer currentBalance = studentDetails.getBalance();
                currentBalance += amount;
                balanceRef.setValue(currentBalance);
                UpdateRechargeHistory(amount);

                wardendet.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        WardenDetails wardenDetails = dataSnapshot.getValue(WardenDetails.class);
                        credited = wardenDetails.getAmountCredited();
                        recharged = wardenDetails.getAmountRecharged();
                        credited -= amount;
                        recharged += amount;
                        UpdateWaredenDatabase(credited, recharged);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            private void UpdateRechargeHistory(Integer amount) {
                DatabaseReference rechargeHistory = firebaseDatabase.getReference().child("databases").child("rechargehistory").child(phonenumber).child(String.valueOf(System.currentTimeMillis()));
                RechargeHistory history = new RechargeHistory("no", "yes", amount, regnum, System.currentTimeMillis());
                rechargeHistory.setValue(history);
            }

            private void UpdateWaredenDatabase(long credited, long recharged) {

                DatabaseReference amountCredited = wardendet.child("amountcredited");
                amountCredited.setValue(credited);
                DatabaseReference amountrecharged = wardendet.child("amountrecharged");
                amountrecharged.setValue(recharged);
                Toast.makeText(mContext, "Recharge for " + amount + " done successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
        if(!result.getText().equals("")){
            Scan.setVisibility(View.VISIBLE);
        }
        RegisterNum.setText(result.getText());
        zXingScannerView.resumeCameraPreview(this);
    }
}
