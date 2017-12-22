package android.project.longnt.giftchoiceguidance.Business;

/**
 * Created by LongNT on 22/12/17.
 */

public class ObjModel {
    private int objId;
    private String objType;

    public ObjModel(int objId, String objType) {
        this.objId = objId;
        this.objType = objType;
    }

    public int getObjId() {
        return objId;
    }

    public String getObjType() {
        return objType;
    }
}