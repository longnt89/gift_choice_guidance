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

public class ListObjectAdapter extends ArrayAdapter<ObjModel> {
    private Button btnObj;

    LayoutInflater layoutInflater;

    AbsActivity mAbsActivity;

    public ListObjectAdapter(ArrayList<ObjModel> data, AbsActivity absActivity) {
        super(absActivity, R.layout.listitem_objects, data);

        mAbsActivity = absActivity;

        layoutInflater = LayoutInflater.from(getContext());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ObjModel objModel = getItem(position);

            convertView = layoutInflater.inflate(R.layout.listitem_objects, parent, false);

            btnObj = (Button)convertView.findViewById(R.id.btn_listitemobjects_obj);

            btnObj.setText(objModel.getObjType());
            btnObj.setTag(BusinessConstants.VIEW_TAG_OBJ_ID, objModel.getObjId());
            btnObj.setTag(BusinessConstants.VIEW_TAG_OBJ_TYPE, objModel.getObjType());

            btnObj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int objId = (int)btnObj.getTag(BusinessConstants.VIEW_TAG_OBJ_ID);
                    String objType = (String)btnObj.getTag(BusinessConstants.VIEW_TAG_OBJ_TYPE);

                    Intent itnEvent = new Intent(mAbsActivity, EventActivity.class);

                    itnEvent.putExtra(BusinessConstants.EXTRA_KEY_OBJ_ID, objId);
                    itnEvent.putExtra(BusinessConstants.EXTRA_KEY_OBJ_TYPE, objType);

                    mAbsActivity.startActivity(itnEvent);
                }
            });
        }
        catch (Exception e) {
            mAbsActivity.log(e.getMessage(), StatusConstants.LogType.ERROR);
        }

        return convertView;
    }
}
