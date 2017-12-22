package android.project.longnt.giftchoiceguidance.Data.DBService;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.project.longnt.giftchoiceguidance.Business.AdviceModel;
import android.project.longnt.giftchoiceguidance.Business.EventModel;
import android.project.longnt.giftchoiceguidance.Business.ObjModel;
import android.project.longnt.giftchoiceguidance.Business.ShopModel;
import android.project.longnt.giftchoiceguidance.Constant.DataConstants;
import android.project.longnt.giftchoiceguidance.R;
import android.project.longnt.giftchoiceguidance.Status.ErrorInfo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by longnt on 12/20/17.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private SQLiteDatabase mDB;

    private final Context mContext;

    private ErrorInfo mErrorInfo;

    public SQLiteHandler(Context context) {
        super(context, DataConstants.DB_NAME, null, 1);
        this.mContext = context;
        this.mErrorInfo = new ErrorInfo(context);
    }

    public void createDB() throws IOException {
        boolean dbExist = checkDB();

        if(dbExist) {

        }
        else{
            this.getReadableDatabase();

            try {
                copyDB();
            }
            catch (IOException e) {
                throw e;
            }
        }
    }

    private boolean checkDB(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DataConstants.DB_PATH + DataConstants.DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e){

        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDB() throws IOException{
        InputStream myInput = mContext.getAssets().open(DataConstants.DB_NAME);

        String outFileName = DataConstants.DB_PATH + DataConstants.DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDB() throws SQLException {
        String myPath = DataConstants.DB_PATH + DataConstants.DB_NAME;
        mDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(mDB != null)
            mDB.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Do something here
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Do something here
    }

    // **************************** HANDLING DATA FOR BUSINESS PURPOSE ************************** //

    public ErrorInfo getErrorInfo() {
        return mErrorInfo;
    }

    public ArrayList<ObjModel> getAllObjects() {
        ArrayList<ObjModel> objModels = new ArrayList<>();

        try {
            Cursor cursorAllObjs = mDB.query(DataConstants.DB_TAB_OBJECT_NAME, null, null, null, null, null, null, null);

            if (cursorAllObjs != null) {
                while (cursorAllObjs.moveToNext()) {
                    int idxObjId = cursorAllObjs.getColumnIndex(DataConstants.DB_TAB_OBJECT_COL_NAME_OBJ_ID);
                    int idxObjType = cursorAllObjs.getColumnIndex(DataConstants.DB_TAB_OBJECT_COL_NAME_OBJ_TYPE);

                    if (idxObjId < 0 || idxObjType < 0) {
                        mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_column_not_found) + ": "
                                            + DataConstants.DB_TAB_OBJECT_COL_NAME_OBJ_ID + " or " + DataConstants.DB_TAB_OBJECT_COL_NAME_OBJ_TYPE, null);
                        return null;
                    }

                    int objId = cursorAllObjs.getInt(idxObjId);
                    String objType = cursorAllObjs.getString(idxObjType);

                    objModels.add(new ObjModel(objId, objType));
                }
            }
            else {
                mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_null_cursor) + ": " + DataConstants.DB_TAB_OBJECT_NAME,
                                        mContext.getResources().getString(R.string.errinfo_usrmsg_null_cursor));
                return null;
            }
        }
        catch (Exception e) {
            mErrorInfo.setInfo(e.getMessage(), null);
            return null;
        }

        return objModels;
    }

    public ArrayList<EventModel> getEventsByObject(int objId) {
        ArrayList<EventModel> eventModels = new ArrayList<>();

        try {
            Cursor cursorEvents = mDB.rawQuery("SELECT " + DataConstants.DB_TAB_EVENT_COL_NAME_EVN_ID + ", " + DataConstants.DB_TAB_EVENT_COL_NAME_EVN_TYPE
                                                    + " FROM " + DataConstants.DB_TAB_EVENT_NAME + " WHERE " + DataConstants.DB_TAB_EVENT_COL_NAME_EVN_ID + " IN "
                                                        + "(SELECT " + DataConstants.DB_TAB_EVENT_COL_NAME_EVN_ID + " FROM " + DataConstants.DB_TAB_ADVICE_NAME
                                                            + " WHERE " + DataConstants.DB_TAB_ADVICE_COL_NAME_OBJ_ID + " = ?)", new String[] {Integer.toString(objId)});

            if (cursorEvents != null) {
                while (cursorEvents.moveToNext()) {
                    int idxEvnId = cursorEvents.getColumnIndex(DataConstants.DB_TAB_EVENT_COL_NAME_EVN_ID);
                    int idxEvnType = cursorEvents.getColumnIndex(DataConstants.DB_TAB_EVENT_COL_NAME_EVN_TYPE);

                    if (idxEvnId < 0 || idxEvnType < 0) {
                        mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_column_not_found) + ": "
                                + DataConstants.DB_TAB_EVENT_COL_NAME_EVN_ID + " or " + DataConstants.DB_TAB_EVENT_COL_NAME_EVN_TYPE, null);
                        return null;
                    }

                    int evnId = cursorEvents.getInt(idxEvnId);
                    String evnType = cursorEvents.getString(idxEvnType);

                    eventModels.add(new EventModel(evnId, evnType, objId));
                }
            }
            else {
                mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_null_cursor) + ": " + DataConstants.DB_TAB_EVENT_NAME,
                        mContext.getResources().getString(R.string.errinfo_usrmsg_null_cursor));
                return null;
            }
        }
        catch (Exception e) {
            mErrorInfo.setInfo(e.getMessage(), null);
            return null;
        }

        return eventModels;
    }

    public AdviceModel getAdviceByObjectByEvent(int objId, int evnId) {
        try {
            Cursor cursorAdvices = mDB.rawQuery("SELECT " + DataConstants.DB_TAB_ADVICE_COL_NAME_ADV_ID + ", " + DataConstants.DB_TAB_ADVICE_COL_NAME_ADV_CONTENT
                                                    + " FROM " + DataConstants.DB_TAB_ADVICE_NAME
                                                    + " WHERE " + DataConstants.DB_TAB_ADVICE_COL_NAME_OBJ_ID + " = ? "
                                                        + "AND " + DataConstants.DB_TAB_ADVICE_COL_NAME_EVN_ID + " = ? LIMIT 1" , new String[] {Integer.toString(objId), Integer.toString(evnId)});

            if (cursorAdvices != null) {
                if (cursorAdvices.moveToFirst()) {
                    int idxAdvId = cursorAdvices.getColumnIndex(DataConstants.DB_TAB_ADVICE_COL_NAME_ADV_ID);
                    int idxAdvContent = cursorAdvices.getColumnIndex(DataConstants.DB_TAB_ADVICE_COL_NAME_ADV_CONTENT);

                    if (idxAdvId < 0 || idxAdvContent < 0) {
                        mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_column_not_found) + ": "
                                + DataConstants.DB_TAB_ADVICE_COL_NAME_ADV_ID + " or " + DataConstants.DB_TAB_ADVICE_COL_NAME_ADV_CONTENT, null);
                        return null;
                    }

                    int advId = cursorAdvices.getInt(idxAdvId);
                    String advContent = cursorAdvices.getString(idxAdvContent);

                    return new AdviceModel(advId, advContent);
                }
                else {
                    mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_null_scalar) + ": " + DataConstants.DB_TAB_ADVICE_NAME,
                            mContext.getResources().getString(R.string.errinfo_usrmsg_null_scalar));
                    return null;
                }
            }
            else {
                mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_null_cursor) + ": " + DataConstants.DB_TAB_ADVICE_NAME,
                        mContext.getResources().getString(R.string.errinfo_usrmsg_null_cursor));
                return null;
            }
        }
        catch (Exception e) {
            mErrorInfo.setInfo(e.getMessage(), null);
            return null;
        }
    }

    public ArrayList<ShopModel> getShopsByAdvice(int advId) {
        ArrayList<ShopModel> shopModels = new ArrayList<>();

        try {
            Cursor cursorShops = mDB.rawQuery("SELECT " + DataConstants.DB_TAB_SHOP_COL_NAME_SHP_ID + ", " + DataConstants.DB_TAB_SHOP_COL_NAME_SHP_NAME
                                                    + ", " + DataConstants.DB_TAB_SHOP_COL_NAME_SHP_ADDRESS + " FROM " + DataConstants.DB_TAB_SHOP_NAME
                                                + " WHERE " + DataConstants.DB_TAB_SHOP_COL_NAME_SHP_ID + " IN "
                                                + "(SELECT " + DataConstants.DB_TAB_ADVICE_SHOP_COL_NAME_SHP_ID + " FROM " + DataConstants.DB_TAB_ADVICE_SHOP_NAME
                                                + " WHERE " + DataConstants.DB_TAB_ADVICE_SHOP_COL_NAME_ADV_ID + " = ?)", new String[] {Integer.toString(advId)});

            if (cursorShops != null) {
                while (cursorShops.moveToNext()) {
                    int idxShpId = cursorShops.getColumnIndex(DataConstants.DB_TAB_SHOP_COL_NAME_SHP_ID);
                    int idxShpName = cursorShops.getColumnIndex(DataConstants.DB_TAB_SHOP_COL_NAME_SHP_NAME);
                    int idxShpAddress = cursorShops.getColumnIndex(DataConstants.DB_TAB_SHOP_COL_NAME_SHP_ADDRESS);

                    if (idxShpId < 0 || idxShpName < 0 || idxShpAddress < 0) {
                        mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_column_not_found) + ": "
                                + DataConstants.DB_TAB_SHOP_COL_NAME_SHP_ID + " or " + DataConstants.DB_TAB_SHOP_COL_NAME_SHP_NAME
                                + " or " + DataConstants.DB_TAB_SHOP_COL_NAME_SHP_ADDRESS, null);
                        return null;
                    }

                    int shpId = cursorShops.getInt(idxShpId);
                    String shpName = cursorShops.getString(idxShpName);
                    String shpAddress = cursorShops.getString(idxShpAddress);

                    shopModels.add(new ShopModel(shpId, shpName, shpAddress));
                }
            }
            else {
                mErrorInfo.setInfo(mContext.getResources().getString(R.string.errinfo_sysmsg_null_cursor) + ": " + DataConstants.DB_TAB_SHOP_NAME,
                        mContext.getResources().getString(R.string.errinfo_usrmsg_null_cursor));
                return null;
            }
        }
        catch (Exception e) {
            mErrorInfo.setInfo(e.getMessage(), null);
            return null;
        }

        return shopModels;
    }
}