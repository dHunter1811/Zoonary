package com.example.latihan_praktikum_7.presentation.ui.konten;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.latihan_praktikum_7.R;
import com.example.latihan_praktikum_7.presentation.adapter.AnimalAdapter;
import com.example.latihan_praktikum_7.data.entity.Animal;
import com.example.latihan_praktikum_7.presentation.viewmodel.AnimalViewModel;

import java.util.ArrayList;
import java.util.List;

public class KontenFragment extends Fragment {
    private AnimalViewModel animalViewModel;
    private EditText etName, etSpecies, etDescription;
    private Button btnSave, btnUpdate, btnDelete;
    private RecyclerView recyclerView;
    private AnimalAdapter adapter;
    private List<Animal> animalList = new ArrayList<>();
    private int selectedAnimalId = -1;

    private EditText etSearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_konten, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animalViewModel = new ViewModelProvider(requireActivity()).get(AnimalViewModel.class);

        etName = view.findViewById(R.id.et_name);
        etSpecies = view.findViewById(R.id.et_species);
        etDescription = view.findViewById(R.id.et_description);
        etSearch = view.findViewById(R.id.et_search);

        etSearch.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAnimals(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
        });

        btnSave = view.findViewById(R.id.btn_save);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnDelete = view.findViewById(R.id.btn_delete);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AnimalAdapter(animalList, animal -> {
            etName.setText(animal.getName());
            etSpecies.setText(animal.getSpecies());
            etDescription.setText(animal.getDescription());
            selectedAnimalId = animal.getId();
        }, requireContext());

        recyclerView.setAdapter(adapter);

        animalViewModel.getAnimalList().observe(getViewLifecycleOwner(), list -> {
            animalList.clear();
            animalList.addAll(list);
            adapter.notifyDataSetChanged();
        });

        animalViewModel.fetchAnimalsFromApi();

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String species = etSpecies.getText().toString();
            String description = etDescription.getText().toString();

            if (name.isEmpty() || species.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            animalViewModel.saveData(name, species, description);
            clearInputFields();
        });

        btnUpdate.setOnClickListener(v -> {
            if (selectedAnimalId == -1) {
                Toast.makeText(getContext(), "Pilih data terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = etName.getText().toString();
            String species = etSpecies.getText().toString();
            String description = etDescription.getText().toString();

            if (name.isEmpty() || species.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            animalViewModel.updateData(selectedAnimalId, name, species, description);
            clearInputFields();
        });

        btnDelete.setOnClickListener(v -> {
            if (selectedAnimalId == -1) {
                Toast.makeText(getContext(), "Pilih data terlebih dahulu!", Toast.LENGTH_SHORT).show();
                return;
            }

            animalViewModel.deleteData(selectedAnimalId);
            clearInputFields();
        });
    }

    private void searchAnimals(String keyword) {
        new Thread(() -> {
            List<Animal> results = animalViewModel.searchAnimals(keyword);
            requireActivity().runOnUiThread(() -> {
                animalList.clear();
                animalList.addAll(results);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }


    private void clearInputFields() {
        etName.setText("");
        etSpecies.setText("");
        etDescription.setText("");
        selectedAnimalId = -1;
    }
}
