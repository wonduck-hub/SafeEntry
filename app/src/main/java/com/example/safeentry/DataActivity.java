package com.example.safeentry;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.Manifest;

public class DataActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;

    private String cityName;
    private String districtName;
    private String apiKey;

    private String xmlDataString;

    private List<Hospital> hospitals = new ArrayList<Hospital>(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Intent에서 데이터 받기
        Intent intent = getIntent();
        cityName = intent.getStringExtra("city");
        districtName = intent.getStringExtra("district");
        apiKey = intent.getStringExtra("apiKey");
        xmlDataString = intent.getStringExtra("xmlDataString");

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlDataString));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && parser.getName().equals("item")) {
                    Hospital temp = new Hospital();
                    while (!(eventType == XmlPullParser.END_TAG && parser.getName().equals("item"))) {
                        if (eventType == XmlPullParser.START_TAG && parser.getName().equals("dutyName")) {
                            temp.setName(parser.nextText());
                        }
                        else if(eventType == XmlPullParser.START_TAG && parser.getName().equals("dutyTel3")) {
                            temp.setTelNum(parser.nextText());
                        }
                        else if(eventType == XmlPullParser.START_TAG && parser.getName().equals("hvec")) {
                            temp.setEmergencyRoomCount(Integer.parseInt(parser.nextText()));
                        }
                        eventType = parser.next();
                    }
                    hospitals.add(temp);
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ListView listView = findViewById(R.id.list_view);

        HospitalAdapter adapter = new HospitalAdapter(this, hospitals);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Hospital selectedHospital = (Hospital) parent.getItemAtPosition(position);
                String phoneNumber = selectedHospital.getTelNum();

                if (ContextCompat.checkSelfPermission(DataActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DataActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                } else {
                    makePhoneCall(phoneNumber);
                }
            }

        });
    }

    private void makePhoneCall(String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        try {
            startActivity(callIntent);
        } catch (SecurityException e) {
            Toast.makeText(this, "전화 걸기 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // super 호출 추가

        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "전화 걸기 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}