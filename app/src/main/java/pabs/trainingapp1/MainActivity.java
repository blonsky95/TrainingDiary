package pabs.trainingapp1;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.BaseColumns;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import pabs.trainingapp1.data.PlanDbHelper;
import pabs.trainingapp1.data.TrainingPlan.TrainingEntry;

public class MainActivity extends AppCompatActivity {


    private final String LOG_TAG = "LOG_CAT";

    private Uri mCurrentWeekUri;

    private TextView mondayTV;
    private TextView tuesdayTV;
    private TextView wednesdayTV;
    private TextView thursdayTV;
    private TextView fridayTV;
    private TextView saturdayTV;
    private TextView sundayTV;

    private TextView mondayRE;
    private TextView tuesdayRE;
    private TextView wednesdayRE;
    private TextView thursdayRE;
    private TextView fridayRE;
    private TextView saturdayRE;
    private TextView sundayRE;

    private TextView weeknumberTV;
    private TextView currentWeekDisplayTV;

    private static int weekNumber = 1;

    PlanDbHelper dbhelper = new PlanDbHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_main));


        currentWeekDisplayTV = findViewById(R.id.current_week_display);

        weeknumberTV = findViewById(R.id.WN_display);
        Log.e(LOG_TAG, "WEEK NUMBER AT THE START OF ACTIVITY: " + weekNumber);

        if (weekNumber > 0) {
            weeknumberTV.setText(Integer.toString(weekNumber));
            currentWeekDisplayTV.setText(Integer.toString(weekNumber));

        } else {

            weeknumberTV.setText("1");
            currentWeekDisplayTV.setText("1");
        }


        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor cursor = db.query(TrainingEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            Log.e(LOG_TAG, "DATABASE ISN'T EMPTY");
        }
        else {
            Log.e(LOG_TAG, "DATABASE IS EMPTY");
            createTable();
        }
        cursor.close();

        TextView export_btn = findViewById(R.id.export_btn);
        export_btn.setOnClickListener(export_data);

        TextView clonedata = findViewById(R.id.clonedatabtn);

        mondayTV = findViewById(R.id.mondayt);
        tuesdayTV = findViewById(R.id.tuesdayt);
        wednesdayTV = findViewById(R.id.wednesdayt);
        thursdayTV = findViewById(R.id.thursdayt);
        fridayTV = findViewById(R.id.fridayt);
        saturdayTV = findViewById(R.id.saturdayt);
        sundayTV = findViewById(R.id.sundayt);

        mondayRE = findViewById(R.id.mondayr);
        tuesdayRE = findViewById(R.id.tuesdayr);
        wednesdayRE = findViewById(R.id.wednesdayr);
        thursdayRE = findViewById(R.id.thursdayr);
        fridayRE = findViewById(R.id.fridayr);
        saturdayRE = findViewById(R.id.saturdayr);
        sundayRE = findViewById(R.id.sundayr);

        TextView plusTV = findViewById(R.id.week_plus_one);
        TextView minusTV = findViewById(R.id.week_minus_one);

        TextView gotoTV = findViewById(R.id.go_to_btn);

        mondayTV.setOnClickListener(goToEditorA);
        tuesdayTV.setOnClickListener(goToEditorB);
        wednesdayTV.setOnClickListener(goToEditorC);
        thursdayTV.setOnClickListener(goToEditorD);
        fridayTV.setOnClickListener(goToEditorE);
        saturdayTV.setOnClickListener(goToEditorF);
        sundayTV.setOnClickListener(goToEditorG);

        mondayRE.setOnClickListener(goToResultsA);
        tuesdayRE.setOnClickListener(goToResultsB);
        wednesdayRE.setOnClickListener(goToResultsC);
        thursdayRE.setOnClickListener(goToResultsD);
        fridayRE.setOnClickListener(goToResultsE);
        saturdayRE.setOnClickListener(goToResultsF);
        sundayRE.setOnClickListener(goToResultsG);

        plusTV.setOnClickListener(weekPlusOne);
        minusTV.setOnClickListener(weekMinusOne);

        gotoTV.setOnClickListener(updateDisplay);

        clonedata.setOnClickListener(CloneData);
        loadData();

    }

    private void createTable() {
        ContentValues values = new ContentValues();
        String defaultTextTraining = getString(R.string.edit);
        String defaultTextResults = getString(R.string.edit);
        values.put(TrainingEntry.COLUMN_TRAINING_MONDAY, defaultTextTraining);
        values.put(TrainingEntry.COLUMN_TRAINING_TUESDAY, defaultTextTraining);
        values.put(TrainingEntry.COLUMN_TRAINING_WEDNESDAY, defaultTextTraining);
        values.put(TrainingEntry.COLUMN_TRAINING_THURSDAY, defaultTextTraining);
        values.put(TrainingEntry.COLUMN_TRAINING_FRIDAY, defaultTextTraining);
        values.put(TrainingEntry.COLUMN_TRAINING_SATURDAY, defaultTextTraining);
        values.put(TrainingEntry.COLUMN_TRAINING_SUNDAY, defaultTextTraining);
        values.put(TrainingEntry.COLUMN_RESULTS_MONDAY, defaultTextResults);
        values.put(TrainingEntry.COLUMN_RESULTS_TUESDAY, defaultTextResults);
        values.put(TrainingEntry.COLUMN_RESULTS_WEDNESDAY, defaultTextResults);
        values.put(TrainingEntry.COLUMN_RESULTS_THURSDAY, defaultTextResults);
        values.put(TrainingEntry.COLUMN_RESULTS_FRIDAY, defaultTextResults);
        values.put(TrainingEntry.COLUMN_RESULTS_SATURDAY, defaultTextResults);
        values.put(TrainingEntry.COLUMN_RESULTS_SUNDAY, defaultTextResults);

        Log.e(LOG_TAG, "TABLE IS BEING CREATED");
        for (int l = 1; l <= 52; l++) {

            //     String whereClause = TrainingEntry._ID + "=" + l;
            getContentResolver().insert(TrainingEntry.CONTENT_URI, values);

        }
    }

    private void loadData() {
        mondayTV = findViewById(R.id.mondayt);
        tuesdayTV = findViewById(R.id.tuesdayt);
        wednesdayTV = findViewById(R.id.wednesdayt);
        thursdayTV = findViewById(R.id.thursdayt);
        fridayTV = findViewById(R.id.fridayt);
        saturdayTV = findViewById(R.id.saturdayt);
        sundayTV = findViewById(R.id.sundayt);

        mondayRE = findViewById(R.id.mondayr);
        tuesdayRE = findViewById(R.id.tuesdayr);
        wednesdayRE = findViewById(R.id.wednesdayr);
        thursdayRE = findViewById(R.id.thursdayr);
        fridayRE = findViewById(R.id.fridayr);
        saturdayRE = findViewById(R.id.saturdayr);
        sundayRE = findViewById(R.id.sundayr);

        weeknumberTV = findViewById(R.id.WN_display);
        int week_number = Integer.parseInt(weeknumberTV.getText().toString());

        weekNumber = week_number;

        String[] projection = {
                BaseColumns._ID,
                TrainingEntry.COLUMN_TRAINING_MONDAY,
                TrainingEntry.COLUMN_TRAINING_TUESDAY,
                TrainingEntry.COLUMN_TRAINING_WEDNESDAY,
                TrainingEntry.COLUMN_TRAINING_THURSDAY,
                TrainingEntry.COLUMN_TRAINING_FRIDAY,
                TrainingEntry.COLUMN_TRAINING_SATURDAY,
                TrainingEntry.COLUMN_TRAINING_SUNDAY,
                TrainingEntry.COLUMN_RESULTS_MONDAY,
                TrainingEntry.COLUMN_RESULTS_TUESDAY,
                TrainingEntry.COLUMN_RESULTS_WEDNESDAY,
                TrainingEntry.COLUMN_RESULTS_THURSDAY,
                TrainingEntry.COLUMN_RESULTS_FRIDAY,
                TrainingEntry.COLUMN_RESULTS_SATURDAY,
                TrainingEntry.COLUMN_RESULTS_SUNDAY
        };
        String selection = TrainingEntry._ID + "=" + Integer.toString(week_number);
        Log.e(LOG_TAG, "SELECTION FOR QUERY: " + selection);

        mCurrentWeekUri = ContentUris.withAppendedId(TrainingEntry.CONTENT_URI, week_number);

        Cursor cursor = getContentResolver().query(mCurrentWeekUri, projection, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int weekNumberCI = cursor.getColumnIndex(TrainingEntry._ID);
            int mondayCI = cursor.getColumnIndex(TrainingEntry.COLUMN_TRAINING_MONDAY);
            int tuesdayCI = cursor.getColumnIndex(TrainingEntry.COLUMN_TRAINING_TUESDAY);
            int wednesdayCI = cursor.getColumnIndex(TrainingEntry.COLUMN_TRAINING_WEDNESDAY);
            int thursdayCI = cursor.getColumnIndex(TrainingEntry.COLUMN_TRAINING_THURSDAY);
            int fridayCI = cursor.getColumnIndex(TrainingEntry.COLUMN_TRAINING_FRIDAY);
            int saturdayCI = cursor.getColumnIndex(TrainingEntry.COLUMN_TRAINING_SATURDAY);
            int sundayCI = cursor.getColumnIndex(TrainingEntry.COLUMN_TRAINING_SUNDAY);
            int mondayRCI = cursor.getColumnIndex(TrainingEntry.COLUMN_RESULTS_MONDAY);
            int tuesdayRCI = cursor.getColumnIndex(TrainingEntry.COLUMN_RESULTS_TUESDAY);
            int wednesdayRCI = cursor.getColumnIndex(TrainingEntry.COLUMN_RESULTS_WEDNESDAY);
            int thursdayRCI = cursor.getColumnIndex(TrainingEntry.COLUMN_RESULTS_THURSDAY);
            int fridayRCI = cursor.getColumnIndex(TrainingEntry.COLUMN_RESULTS_FRIDAY);
            int saturdayRCI = cursor.getColumnIndex(TrainingEntry.COLUMN_RESULTS_SATURDAY);
            int sundayRCI = cursor.getColumnIndex(TrainingEntry.COLUMN_RESULTS_SUNDAY);

            String weekNumberT = cursor.getString(weekNumberCI);
            String mondayT = cursor.getString(mondayCI);
            String tuesdayT = cursor.getString(tuesdayCI);
            String wednesdayT = cursor.getString(wednesdayCI);
            String thursdayT = cursor.getString(thursdayCI);
            String fridayT = cursor.getString(fridayCI);
            String saturdayT = cursor.getString(saturdayCI);
            String sundayT = cursor.getString(sundayCI);

            String mondayRe = cursor.getString(mondayRCI);
            String tuesdayRe = cursor.getString(tuesdayRCI);
            String wednesdayRe = cursor.getString(wednesdayRCI);
            String thursdayRe = cursor.getString(thursdayRCI);
            String fridayRe = cursor.getString(fridayRCI);
            String saturdayRe = cursor.getString(saturdayRCI);
            String sundayRe = cursor.getString(sundayRCI);

            weeknumberTV.setText(weekNumberT);
            mondayTV.setText(mondayT);
            tuesdayTV.setText(tuesdayT);
            wednesdayTV.setText(wednesdayT);
            thursdayTV.setText(thursdayT);
            fridayTV.setText(fridayT);
            saturdayTV.setText(saturdayT);
            sundayTV.setText(sundayT);

            mondayRE.setText(mondayRe);
            tuesdayRE.setText(tuesdayRe);
            wednesdayRE.setText(wednesdayRe);
            thursdayRE.setText(thursdayRe);
            fridayRE.setText(fridayRe);
            saturdayRE.setText(saturdayRe);
            sundayRE.setText(sundayRe);

            cursor.close();

            currentWeekDisplayTV.setText(weekNumberT);
        }


    }

    private String planToTextConstruct() {
        String header = "Week " + weekNumber + " - training plan";
        String monday = "Monday \n" + mondayTV.getText().toString() + "\n" + mondayRE.getText().toString();
        String tuesday = "Tuesday \n" + tuesdayTV.getText().toString() + "\n" + tuesdayRE.getText().toString();
        String wednesday = "Wendesday \n" + wednesdayTV.getText().toString() + "\n" + wednesdayRE.getText().toString();
        String thursday = "Thursday \n" + thursdayTV.getText().toString() + "\n" + thursdayRE.getText().toString();
        String friday = "Friday \n" + fridayTV.getText().toString() + "\n" + fridayRE.getText().toString();
        String saturday = "Saturday \n" + saturdayTV.getText().toString() + "\n" + saturdayRE.getText().toString();
        String sunday = "Sunday \n" + sundayTV.getText().toString() + "\n" + sundayRE.getText().toString();
        String footer = "Email sent via TrainingApp";

        String weekPlan = header + "\n\n" + monday + "\n\n" + tuesday + "\n\n" + wednesday + "\n\n" + thursday
                + "\n\n" + friday + "\n\n" + saturday + "\n\n" + sunday + "\n\n\n" + footer;
        return weekPlan;
    }


    private View.OnClickListener export_data = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sendPlan();

        }


    };



    private void sendPlan() {

        String output = planToTextConstruct();

        Log.i("Send email", "");


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailo:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Training plan week "+weekNumber);
        emailIntent.putExtra(Intent.EXTRA_TEXT, output);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private View.OnClickListener updateDisplay = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            weeknumberTV = findViewById(R.id.WN_display);
            weekNumber = Integer.parseInt(weeknumberTV.getText().toString());
            Log.e(LOG_TAG, "UI UPDATED, WEEK NUMBER: " + weekNumber);
            loadData();

        }
    };

    private View.OnClickListener weekPlusOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            weeknumberTV = findViewById(R.id.WN_display);
            int week_count = Integer.parseInt(weeknumberTV.getText().toString());
            if (week_count < 52) {
                week_count = week_count + 1;
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.week_update_fail_1), Toast.LENGTH_SHORT).show();

            }
            weeknumberTV.setText(Integer.toString(week_count));
        }
    };

    private View.OnClickListener weekMinusOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            weeknumberTV = findViewById(R.id.WN_display);
            int week_count = Integer.parseInt(weeknumberTV.getText().toString());
            if (week_count > 1) {
                week_count = week_count - 1;

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.week_update_fail_2), Toast.LENGTH_SHORT).show();

            }
            weeknumberTV.setText(Integer.toString(week_count));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.spanish_language:
                Toast.makeText(this, "Has seleccionado Español", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, "SPANISH SELECTED");
                setLocale("es");
                return true;

            case R.id.english_language:
                Toast.makeText(this, "You have selected English", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, "ENGLISH SELECTED");
                setLocale("en");
                return true;

            case R.id.help_button:
                AlertDialog.Builder builder_help = new AlertDialog.Builder(this);

                builder_help.setMessage(getString(R.string.help_text));
                builder_help.setPositiveButton(getString(R.string.confirmation_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }

                });


                builder_help.show();
                return true;
        }

        return super.onOptionsItemSelected(item);


    }


    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }

    private View.OnClickListener goToEditorA = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingEditor.class);
            TextView mondayTV = findViewById(R.id.mondayt);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String mondayTVstr = "1" + weeknumber + "zx0" + mondayTV.getText().toString();
            gtE.putExtra("previous_plan", mondayTVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToResultsA = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingResults.class);
            TextView mondayRE = findViewById(R.id.mondayr);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String mondayTVstr = "1" + weeknumber + "zx0" + mondayRE.getText().toString();
            gtE.putExtra("previous_plan", mondayTVstr);
            startActivity(gtE);

        }
    };


    private View.OnClickListener goToEditorB = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingEditor.class);
            TextView tuesdayTV = findViewById(R.id.tuesdayt);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String tuesdayTVstr = "2" + weeknumber + "zx0" + tuesdayTV.getText().toString();
            gtE.putExtra("previous_plan", tuesdayTVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToResultsB = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingResults.class);
            TextView tuesdayRE = findViewById(R.id.tuesdayr);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String TVstr = "2" + weeknumber + "zx0" + tuesdayRE.getText().toString();
            gtE.putExtra("previous_plan", TVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToEditorC = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingEditor.class);
            TextView wednesdayTV = findViewById(R.id.wednesdayt);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String wednesdayTVstr = "3" + weeknumber + "zx0" + wednesdayTV.getText().toString();
            gtE.putExtra("previous_plan", wednesdayTVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToResultsC = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingResults.class);
            weeknumberTV = findViewById(R.id.WN_display);
            TextView wednesdayRE = findViewById(R.id.wednesdayr);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String TVstr = "3" + weeknumber + "zx0" + wednesdayRE.getText().toString();
            gtE.putExtra("previous_plan", TVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToEditorD = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingEditor.class);
            TextView thursdayTV = findViewById(R.id.thursdayt);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String thursdayTVstr = "4" + weeknumber + "zx0" + thursdayTV.getText().toString();
            gtE.putExtra("previous_plan", thursdayTVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToResultsD = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingResults.class);
            TextView thursdayRE = findViewById(R.id.thursdayr);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String TVstr = "4" + weeknumber + "zx0" + thursdayRE.getText().toString();
            gtE.putExtra("previous_plan", TVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToEditorE = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingEditor.class);
            TextView fridayTV = findViewById(R.id.fridayt);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String fridayTVstr = "5" + weeknumber + "zx0" + fridayTV.getText().toString();
            gtE.putExtra("previous_plan", fridayTVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToResultsE = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingResults.class);
            TextView fridayRE = findViewById(R.id.fridayr);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String TVstr = "5" + weeknumber + "zx0" + fridayRE.getText().toString();
            gtE.putExtra("previous_plan", TVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToEditorF = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingEditor.class);
            TextView saturdayTV = findViewById(R.id.saturdayt);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String saturdayTVstr = "6" + weeknumber + "zx0" + saturdayTV.getText().toString();
            gtE.putExtra("previous_plan", saturdayTVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToResultsF = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingResults.class);
            TextView saturdayRE = findViewById(R.id.saturdayr);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String TVstr = "6" + weeknumber + "zx0" + saturdayRE.getText().toString();
            gtE.putExtra("previous_plan", TVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToEditorG = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingEditor.class);
            TextView sundayTV = findViewById(R.id.sundayt);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String sundayTVstr = "7" + weeknumber + "zx0" + sundayTV.getText().toString();
            gtE.putExtra("previous_plan", sundayTVstr);
            startActivity(gtE);

        }
    };

    private View.OnClickListener goToResultsG = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gtE = new Intent(MainActivity.this, TrainingResults.class);
            TextView sundayRE = findViewById(R.id.sundayr);
            weeknumberTV = findViewById(R.id.WN_display);
            String weeknumber = weeknumberTV.getText().toString();
            String TVstr = "7" + weeknumber + "zx0" + sundayRE.getText().toString();
            gtE.putExtra("previous_plan", TVstr);
            startActivity(gtE);

        }
    };


    private View.OnClickListener CloneData = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RequestWeekDialog();
        }
    };

    private void RequestWeekDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText edittext = new EditText(this);
        edittext.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(dpToPx(30), 0, dpToPx(30), 0);
        edittext.setLayoutParams(lp);

        builder.setMessage(getString(R.string.clone_request));
        builder.setView(edittext);
        builder.setPositiveButton(getString(R.string.clone_confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean validInput = TextUtils.isDigitsOnly(edittext.getText());

                if (validInput) {
                    int weekToBeCloned = Integer.parseInt(edittext.getText().toString());

                    if (weekToBeCloned >= 1 && weekToBeCloned <= 52) {
                        cloneCurrentWeek(weekToBeCloned);
                        Toast.makeText(getApplicationContext(), getString(R.string.clone_success), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.clone_fail_1), Toast.LENGTH_SHORT).show();
                        RequestWeekDialog();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.clone_fail_2), Toast.LENGTH_SHORT).show();
                    RequestWeekDialog();
                }

            }
        });

        builder.setNegativeButton(getString(R.string.clone_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();

    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void cloneCurrentWeek(int weekToBeCloned) {

        mondayTV = findViewById(R.id.mondayt);
        tuesdayTV = findViewById(R.id.tuesdayt);
        wednesdayTV = findViewById(R.id.wednesdayt);
        thursdayTV = findViewById(R.id.thursdayt);
        fridayTV = findViewById(R.id.fridayt);
        saturdayTV = findViewById(R.id.saturdayt);
        sundayTV = findViewById(R.id.sundayt);

        String mon_tr = mondayTV.getText().toString();
        String tue_tr = tuesdayTV.getText().toString();
        String wed_tr = wednesdayTV.getText().toString();
        String thu_tr = thursdayTV.getText().toString();
        String fri_tr = fridayTV.getText().toString();
        String sat_tr = saturdayTV.getText().toString();
        String sun_tr = sundayTV.getText().toString();

        ContentValues values = new ContentValues();

        values.put(TrainingEntry.COLUMN_TRAINING_MONDAY, mon_tr);
        values.put(TrainingEntry.COLUMN_TRAINING_TUESDAY, tue_tr);
        values.put(TrainingEntry.COLUMN_TRAINING_WEDNESDAY, wed_tr);
        values.put(TrainingEntry.COLUMN_TRAINING_THURSDAY, thu_tr);
        values.put(TrainingEntry.COLUMN_TRAINING_FRIDAY, fri_tr);
        values.put(TrainingEntry.COLUMN_TRAINING_SATURDAY, sat_tr);
        values.put(TrainingEntry.COLUMN_TRAINING_SUNDAY, sun_tr);

        Uri mResultingWeekUri = ContentUris.withAppendedId(TrainingEntry.CONTENT_URI, weekToBeCloned);

        getContentResolver().update(mResultingWeekUri, values, null, null);


    }
}

