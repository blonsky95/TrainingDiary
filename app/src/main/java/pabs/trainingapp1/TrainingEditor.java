package pabs.trainingapp1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pabs.trainingapp1.data.PlanDbHelper;
import pabs.trainingapp1.data.TrainingPlan.TrainingEntry;

import static java.util.logging.Logger.global;

public class TrainingEditor extends AppCompatActivity {

    int day_identifier_int;
    int week_identifier_int;
    String week_identifier;
    String previous_plan;
    private Uri mCurrentWeekUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_editor);

        TextView save_changes_btn = findViewById(R.id.save_changes);
        save_changes_btn.setOnClickListener(savePlanChanges);

        setTitle("Edit Training");


        Intent intent = getIntent();
        previous_plan = intent.getExtras().getString("previous_plan");
        String day_identifier = String.valueOf(previous_plan.charAt(0));
        day_identifier_int = Integer.parseInt(day_identifier);

        week_identifier = previous_plan.substring(1, previous_plan.indexOf("zx0"));

        week_identifier_int = Integer.parseInt(week_identifier);


        mCurrentWeekUri = ContentUris.withAppendedId(TrainingEntry.CONTENT_URI, week_identifier_int);

        if (day_identifier_int == 1) {
            setTitle("EDIT MONDAY TRAINING");

        }
        if (day_identifier_int == 2) {
            setTitle("EDIT TUESDAY TRAINING");
        }
        if (day_identifier_int == 3) {
            setTitle("EDIT WEDNESDAY TRAINING");
        }
        if (day_identifier_int == 4) {
            setTitle("EDIT THURSDAY TRAINING");
        }
        if (day_identifier_int == 5) {
            setTitle("EDIT FRIDAY TRAINING");
        }
        if (day_identifier_int == 6) {
            setTitle("EDIT SATURDAY TRAINING");
        }
        if (day_identifier_int == 7) {
            setTitle("EDIT SUNDAY TRAINING");
        }

        EditText trainingET = findViewById(R.id.training_text);
        String previous_plan_clean = previous_plan.substring(previous_plan.indexOf("zx0") + 3, previous_plan.length());
        if (previous_plan_clean.equals("Edit")) {
            trainingET.setText("");
        } else {
            trainingET.setText(previous_plan_clean);
        }
    }


    private View.OnClickListener savePlanChanges = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            EditText daily_training = findViewById(R.id.training_text);
            String training_string = daily_training.getText().toString();

            ContentValues values = new ContentValues();

            if (day_identifier_int == 1) {
                values.put(TrainingEntry.COLUMN_TRAINING_MONDAY, training_string);
            }
            if (day_identifier_int == 2) {
                values.put(TrainingEntry.COLUMN_TRAINING_TUESDAY, training_string);
            }
            if (day_identifier_int == 3) {
                values.put(TrainingEntry.COLUMN_TRAINING_WEDNESDAY, training_string);
            }
            if (day_identifier_int == 4) {
                values.put(TrainingEntry.COLUMN_TRAINING_THURSDAY, training_string);
            }
            if (day_identifier_int == 5) {
                values.put(TrainingEntry.COLUMN_TRAINING_FRIDAY, training_string);
            }
            if (day_identifier_int == 6) {
                values.put(TrainingEntry.COLUMN_TRAINING_SATURDAY, training_string);
            }
            if (day_identifier_int == 7) {
                values.put(TrainingEntry.COLUMN_TRAINING_SUNDAY, training_string);
            }


            getContentResolver().update(mCurrentWeekUri, values, null, null);

            Intent backHome = new Intent(TrainingEditor.this, MainActivity.class);
            backHome.putExtra("weekIdentfier", week_identifier);
            startActivity(backHome);

        }
    };

}
