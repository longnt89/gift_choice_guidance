package android.project.longnt.giftchoiceguidance.Business;

import android.content.DialogInterface;
import android.os.Handler;
import android.project.longnt.giftchoiceguidance.Constant.BusinessConstants;
import android.project.longnt.giftchoiceguidance.Data.DBService.SQLiteHandler;
import android.project.longnt.giftchoiceguidance.Constant.StatusConstants.LogType;
import android.project.longnt.giftchoiceguidance.R;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by LongNT on 19/12/17.
 */

public abstract class AbsActivity extends AppCompatActivity {
    LinearLayout coverLayout;
    LinearLayout contentLayout;

    protected SQLiteHandler sqLiteHandler = null;
    String tag = "Abstract base activity";

    @Override
    public void setContentView(int layoutResID) {
        coverLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_abs, null);
        contentLayout = (LinearLayout)coverLayout.findViewById(R.id.lyt_activityabs_content);
        getLayoutInflater().inflate(layoutResID, contentLayout, true);
        super.setContentView(coverLayout);
    }

    protected boolean openSQLiteSession() {
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

    protected boolean closeSQLiteSession() {
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

    protected void log(String msg, LogType logType) {
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

    protected void showInfo(String title, String msg, int delayInMillis) {
        if (title == "" || title == null) {
            title = getResources().getString(R.string.dialog_info_title_default);
        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.style_dialog_info).setTitle(title).setMessage(msg);
        final AlertDialog alert = dialog.create();
        alert.show();
        alert.setCanceledOnTouchOutside(true);

        if (delayInMillis >= BusinessConstants.DIALOG_USE_DELAY_FROM) {
            delayDialog(alert, delayInMillis);
        }
    }

    protected void showWarning(String title, String msg, int delayInMillis) {
        if (title == "" || title == null) {
            title = getResources().getString(R.string.dialog_warning_title_default);
        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.style_dialog_warning).setTitle(title).setMessage(msg);
        final AlertDialog alert = dialog.create();
        alert.show();
        alert.setCanceledOnTouchOutside(true);

        if (delayInMillis >= BusinessConstants.DIALOG_USE_DELAY_FROM) {
            delayDialog(alert, delayInMillis);
        }
    }

    protected void showError(String title, String msg, int delayInMillis) {
        if (title == "" || title == null) {
            title = getResources().getString(R.string.dialog_info_title_default);
        }

        if (msg == "" || msg == null) {
            msg = getResources().getString(R.string.dialog_error_msg_default);
        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.style_dialog_error).setTitle(title).setMessage(msg);
        final AlertDialog alert = dialog.create();
        alert.show();
        alert.setCanceledOnTouchOutside(true);

        if (delayInMillis >= BusinessConstants.DIALOG_USE_DELAY_FROM) {
            delayDialog(alert, delayInMillis);
        }
    }

    private void delayDialog(final AlertDialog alertDialog, int delayInMillis) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        };

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, delayInMillis);
    }

    protected void demandConfirmation(String title, String msg, DialogInterface.OnClickListener confirmListener,
                                                                DialogInterface.OnClickListener cancelListener) {
        if (title == "" || title == null) {
            title = getResources().getString(R.string.dialog_confirm_title_default);
        }

        if (msg == "" || msg == null) {
            msg = getResources().getString(R.string.dialog_confirm_msg_default);
        }

        String strYes = getResources().getString(R.string.dialog_confirm_yes);
        String strNo = getResources().getString(R.string.dialog_confirm_no);

        new AlertDialog.Builder(this).setTitle(title).setMessage(msg).setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(strYes, confirmListener)
                                        .setNegativeButton(strNo, cancelListener)
                                        .show();
    }
}
