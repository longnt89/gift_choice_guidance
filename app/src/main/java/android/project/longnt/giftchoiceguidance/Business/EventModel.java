package android.project.longnt.giftchoiceguidance.Business;

/**
 * Created by LongNT on 22/12/17.
 */

public class EventModel {
    private int evnId;
    private String evnType;
    private int objId;

    public EventModel(int evnId, String evnType, int objId) {
        this.evnId = evnId;
        this.evnType = evnType;
        this.objId = objId;
    }

    public int getEvnId() {
        return evnId;
    }

    public String getEvnType() {
        return evnType;
    }

    public int getObjId() {
        return objId;
    }
}
