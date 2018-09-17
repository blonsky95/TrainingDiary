package pabs.trainingapp1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import pabs.trainingapp1.data.TrainingPlan.TrainingEntry;

/**
 * Created by Pablo-W8 on 07/03/2018.
 */

public class ItemProvider extends ContentProvider {

    public static final String LOG_TAG = ItemProvider.class.getSimpleName();

    private static final int ITEM_LIST = 1;

    private static final int ITEM_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(TrainingPlan.CONTENT_AUTHORITY, TrainingPlan.PATH_TRAININGS, ITEM_LIST);

        sUriMatcher.addURI(TrainingPlan.CONTENT_AUTHORITY, TrainingPlan.PATH_TRAININGS + "/#", ITEM_ID);
    }

    private PlanDbHelper mDbHelper;


    @Override
    public boolean onCreate() {
        mDbHelper = new PlanDbHelper((getContext()));
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case ITEM_LIST:
                cursor = database.query(TrainingEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                Log.e(LOG_TAG, "LIST SEARCH");
                break;

            case ITEM_ID:
                selection = TrainingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(TrainingEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                Log.e(LOG_TAG, "UNIQUE ID SEARCH");
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI");

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM_LIST:
                return TrainingEntry.CONTENT_LIST_TYPE;
            case ITEM_ID:
                return TrainingEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM_LIST:
                return insertItem(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertItem(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(TrainingEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);


        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM_LIST:
                rowsDeleted = database.delete(TrainingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = TrainingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TrainingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEM_LIST:
                return updateItem(uri, contentValues, selection, selectionArgs);
            case ITEM_ID:

                selection = TrainingEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }

    }

    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(TrainingEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;

    }
}
