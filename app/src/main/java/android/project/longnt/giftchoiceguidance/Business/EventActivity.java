package android.project.longnt.giftchoiceguidance.Business;

import android.project.longnt.giftchoiceguidance.Constant.BusinessConstants;
import android.project.longnt.giftchoiceguidance.Constant.StatusConstants;
import android.os.Bundle;
import android.project.longnt.giftchoiceguidance.R;
import android.widget.ListView;

import java.util.ArrayList;

public class EventActivity extends AbsActivity {
    int mObjId;
    String mObjType;
    ListEventAdapter listEventAdapter;
    ListView listEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if (getIntentExtra()) {
            displayEvents();
        }
        else {
            showError(null, null, 0);
            log(getString(R.string.errinfo_sysmsg_intent_extra_not_found), StatusConstants.LogType.ERROR);
        }
    }

    private boolean getIntentExtra() {
        try {
            mObjId = getIntent().getIntExtra(BusinessConstants.EXTRA_KEY_OBJ_ID, BusinessConstants.EXTRA_VALUE_DEFAULT_OBJ_ID);
            mObjType = getIntent().getStringExtra(BusinessConstants.EXTRA_KEY_OBJ_TYPE);

            if (mObjId == BusinessConstants.EXTRA_VALUE_DEFAULT_OBJ_ID || mObjType == null) {
                return false;
            }
        }
        catch (Exception e) {
            log(e.getMessage(), StatusConstants.LogType.ERROR);
            return false;
        }

        return true;
    }

    private void displayEvents() {
        try {
            ArrayList<EventModel> eventModels;

            if (openSQLiteSession()) {
                eventModels = sqLiteHandler.getEventsByObject(mObjId);

                if (eventModels == null) {
                    showError(null, null, 0);
                }
                else {
                    listEventAdapter = new ListEventAdapter(eventModels, this, new ObjModel(mObjId, mObjType));

                    listEvents = (ListView)findViewById(R.id.lst_activityevent_event);
                    listEvents.setAdapter(listEventAdapter);
                }

                closeSQLiteSession();
            }
            else {
                showError(null, null, 0);
            }
        }
        catch (Exception e) {
            log(e.getMessage(), StatusConstants.LogType.ERROR);
        }
    }
}
