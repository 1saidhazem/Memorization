package com.example.memorization.Maps;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.onClick;
import com.example.memorization.onClickShowMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.memorization.databinding.ActivityShowMapsBinding;

import java.util.Map;

public class ShowMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityShowMapsBinding binding;
    private double Longitude, Latitude;
    private int idCenter;
    private MyViewModel mvm;
    private Center center;
    private LatLng sydney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idCenter = getIntent().getIntExtra("idCenter", -1);
        Log.d("ttt", "id Center : " + idCenter);

        mvm = new ViewModelProvider(this).get(MyViewModel.class);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        ActivityResultLauncher<String[]> arl = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result.get(Manifest.permission.ACCESS_FINE_LOCATION) &&
                        result.get(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(ShowMapsActivity.this);
                    mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(ShowMapsActivity.this);

                }
            }
        });

        arl.launch(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        mvm.getLatLong(idCenter+1, new onClickShowMap() {
            @Override
            public void onReturnedCenter(Center center) {
                sydney = new LatLng(center.getLatitude(), center.getLongitude());
            }
        });

        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}