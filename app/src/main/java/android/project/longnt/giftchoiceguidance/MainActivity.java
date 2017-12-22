package android.project.longnt.giftchoiceguidance;

import android.os.Bundle;
import android.project.longnt.giftchoiceguidance.Business.AbsActivity;
import android.project.longnt.giftchoiceguidance.Business.ListObjectAdapter;
import android.project.longnt.giftchoiceguidance.Business.ObjModel;
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
        ArrayList<ObjModel> objModels;

        if (openSQLiteSession()) {
            objModels = sqLiteHandler.getAllObjects();

            if (objModels == null) {
                // Error here

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
}
