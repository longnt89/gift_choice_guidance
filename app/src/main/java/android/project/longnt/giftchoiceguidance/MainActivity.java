package android.project.longnt.giftchoiceguidance;

import android.os.Bundle;
import android.project.longnt.giftchoiceguidance.Business.AbsActivity;
import android.project.longnt.giftchoiceguidance.Business.ListObjectAdapter;
import android.project.longnt.giftchoiceguidance.Business.ObjModel;
import android.project.longnt.giftchoiceguidance.Constant.StatusConstants;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AbsActivity {
    ListView listObjs;
    ListObjectAdapter listObjectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayObjects();
    }

    private void displayObjects() {
        try {
            ArrayList<ObjModel> objModels;

            if (openSQLiteSession()) {
                objModels = sqLiteHandler.getAllObjects();

                if (objModels == null) {
                    showError(null, null, 0);

                }
                else {
                    listObjectAdapter = new ListObjectAdapter(objModels, this);

                    listObjs = (ListView)findViewById(R.id.lst_activitymain_objs);
                    listObjs.setAdapter(listObjectAdapter);
                }

                closeSQLiteSession();
            }
            else {
                showError(null, null, 0);
            }
        }
        catch (Exception e) {
            showError(null, null, 0);
            log(e.getMessage(), StatusConstants.LogType.ERROR);
        }
    }
}
