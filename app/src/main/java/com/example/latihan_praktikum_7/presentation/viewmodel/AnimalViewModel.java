package com.example.latihan_praktikum_7.presentation.viewmodel;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.latihan_praktikum_7.data.network.RetrofitClient;
import com.example.latihan_praktikum_7.data.database.DatabaseContract;
import com.example.latihan_praktikum_7.data.database.DatabaseHelper;
import com.example.latihan_praktikum_7.data.entity.Animal;
import com.example.latihan_praktikum_7.data.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalViewModel extends AndroidViewModel {
    private final DatabaseHelper dbHelper;
    private final MutableLiveData<List<Animal>> animalList = new MutableLiveData<>();
    private final MutableLiveData<List<Animal>> favoriteAnimalList = new MutableLiveData<>();

    public AnimalViewModel(@NonNull Application application) {
        super(application);
        dbHelper = new DatabaseHelper(application);
        fetchAnimalsFromApi();
    }

    public MutableLiveData<List<Animal>> getAnimalList() {
        return animalList;
    }

    public MutableLiveData<List<Animal>> getFavoriteAnimalList() {
        return favoriteAnimalList;
    }

    public void fetchAnimalsFromApi() {
        ApiService apiService = RetrofitClient.getClient();
        Call<List<Animal>> call = apiService.getAnimals();

        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveAnimalsToDatabase(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch data: " + t.getMessage());
            }
        });
    }

    public void saveAnimalsToDatabase(List<Animal> animals) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + DatabaseContract.AnimalEntry.TABLE_NAME);

        for (Animal animal : animals) {
            db.execSQL("INSERT INTO " + DatabaseContract.AnimalEntry.TABLE_NAME +
                            " (" + DatabaseContract.AnimalEntry.COLUMN_ID + ", " +
                            DatabaseContract.AnimalEntry.COLUMN_NAME + ", " +
                            DatabaseContract.AnimalEntry.COLUMN_SPECIES + ", " +
                            DatabaseContract.AnimalEntry.COLUMN_DESCRIPTION + ", " +
                            DatabaseContract.AnimalEntry.COLUMN_IS_FAVORITE + ") VALUES (?, ?, ?, ?, ?) ",
                    new Object[]{animal.getId(), animal.getName(), animal.getSpecies(), animal.getDescription(), animal.getIsFavorite()});
        }

        loadAnimalsFromDatabase();
    }

    public void loadAnimalsFromDatabase() {
        List<Animal> animals = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.AnimalEntry.TABLE_NAME +
                " ORDER BY id DESC", null);

        while (cursor.moveToNext()) {
            animals.add(new Animal(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) // Mengambil nilai isFavorite dari database
            ));
        }
        cursor.close();
        animalList.postValue(animals);
    }

    public void loadFavoriteAnimalsFromDatabase() {
        List<Animal> favoriteAnimals = dbHelper.getFavoriteAnimals();
        favoriteAnimalList.postValue(favoriteAnimals);
    }

    public void setFavorite(int animalId, boolean isFavorite) {
        dbHelper.setFavorite(animalId, isFavorite);
        loadFavoriteAnimalsFromDatabase();  // Memuat hewan favorit setelah pembaruan
    }

    public void saveData(String name, String species, String habitat) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AnimalEntry.COLUMN_NAME, name);
        values.put(DatabaseContract.AnimalEntry.COLUMN_SPECIES, species);
        values.put(DatabaseContract.AnimalEntry.COLUMN_DESCRIPTION, habitat);

        db.insert(DatabaseContract.AnimalEntry.TABLE_NAME, null, values);
        loadAnimalsFromDatabase();
    }

    public void updateData(int id, String name, String species, String habitat) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AnimalEntry.COLUMN_NAME, name);
        values.put(DatabaseContract.AnimalEntry.COLUMN_SPECIES, species);
        values.put(DatabaseContract.AnimalEntry.COLUMN_DESCRIPTION, habitat);

        db.update(DatabaseContract.AnimalEntry.TABLE_NAME, values,
                DatabaseContract.AnimalEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        loadAnimalsFromDatabase();
    }

    public void deleteData(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.AnimalEntry.TABLE_NAME,
                DatabaseContract.AnimalEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        loadAnimalsFromDatabase();
    }

    public List<Animal> searchAnimals(String keyword) {
        return dbHelper.searchAnimals(keyword);
    }
}
