package com.example.covid.ui.fragments.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.ui.fragments.models.Maps;
import com.example.covid.ui.fragments.services.ApiAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment {
    private final int REQUEST_LOCATION_PERMISSION = 1;
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
    GoogleMap mMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            ApiAdapter.getInstance().getMaps().enqueue(new Callback<List<Maps>>() {
                @Override
                public void onResponse(Call<List<Maps>> call, Response<List<Maps>> response) {
                    LatLng position = null;
                    for (Maps local : response.body()) {
                        position = new LatLng(Double.parseDouble(local.getLatitude()), Double.parseDouble(local.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(position).title(local.getName()));

                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 8));

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            LatLng latLng = marker.getPosition();
                            if (EasyPermissions.hasPermissions(getContext(), perms)) {
                                String uri = "http://maps.google.com/maps?q=loc:" + latLng.latitude + "," + latLng.longitude + " (" + marker.getTitle() + ")";
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                            }
                            return true;
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Maps>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        requestLocationPermission();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            Toast.makeText(getContext(), "Permiso ya otorgado", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Por favor conceda el permiso de ubicación", REQUEST_LOCATION_PERMISSION, perms);
        }
    }
}