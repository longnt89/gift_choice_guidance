package android.project.longnt.giftchoiceguidance.Business;

import android.project.longnt.giftchoiceguidance.Data.DBService.SQLiteHandler;
import android.project.longnt.giftchoiceguidance.Constant.StatusConstants.LogType;
import android.project.longnt.giftchoiceguidance.R;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.IOException;

/**
 * Created by LongNT on 19/12/17.
 */

public abstract class AbsActivity extends AppCompatActivity {
    LinearLayout coverLayout;
    LinearLayout contentLayout;

    SQLiteHandler sqLiteHandler = null;
    String tag = "[Abstract base activity]";

    @Override
    public void setContentView(int layoutResID) {
        coverLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_abs, null);
        contentLayout = (LinearLayout)coverLayout.findViewById(R.id.lyt_activityabs_content);
        getLayoutInflater().inflate(layoutResID, contentLayout, true);
        super.setContentView(coverLayout);
    }

    private boolean openSQLiteSession() {
        try {
            if (sqLiteHandler == null) {
                sqLiteHandler = new SQLiteHandler(getApplicationContext());
            }

            sqLiteHandler.createDB();

            sqLiteHandler.openDB();

            return true;
        }
        catch (Exception e) {
            log(e.getMessage(), LogType.ERROR);
            return false;
        }
    }

    private boolean closeSQLiteSession() {
        try {
            if (sqLiteHandler != null) {
                sqLiteHandler.close();
            }

            return true;
        }
        catch (Exception e) {
            log(e.getMessage(), LogType.ERROR);
            return false;
        }
    }

    private void log(String msg, LogType logType) {
        switch (logType) {
            case INFO:
                Log.i(tag, msg);
                break;

            case DEBUG:
                Log.d(tag, msg);
                break;

            case ERROR:
                Log.e(tag, msg);
                break;

            default:
                break;
        }
    }
}
