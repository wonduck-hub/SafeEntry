package com.example.safeentry;

import android.content.Intent;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.net.ssl.HostnameVerifier;

public class DataActivity extends AppCompatActivity {

    String cityName;
    String districtName;
    String apiKey;

    String xmlDataString;

    List<Hospital> hospitals = new ArrayList<Hospital>(100);

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
    }
}