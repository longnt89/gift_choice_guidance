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
        coverLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.activity_abs, null);  // The base layout
        contentLayout = (LinearLayout)coverLayout.findViewById(R.id.lyt_activityabs_content);            // The frame layout where the activity content is placed.
        getLayoutInflater().inflate(layoutResID, contentLayout, true);            // Places the activity layout inside the activity content frame.
        super.setContentView(coverLayout);                                                       // Sets the content view as the merged layouts.
    }
}
