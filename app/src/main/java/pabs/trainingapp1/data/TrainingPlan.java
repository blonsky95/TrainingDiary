package pabs.trainingapp1.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Pablo-W8 on 23/02/2018.
 */

public class TrainingPlan {


    private TrainingPlan() {
    }

    public static final String CONTENT_AUTHORITY = "pabs.trainingapp1.data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TRAININGS = "trainings";

    public static final class TrainingEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRAININGS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAININGS;
        //
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAININGS;

        public final static String TABLE_NAME = "plans";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_TRAINING_MONDAY = "monday";

        public final static String COLUMN_RESULTS_MONDAY = "mondayR";

        public final static String COLUMN_TRAINING_TUESDAY = "tuesday";

        public final static String COLUMN_RESULTS_TUESDAY = "tuesday_results";

        public final static String COLUMN_TRAINING_WEDNESDAY = "wednesday";

        public final static String COLUMN_RESULTS_WEDNESDAY = "wednesday_results";

        public final static String COLUMN_TRAINING_THURSDAY = "thursday";

        public final static String COLUMN_RESULTS_THURSDAY = "thursday_results";

        public final static String COLUMN_TRAINING_FRIDAY = "friday";

        public final static String COLUMN_RESULTS_FRIDAY = "friday_results";

        public final static String COLUMN_TRAINING_SATURDAY = "saturday";

        public final static String COLUMN_RESULTS_SATURDAY = "saturday_results";

        public final static String COLUMN_TRAINING_SUNDAY = "sunday";

        public final static String COLUMN_RESULTS_SUNDAY = "sunday_results";


    }

}


