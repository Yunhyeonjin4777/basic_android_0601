package com.example.v3;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class Map2Activity extends AppCompatActivity implements OnMapReadyCallback {

    //구글맵은 다양하게 사용될 수 있으므로 변수로 선언한다
    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private EditText editText;

    EditText et_address;
    Button but_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        //1) xml에 포함된 fragment에서 지도객체를 얻어오기 위해서 getMapAsync를 호출했고
        //2) callback이 호출되면
        //SupportMapFragment : googlemap 객체의 수명 주기를 관리하기 위한 프래그먼트
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //프래그먼트에서 콜백을 설정
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //3) 파라미터를 가지고
        mMap = googleMap;
        //geocoding : 주소를 위도 및 경도 등으로 변환하는 프로세스
        geocoder = new Geocoder(this);

        //맵 터치 이벤트 구현
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                MarkerOptions mOptions = new MarkerOptions();
                //마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = latLng.latitude; //위도
                Double longitude = latLng.longitude; //경도

                //마커 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());

                //latlng : 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));

                //마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });

        //버튼 이벤트
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();
                List<Address> addressList = null;
                try {
                    {
                        //editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                        //str = 주소, 10 = 최대 검색 결과 개수
                        addressList = geocoder.getFromLocationName(str, 10);

                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
                Log.d("MAP", "addressList : " + addressList.get(0).toString());




                //콤마를 기준으로 split
                String []splitStr = addressList.get(0).toString().split(",");
                Log.d("MAP", "splitStr : " + splitStr[0]);

                //주소
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2);
                Log.d("MAP", "address : " + address);


//                //위도
//                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);
//                //경도
//                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1);
//                Log.d("TAG", "String latitude : " + latitude);
//                Log.d("TAG", "String longitude : " + longitude);

//                //위도
//                String latitude = "37.566535";
//                //경도
//                String longitude = "126.977969";



                double latitude = addressList.get(0).getLatitude();
                double longitude = addressList.get(0).getLongitude();
                Log.d("MAP", "mLat + mlng : " + latitude + " / " + longitude);


                //좌표(위도, 경도) 생성
                //String형 숫자를 double형으로 변환

                LatLng latLng = new LatLng(latitude, longitude);
                Log.d("MAP", "latLng : " + latLng);

                //마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                //마커 스니펫(간단한 텍스트) 설정
                mOptions2.snippet(address);
                //latlng : 위도 경도 쌍을 나타냄
                mOptions2.position(latLng);
                //마커(핀) 추가
                googleMap.addMarker(mOptions2);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

                et_address = findViewById(R.id.et_address);
                et_address.setText(address);
                Log.d("MAP", "address : " + address);



//                DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
//
//                String lati = decimalFormat.format(latitude);
//                String longi = decimalFormat.format(longitude);
                String lati = Double.toString(latitude);
                String longi = Double.toString(longitude);
                Log.d("MAP", "형번환 : " + lati + " / " + longi);


                but_address = findViewById(R.id.but_address);
                but_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //fragment_mylist로 인텐트 전달
                        Intent intent = getIntent();
                        intent.putExtra("address", address);
                        intent.putExtra("latitude", lati);
                        intent.putExtra("longitude", longi);
                        Log.d("MAP", "intent.putExtra : " + address + lati + longi);
                        setResult(1004, intent);
                        finish();
                    }
                });

            }
        });

        //특정위치를 보여주기위해선 특정객체를 생성해주어야 한다 => 위도, 경도를 기반으로 하는 위치정보 클래스
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in sydney"));
        //4) 카메라가 바라보는 위치, 즉 latLng == 홍대입구역으로 구글맵을 설정한다
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

}
