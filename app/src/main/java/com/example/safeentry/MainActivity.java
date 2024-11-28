package com.example.safeentry;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public enum Region {
        // 서울특별시
        SEOUL("서울특별시", new String[]{"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"}),

        // 부산광역시
        BUSAN("부산광역시", new String[]{"해운대구", "수영구", "남구", "연제구", "동래구", "금정구", "사상구", "사하구", "강서구", "북구", "중구"}),

        // 대구광역시
        DAEGU("대구광역시", new String[]{"중구", "동구", "서구", "남구", "북구", "달서구", "수성구", "달성군"}),

        // 인천광역시
        INCHEON("인천광역시", new String[]{"중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진군"}),

        // 광주광역시
        GWANGJU("광주광역시", new String[]{"동구", "서구", "남구", "북구", "광산구"}),

        // 대전광역시
        DAEJEON("대전광역시", new String[]{"동구", "중구", "서구", "유성구", "대덕구"}),

        // 울산광역시
        ULSAN("울산광역시", new String[]{"중구", "남구", "동구", "북구", "울주군"}),

        // 세종특별자치시
        SEJONG("세종특별자치시", new String[]{"세종시"}),

        // 경기도
        GYEONGGI("경기도", new String[]{"수원시", "성남시", "안양시", "부천시", "광명시", "평택시", "안산시", "고양시", "과천시", "의왕시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의정부시", "용인시", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "여주시", "포천시", "양주시"}),

        // 강원도
        GANGWON("강원도", new String[]{"춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시"}),

        // 충청북도
        CHUNGBUK("충청북도", new String[]{"청주시", "충주시", "제천시"}),

        // 충청남도
        CHUNGNAM("충청남도", new String[]{"천안시", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시"}),

        // 전라북도
        JEONBUK("전라북도", new String[]{"전주시", "군산시", "익산시", "정읍시", "남원시", "김제시"}),

        // 전라남도
        JEONNAM("전라남도", new String[]{"목포시", "여수시", "순천시", "나주시", "광양시"}),

        // 경상북도
        GYEONGBUK("경상북도", new String[]{"포항시", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시"}),

        // 경상남도
        GYEONGNAM("경상남도", new String[]{"창원시", "진주시", "통영시", "사천시", "김해시", "밀양시", "거제시", "양산시"}),

        // 제주특별자치도
        JEJU("제주특별자치도", new String[]{"제주시", "서귀포시"});

        private final String name;
        private final String[] districts;

        Region(String name, String[] districts) {
            this.name = name;
            this.districts = districts;
        }

        public String getName() {
            return name;
        }

        public String[] getDistricts() {
            return districts;
        }
    }

    Spinner citySpinner;
    Spinner districtSpinner;

    String[] cities = { "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시",
            "울산광역시", "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도",
            "전라남도", "경상북도", "경상남도", "제주특별자치도" };

    String[] districts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        citySpinner = findViewById(R.id.city_spinner);
        districtSpinner = findViewById(R.id.district_spinner);

        ArrayAdapter<String> citySpinnerItems = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        citySpinner.setAdapter(citySpinnerItems);

        districts = Region.SEOUL.getDistricts();
        ArrayAdapter<String> districtSpinnerItems =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districts);
        districtSpinner.setAdapter(districtSpinnerItems);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleCitySelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때
            }
        });
    }

    private void handleCitySelection(int position) {
        String selectedCity = cities[position];
        System.out.println("선택된 시도: " + selectedCity);
        updateDistricts(selectedCity);
    }


    private void updateDistricts(String city) {

        for (Region region : Region.values()) {
            if (region.getName().equals(city)) {
                districts = region.getDistricts();
            }
        }
        ArrayAdapter<String> districtSpinnerItems =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districts);
        districtSpinner.setAdapter(districtSpinnerItems);

        Toast.makeText(getApplicationContext(), "시군구를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show();

    }

}