package android.project.longnt.giftchoiceguidance.Status;

import android.content.Context;
import android.project.longnt.giftchoiceguidance.R;

/**
 * Created by LongNT on 22/12/17.
 */

public class ErrorInfo {
    private String sysErrMsg;
    private String usrErrMsg;
    private Context mContext;

    public ErrorInfo(Context context){
        mContext = context;
        sysErrMsg = context.getResources().getString(R.string.errinfo_default_sys_msg);
        usrErrMsg = context.getResources().getString(R.string.errinfo_default_usr_msg);
    }

    public void setInfo(String sysErrMsg, String usrErrMsg) {
        if (sysErrMsg == null) {
            this.sysErrMsg = mContext.getResources().getString(R.string.errinfo_default_sys_msg);
        }
        else {
            this.sysErrMsg = sysErrMsg;
        }

        if (usrErrMsg == null) {
            this.usrErrMsg = mContext.getResources().getString(R.string.errinfo_default_usr_msg);
        }
        else {
            this.usrErrMsg = usrErrMsg;
        }
    }

    public String getSysErrMsg() {
        return sysErrMsg;
    }

    public String getUsrErrMsg() {
        return usrErrMsg;
    }
}
