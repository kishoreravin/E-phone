package com.example.gphone;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gphone.Adapters.RechargeAdapter;
import com.example.gphone.DataClass.RechargeHistory;
import com.example.gphone.Dialogs.AdminLoginDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class WardenActivity extends AppCompatActivity {

    TextView WardenName, BalanceAmount, RechargedAmount;
    String wardenname, phonenumber, schoolname = "";
    long rechargeamount, balanceamount;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<RechargeHistory> rechargeHistories = new ArrayList<>();
    FloatingActionButton AddStudents, Recharge, Admin, ChangeNumber, Fab;
    private LinearLayout fabLayout1, fabLayout2, fabLayout3, fabLayout4;
    private View fabBGLayout;
    private boolean isFABOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden);
        Bundle bundle = getIntent().getExtras();
        wardenname = bundle.getString("name");
        balanceamount = bundle.getLong("balanceamount");
        rechargeamount = bundle.getLong("rechargedamount");
        phonenumber = bundle.getString("phonenumber");
        schoolname = bundle.getString("schoolname");
        WardenName = findViewById(R.id.warden_name);
        BalanceAmount = findViewById(R.id.balance_available);
        RechargedAmount = findViewById(R.id.recharge_done);

        fabLayout1 = findViewById(R.id.fabLayout1);
        fabLayout2 = findViewById(R.id.fabLayout2);
        fabLayout3 = findViewById(R.id.fabLayout3);
        fabLayout4 = findViewById(R.id.fabLayout4);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        AddStudents = findViewById(R.id.add_student);
        Recharge = findViewById(R.id.recharge);
        Admin = findViewById(R.id.admin);
        ChangeNumber = findViewById(R.id.change_num);
        Fab = findViewById(R.id.fab);

        WardenName.setText(wardenname);
        BalanceAmount.setText("" + balanceamount);
        RechargedAmount.setText("" + rechargeamount);

        AddStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), AddStudent.class);
                i.putExtra("schoolname", schoolname);
                startActivity(i);
            }
        });

        Recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RechargeActivity.class);
                i.putExtra("schoolname", schoolname);
                i.putExtra("phonenumber", phonenumber);
                startActivity(i);
            }
        });

        ChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ChangeNumber.class);
                i.putExtra("schoolname", schoolname);
                startActivity(i);
            }
        });

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminLoginDialog dialog = new AdminLoginDialog();
                dialog.show(getSupportFragmentManager(),"Admin Login");
            }
        });
        DatabaseReference history = databaseReference.child("databases").child("rechargehistory").child(phonenumber);
        history.orderByChild("millisec").limitToFirst(20).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    RechargeHistory rechargeHistory = post.getValue(RechargeHistory.class);
                    rechargeHistories.add(rechargeHistory);
                    Collections.reverse(rechargeHistories);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Unable to get Histories", Toast.LENGTH_SHORT).show();
            }
        });

        RechargeAdapter rechargeAdapter = new RechargeAdapter(getBaseContext(), rechargeHistories);
        ListView listView = findViewById(R.id.history_list);
        listView.setAdapter(rechargeAdapter);
        rechargeAdapter.notifyDataSetChanged();
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabBGLayout.setVisibility(View.VISIBLE);
        Fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        fabLayout3.setVisibility(View.VISIBLE);
        fabLayout4.animate().translationY(-getResources().getDimension(R.dimen.standard_190));
        fabLayout4.setVisibility(View.VISIBLE);

    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        Fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout4.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                    fabLayout4.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
