package com.example.gphone.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gphone.AdminActivity;
import com.example.gphone.R;

public class AdminLoginDialog extends DialogFragment {
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if(d!=null){
            d.getWindow().setLayout(900,600);
        }
        assert d != null;
        d.setCanceledOnTouchOutside(true);
    }

    EditText userName, passWord;
    Button login;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_login_dialog,container,false);
        userName = view.findViewById(R.id.admin_name);
        passWord = view.findViewById(R.id.admin_pass);
        login = view.findViewById(R.id.admin_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().trim().equals("admin") && passWord.getText().toString().trim().equals("adminpassword")){
                    Intent i = new Intent(getContext(), AdminActivity.class);
                    startActivity(i);
                }else {
                    passWord.setError("username or password is incorrect");
                }
            }
        });
        return view;
    }
}
