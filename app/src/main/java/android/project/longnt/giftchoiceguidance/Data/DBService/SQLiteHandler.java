package android.project.longnt.giftchoiceguidance.Data.DBService;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.project.longnt.giftchoiceguidance.Constant.DataConstants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by longnt on 12/20/17.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private SQLiteDatabase mDB;

    private final Context mContext;

    public SQLiteHandler(Context context) {
        super(context, DataConstants.DB_NAME, null, 1);
        this.mContext = context;
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
                throw new Error("Error copying database");
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}