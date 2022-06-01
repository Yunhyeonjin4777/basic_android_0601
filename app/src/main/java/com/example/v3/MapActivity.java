package com.example.v3;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    //구글맵은 다양하게 사용될 수 있으므로 변수로 선언한다
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //1) xml에 포함된 fragment에서 지도객체를 얻어오기 위해서 getMapAsync를 호출했고
        //2) callback이 호출되면
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        //3) 파라미터를 가지고
        //37.5581745287685, 126.92440907100338 홍대입구역
        //특정위치를 보여주기위해선 특정객체를 생성해주어야 한다 => 위도, 경도를 기반으로 하는 위치정보 클래스
        LatLng latLng = new LatLng(37.5581745287685, 126.92440907100338);
        //4) 카메라가 바라보는 위치, 즉 latLng == 홍대입구역으로 구글맵을 설정한다
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //5) 지도 확대
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        //6) 마커 생성
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("홍대입구역");
        googleMap.addMarker(markerOptions);
        //MY location
        //사용자가 gps를 수락하면 실행해라
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            googleMap.setMyLocationEnabled(true);
        } else
        {
            checkLocationPermissionWithRationale();
        }

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
