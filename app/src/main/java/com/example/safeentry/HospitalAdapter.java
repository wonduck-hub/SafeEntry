package com.example.safeentry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HospitalAdapter extends ArrayAdapter<Hospital> {
    public HospitalAdapter(Context context, List<Hospital> hospitals) {
        super(context, 0, hospitals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hospital hospital = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_hospital, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewTelNum = convertView.findViewById(R.id.textViewTelNum);
        TextView textViewEmergencyRoomCount = convertView.findViewById(R.id.textViewEmergencyRoomCount);

        textViewName.setText(hospital.getName());
        textViewTelNum.setText("전화 번호: " + hospital.getTelNum());
        if (hospital.getEmergencyRoomCount() > 0) {
            textViewEmergencyRoomCount.setText("응급실 자리: " + String.valueOf(hospital.getEmergencyRoomCount()));
        }
        else {
            textViewEmergencyRoomCount.setText("응급실 자리: 없음");
        }

        return convertView;
    }
}
