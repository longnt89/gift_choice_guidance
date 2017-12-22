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

public class ListObjectAdapter extends ArrayAdapter<ObjModel> {
    private Button btnObj;

    LayoutInflater layoutInflater;

    public ListObjectAdapter(ArrayList<ObjModel> data, Context context) {
        super(context, R.layout.listitem_objects);

        layoutInflater = LayoutInflater.from(getContext());
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ObjModel objModel = getItem(position);

        convertView = layoutInflater.inflate(R.layout.listitem_objects, parent, false);

        btnObj = (Button)convertView.findViewById(R.id.btn_listitemobjects_obj);

        btnObj.setText(objModel.getObjType());
        btnObj.setTag(BusinessConstants.VIEW_TAG_OBJ_ID, objModel.getObjId());

        btnObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int objId = (int)btnObj.getTag(BusinessConstants.VIEW_TAG_OBJ_ID);

                // Move the new activity here
            }
        });

        return convertView;
    }
}
