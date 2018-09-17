package pabs.trainingapp1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pabs.trainingapp1.data.TrainingPlan.TrainingEntry;

/**
 * Created by Pablo-W8 on 23/02/2018.
 */

public class PlanDbHelper extends SQLiteOpenHelper {

    public  static final String LOG_TAG=PlanDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME="training_plans.db";

    private  static final int DATABASE_VERSION=1;

    public PlanDbHelper (Context context) {super(context, DATABASE_NAME,null,DATABASE_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TRAININGS_TABLE="CREATE TABLE " + TrainingEntry.TABLE_NAME+ " ("
                + TrainingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TrainingEntry.COLUMN_TRAINING_MONDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_RESULTS_MONDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_TRAINING_TUESDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_RESULTS_TUESDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_TRAINING_WEDNESDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_RESULTS_WEDNESDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_TRAINING_THURSDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_RESULTS_THURSDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_TRAINING_FRIDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_RESULTS_FRIDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_TRAINING_SATURDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_RESULTS_SATURDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_TRAINING_SUNDAY + " TEXT NOT NULL, "
                + TrainingEntry.COLUMN_RESULTS_SUNDAY + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_TRAININGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
