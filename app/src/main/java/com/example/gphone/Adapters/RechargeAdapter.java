package com.example.gphone.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gphone.R;
import com.example.gphone.DataClass.RechargeHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RechargeAdapter extends ArrayAdapter<RechargeHistory> {

    ArrayList<RechargeHistory> rechargeHistories = new ArrayList<>();

    public RechargeAdapter(Context context, ArrayList<RechargeHistory> rechargeHistories) {
        super(context, 0, rechargeHistories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        String drg = null;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.recharge_list, parent, false);
        }
        RechargeHistory cHistory = getItem(position);
        TextView Date = listItemView.findViewById(R.id.date);
        assert cHistory != null;
        long millisec = cHistory.getMillisec();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:MM:SS");
        Date.setText(dateFormat.format(new Date(millisec)));

        TextView creditRecharge = listItemView.findViewById(R.id.cre_rec);
        if (cHistory.getCredited().equals("true")) {
            drg = "Credited";
            creditRecharge.setText(drg);
            creditRecharge.setTextColor(Color.GREEN);
        } else {
            drg = "Recharged";
            creditRecharge.setText(drg);
            creditRecharge.setTextColor(Color.RED);
        }

        TextView Amount = listItemView.findViewById(R.id.amount);
        Amount.setText("" + cHistory.getAmount());

        TextView RegNum = listItemView.findViewById(R.id.reg);
        if (cHistory.getCredited().equals("true")) {
            RegNum.setText("Company");
        } else {
            RegNum.setText(cHistory.getRegisternum());
        }
        return listItemView;
    }
}