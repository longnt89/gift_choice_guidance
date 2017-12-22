package android.project.longnt.giftchoiceguidance.Business;

import android.content.Context;
import android.project.longnt.giftchoiceguidance.Constant.BusinessConstants;
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

    LayoutInflater layoutInflater;

    public ListEventAdapter(ArrayList<EventModel> data, Context context) {
        super(context, R.layout.listitem_events);

        layoutInflater = LayoutInflater.from(getContext());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        EventModel eventModel = getItem(position);

        convertView = layoutInflater.inflate(R.layout.listitem_events, parent, false);

        btnEvent = (Button)convertView.findViewById(R.id.btn_listitemevents_event);

        btnEvent.setText(eventModel.getEvnType());
        btnEvent.setTag(BusinessConstants.VIEW_TAG_OBJ_ID, eventModel.getObjId());
        btnEvent.setTag(BusinessConstants.VIEW_TAG_EVN_ID, eventModel.getEvnId());

        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int objId = (int)btnEvent.getTag(BusinessConstants.VIEW_TAG_OBJ_ID);
                int evnId = (int)btnEvent.getTag(BusinessConstants.VIEW_TAG_EVN_ID);

                // Move the new activity here
            }
        });

        return convertView;
    }
}
