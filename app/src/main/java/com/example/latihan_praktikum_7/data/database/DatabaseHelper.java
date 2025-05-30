package com.example.latihan_praktikum_7.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.latihan_praktikum_7.data.entity.Animal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "animals.db";
    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.AnimalEntry.TABLE_NAME + " (" +
                    DatabaseContract.AnimalEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DatabaseContract.AnimalEntry.COLUMN_NAME + " TEXT, " +
                    DatabaseContract.AnimalEntry.COLUMN_SPECIES + " TEXT, " +
                    DatabaseContract.AnimalEntry.COLUMN_DESCRIPTION + " TEXT, " +
                    DatabaseContract.AnimalEntry.COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.AnimalEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public List<Animal> searchAnimals(String keyword) {
        List<Animal> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = DatabaseContract.AnimalEntry.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + keyword + "%"};

        Cursor cursor = db.query(
                DatabaseContract.AnimalEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_NAME));
                String species = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_SPECIES));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_DESCRIPTION));
                int isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_IS_FAVORITE));  // Ambil nilai isFavorite dari database
                result.add(new Animal(id, name, species, description, isFavorite));  // Sertakan isFavorite
            } while (cursor.moveToNext());
            cursor.close();
        }

        return result;
    }

    public List<Animal> getFavoriteAnimals() {
        List<Animal> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.AnimalEntry.TABLE_NAME,
                null,
                DatabaseContract.AnimalEntry.COLUMN_IS_FAVORITE + "=?",
                new String[]{"1"},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_NAME));
                String species = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_SPECIES));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_DESCRIPTION));
                int isFavorite = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AnimalEntry.COLUMN_IS_FAVORITE));  // Ambil nilai isFavorite
                result.add(new Animal(id, name, species, description, isFavorite));  // Sertakan isFavorite
            } while (cursor.moveToNext());
            cursor.close();
        }
        return result;
    }

    public void setFavorite(int id, boolean isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AnimalEntry.COLUMN_IS_FAVORITE, isFavorite ? 1 : 0);
        db.update(DatabaseContract.AnimalEntry.TABLE_NAME, values, DatabaseContract.AnimalEntry.COLUMN_ID + "=?", new String[]{String.valueOf(id)});


    }
}
