package android.project.longnt.giftchoiceguidance.Business;

import android.content.Context;
import android.content.Intent;
import android.project.longnt.giftchoiceguidance.Constant.BusinessConstants;
import android.project.longnt.giftchoiceguidance.Constant.StatusConstants;
import android.project.longnt.giftchoiceguidance.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by LongNT on 22/12/17.
 */

public class ListEventAdapter extends ArrayAdapter<EventModel> {
    private Button btnEvent;

    AbsActivity mAbsActivity;
    ObjModel mObjModel;

    LayoutInflater layoutInflater;

    public ListEventAdapter(ArrayList<EventModel> data, AbsActivity absActivity, ObjModel objModel) {
        super(absActivity, R.layout.listitem_events, data);

        mAbsActivity = absActivity;

        mObjModel = objModel;

        layoutInflater = LayoutInflater.from(getContext());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            EventModel eventModel = getItem(position);

            convertView = layoutInflater.inflate(R.layout.listitem_events, parent, false);

            btnEvent = (Button)convertView.findViewById(R.id.btn_listitemevents_event);

            btnEvent.setText(eventModel.getEvnType());
            btnEvent.setTag(BusinessConstants.VIEW_TAG_EVN_ID, eventModel.getEvnId());
            btnEvent.setTag(BusinessConstants.VIEW_TAG_EVN_TYPE, eventModel.getEvnType());

            btnEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int evnId = (int)btnEvent.getTag(BusinessConstants.VIEW_TAG_EVN_ID);
                    String evnType = (String) btnEvent.getTag(BusinessConstants.VIEW_TAG_EVN_TYPE);

                    Intent itnAdvice = new Intent(mAbsActivity, AdviceActivity.class);

                    itnAdvice.putExtra(BusinessConstants.EXTRA_KEY_OBJ_ID, mObjModel.getObjId());
                    itnAdvice.putExtra(BusinessConstants.EXTRA_KEY_OBJ_TYPE, mObjModel.getObjType());
                    itnAdvice.putExtra(BusinessConstants.EXTRA_KEY_EVN_ID, evnId);
                    itnAdvice.putExtra(BusinessConstants.EXTRA_KEY_EVN_TYPE, evnType);

                    mAbsActivity.startActivity(itnAdvice);
                }
            });
        }
        catch (Exception e) {
            mAbsActivity.log(e.getMessage(), StatusConstants.LogType.ERROR);
        }

        return convertView;
    }
}
