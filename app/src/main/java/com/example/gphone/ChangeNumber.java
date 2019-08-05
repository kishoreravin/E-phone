package com.example.gphone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gphone.DataClass.StudentDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ChangeNumber extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    String regnum, schoolname = "";
    StudentDetails studentDetails;
    DatabaseReference studentdet;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference studentDatabase;
    EditText RegisterNum;
    Button scan;
    private ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);
        RegisterNum = findViewById(R.id.CND_regnum);
        final Button Enter = findViewById(R.id.CND_enter);
        final LinearLayout editLayout = findViewById(R.id.CND_edit_layout);
        scan = findViewById(R.id.scan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zXingScannerView =new ZXingScannerView(getApplicationContext());
                setContentView(zXingScannerView);
                zXingScannerView.setResultHandler((ZXingScannerView.ResultHandler) getApplicationContext());
                zXingScannerView.startCamera();
                scan.setVisibility(View.GONE);
            }
        });

        Bundle bundle = getIntent().getExtras();
        schoolname = bundle.getString("schoolname");
        studentDatabase = databaseReference.child("databases").child("schooldatabase").child(this.schoolname);
        editLayout.setVisibility(View.GONE);
        final TextView Num1Text = findViewById(R.id.CND_num1_text);
        final TextView Num2Text = findViewById(R.id.CND_num2_text);
        final TextView Num3Text = findViewById(R.id.CND_num3_text);
        final TextView Num4Text = findViewById(R.id.CND_num4_text);

        final EditText Num1Edit = findViewById(R.id.CND_num1_edit);
        final EditText Num2Edit = findViewById(R.id.CND_num2_edit);
        final EditText Num3Edit = findViewById(R.id.CND_num3_edit);
        final EditText Num4Edit = findViewById(R.id.CND_num4_edit);

        Num1Edit.setVisibility(View.GONE);
        Num2Edit.setVisibility(View.GONE);
        Num3Edit.setVisibility(View.GONE);
        Num4Edit.setVisibility(View.GONE);

        final Button Num1Button = findViewById(R.id.CND_num1_button);
        final Button Num2Button = findViewById(R.id.CND_num2_button);
        final Button Num3Button = findViewById(R.id.CND_num3_button);
        final Button Num4Button = findViewById(R.id.CND_num4_button);


        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Enter.setEnabled(false);
                regnum = RegisterNum.getText().toString().trim();
                studentdet = studentDatabase.child(regnum);

                if (!regnum.isEmpty()) {
                    studentdet.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            studentDetails = dataSnapshot.getValue(StudentDetails.class);
                            if (dataSnapshot.getValue() != null) {
                                Num1Text.setText(studentDetails.getNum1());
                                Num2Text.setText(studentDetails.getNum2());
                                Num3Text.setText(studentDetails.getNum3());
                                Num4Text.setText(studentDetails.getNum4());
                                editLayout.setVisibility(View.VISIBLE);
                            } else {
                                RegisterNum.setError("Enter valid Reg no.");
                                RegisterNum.setFocusable(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(), "Enter valid Register Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Num1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Num1Text.setVisibility(View.GONE);
                Num1Edit.setVisibility(View.VISIBLE);
                Num1Edit.setHint("New Number");
                Num1Button.setText("Change");
                Num1Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Num1Button.setEnabled(false);
                        String newnumber = Num1Edit.getText().toString().trim();
                        if (!newnumber.isEmpty()) {
                            DatabaseReference studentnum1 = studentDatabase.child(regnum).child("num1");
                            studentnum1.setValue(newnumber);
                            Num1Edit.setVisibility(View.GONE);
                            Num1Text.setText(newnumber);
                            Num1Text.setVisibility(View.VISIBLE);
                            Num1Button.setText("Done");
                        } else {
                            Toast.makeText(getBaseContext(), "Enter a valid number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Num2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Num2Button.setEnabled(false);
                Num2Text.setVisibility(View.GONE);
                Num2Edit.setVisibility(View.VISIBLE);
                Num2Edit.setHint("New Number");
                Num2Button.setText("Change");
                Num2Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newnumber = Num2Edit.getText().toString().trim();
                        if (!newnumber.isEmpty()) {
                            DatabaseReference studentnum1 = studentDatabase.child(regnum).child("num2");
                            studentnum1.setValue(newnumber);
                            Num2Edit.setVisibility(View.GONE);
                            Num2Text.setText(newnumber);
                            Num2Text.setVisibility(View.VISIBLE);
                            Num2Button.setText("Done");
                        } else {
                            Toast.makeText(getBaseContext(), "Enter a valid number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        Num4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Num4Button.setEnabled(false);
                Num4Text.setVisibility(View.GONE);
                Num4Edit.setVisibility(View.VISIBLE);
                Num4Edit.setHint("New Number");
                Num4Button.setText("Change");
                Num4Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newnumber = Num4Edit.getText().toString().trim();
                        if (!newnumber.isEmpty()) {
                            DatabaseReference studentnum1 = studentDatabase.child(regnum).child("num4");
                            studentnum1.setValue(newnumber);
                            Num4Edit.setVisibility(View.GONE);
                            Num4Text.setText(newnumber);
                            Num4Text.setVisibility(View.VISIBLE);
                            Num4Button.setText("Done");
                        } else {
                            Toast.makeText(getBaseContext(), "Enter a valid number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Num3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Num3Button.setEnabled(false);
                Num3Text.setVisibility(View.GONE);
                Num3Edit.setVisibility(View.VISIBLE);
                Num3Edit.setHint("New Number");
                Num3Button.setText("Change");
                Num3Button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newnumber = Num3Edit.getText().toString().trim();
                        if (!newnumber.isEmpty()) {
                            DatabaseReference studentnum1 = studentDatabase.child(regnum).child("num3");
                            studentnum1.setValue(newnumber);
                            Num3Edit.setVisibility(View.GONE);
                            Num3Text.setText(newnumber);
                            Num3Text.setVisibility(View.VISIBLE);
                            Num3Button.setText("Done");
                        } else {
                            Toast.makeText(getBaseContext(), "Enter a valid number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            scan.setVisibility(View.VISIBLE);
        }
        RegisterNum.setText(result.getText());
        zXingScannerView.resumeCameraPreview(this);
    }
}
