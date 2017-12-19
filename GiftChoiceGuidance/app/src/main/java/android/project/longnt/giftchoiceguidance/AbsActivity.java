package android.project.longnt.giftchoiceguidance;

import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Created by LongNT on 19/12/17.
 */

public abstract class AbsActivity extends AppCompatActivity {
    protected LinearLayout coverLayout;
    protected LinearLayout contentLayout;

    @Override
    public void setContentView(int layoutResID) {
        coverLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_abs, null);
        contentLayout = (LinearLayout)coverLayout.findViewById(R.id.lyt_activityabs_content);
        getLayoutInflater().inflate(layoutResID, contentLayout, true);
        super.setContentView(coverLayout);
    }
}
