package pabs.trainingapp1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pabs.trainingapp1.data.TrainingPlan.TrainingEntry;

public class TrainingResults extends AppCompatActivity {

    int day_identifier_int;
    int week_identifier_int;
    String week_identifier;
    String previous_plan;
    private Uri mCurrentWeekUri;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_results);

        TextView save_changes_btn = findViewById(R.id.save_results);
        save_changes_btn.setOnClickListener(saveTrainingResults);

        setTitle("Training Results");


        Intent intent = getIntent();
        previous_plan = intent.getExtras().getString("previous_plan");

        String day_identifier = String.valueOf(previous_plan.charAt(0));
        day_identifier_int = Integer.parseInt(day_identifier);

        week_identifier = previous_plan.substring(1, previous_plan.indexOf("zx0"));

        week_identifier_int = Integer.parseInt(week_identifier);

        if (day_identifier_int == 1) {
            setTitle("MONDAY TRAINING RESULTS");
        }
        if (day_identifier_int == 2) {
            setTitle("TUESDAY TRAINING RESULTS");
        }
        if (day_identifier_int == 3) {
            setTitle("WEDNESDAY TRAINING RESULTS");
        }
        if (day_identifier_int == 4) {
            setTitle("THURSDAY TRAINING RESULTS");
        }
        if (day_identifier_int == 5) {
            setTitle("FRIDAY TRAINING RESULTS");
        }
        if (day_identifier_int == 6) {
            setTitle("SATURDAY TRAINING RESULTS");
        }
        if (day_identifier_int == 7) {
            setTitle("SUNDAY TRAINING RESULTS");
        }

        mCurrentWeekUri = ContentUris.withAppendedId(TrainingEntry.CONTENT_URI, week_identifier_int);

        EditText resultsET = findViewById(R.id.results_text);
        String previous_plan_clean = previous_plan.substring(previous_plan.indexOf("zx0")+3, previous_plan.length());
        if (previous_plan_clean.equals("Edit")) {
            resultsET.setText("");
        } else {
            resultsET.setText(previous_plan_clean);
        }
    }




    private View.OnClickListener saveTrainingResults = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            EditText daily_results = findViewById(R.id.results_text);
            String results_string = daily_results.getText().toString();


            ContentValues values = new ContentValues();

            if (day_identifier_int == 1) {
                values.put(TrainingEntry.COLUMN_RESULTS_MONDAY, results_string);
            }
            if (day_identifier_int == 2) {
                values.put(TrainingEntry.COLUMN_RESULTS_TUESDAY, results_string);
            }
            if (day_identifier_int == 3) {
                values.put(TrainingEntry.COLUMN_RESULTS_WEDNESDAY, results_string);
            }
            if (day_identifier_int == 4) {
                values.put(TrainingEntry.COLUMN_RESULTS_THURSDAY, results_string);
            }
            if (day_identifier_int == 5) {
                values.put(TrainingEntry.COLUMN_RESULTS_FRIDAY, results_string);
            }
            if (day_identifier_int == 6) {
                values.put(TrainingEntry.COLUMN_RESULTS_SATURDAY, results_string);
            }
            if (day_identifier_int == 7) {
                values.put(TrainingEntry.COLUMN_RESULTS_SUNDAY, results_string);
            }

            getContentResolver().update(mCurrentWeekUri, values, null, null);

            Intent backHome = new Intent(TrainingResults.this, MainActivity.class);
            backHome.putExtra("weekIdentfier", week_identifier);
            startActivity(backHome);


        }


    };

}
