package com.example.v3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class maptest extends Fragment implements OnMapReadyCallback{

    private MapView mapView = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //레이아웃과 연결
        View view = (View) inflater.inflate(R.layout.testmap, container, false);

        mapView = (MapView)view.findViewById(R.id.map);
        mapView.getMapAsync(this);


        return view;

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(SEOUL);

        markerOptions.title("서울");

        markerOptions.snippet("수도");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));







    }
}
