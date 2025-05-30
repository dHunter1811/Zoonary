package com.example.latihan_praktikum_7.presentation.ui.favorit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihan_praktikum_7.R;
import com.example.latihan_praktikum_7.data.database.DatabaseHelper;
import com.example.latihan_praktikum_7.data.entity.Animal;
import com.example.latihan_praktikum_7.presentation.adapter.AnimalAdapter;

import java.util.List;

public class FavoritFragment extends Fragment {

    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<Animal> favorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_favorit);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Ambil data favorit
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        favorites = dbHelper.getFavoriteAnimals();

        // Adapter untuk RecyclerView
        adapter = new AnimalAdapter(favorites, animal -> {
            // Aksi klik, jika diperlukan
        }, requireContext());

        recyclerView.setAdapter(adapter);

        // Update data ketika terjadi perubahan pada status favorit
        // Ini bisa dipanggil setelah status favorit diperbarui
        updateFavoriteList();
    }

    // Fungsi untuk memperbarui data favorit
    private void updateFavoriteList() {
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        favorites.clear();
        favorites.addAll(dbHelper.getFavoriteAnimals());
        adapter.notifyDataSetChanged();
    }



}
