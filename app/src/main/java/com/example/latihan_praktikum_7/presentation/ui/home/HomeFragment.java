package com.example.latihan_praktikum_7.presentation.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.latihan_praktikum_7.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi FusedLocationProviderClient untuk mengambil lokasi
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Ambil lokasi terakhir dan tampilkan kota
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return view;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        // Gunakan Geocoder untuk mendapatkan kota berdasarkan latitude dan longitude
                        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (!addresses.isEmpty()) {
                                String city = addresses.get(0).getLocality();
                                TextView tvLokasi = view.findViewById(R.id.tv_lokasi);
                                tvLokasi.setText("Rekomendasi hewan dari daerah: " + city);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        return view;
    }
}
