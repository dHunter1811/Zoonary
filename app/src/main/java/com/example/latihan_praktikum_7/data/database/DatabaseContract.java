package com.example.latihan_praktikum_7.data.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract() {}

    public static class AnimalEntry implements BaseColumns {
        public static final String TABLE_NAME = "animals";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SPECIES = "species";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_FAVORITE = "isFavorite";
    }
}
