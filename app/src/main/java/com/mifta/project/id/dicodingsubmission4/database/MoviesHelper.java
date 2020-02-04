package com.mifta.project.id.dicodingsubmission4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mifta.project.id.dicodingsubmission4.model.MoviesItems;

import java.util.ArrayList;

import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.MoviesColumns.COUNTRY;
import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.MoviesColumns.DATE;
import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.MoviesColumns.OVERVIEW;
import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.MoviesColumns.PHOTO;
import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.MoviesColumns.RATING;
import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.MoviesColumns.TITLE;
import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.MoviesColumns._ID;
import static com.mifta.project.id.dicodingsubmission4.database.DatabaseContract.TABLE_MOVIES;

public class MoviesHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIES;
    private static DatabaseHelper dataBaseHelper;
    private static MoviesHelper INSTANCE;
    private static SQLiteDatabase database;

    private MoviesHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MoviesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<MoviesItems> getAllMovies() {
        ArrayList<MoviesItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MoviesItems movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new MoviesItems();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setRating(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                movie.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(COUNTRY)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MoviesItems movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(DATE, movie.getDate());
        args.put(PHOTO, movie.getPhoto());
        args.put(RATING, movie.getRating());
        args.put(COUNTRY, movie.getCountry());
        return database.insert(DATABASE_TABLE, null, args);
    }


    public int delete(int id) {
        return database.delete(DATABASE_TABLE, _ID + " = '" + id + "'", null);
    }

    public boolean isExist(int id) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + _ID + " =?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        boolean exist = false;
        if (cursor.moveToFirst()) {
            exist = true;
        }
        cursor.close();
        return exist;
    }
}

