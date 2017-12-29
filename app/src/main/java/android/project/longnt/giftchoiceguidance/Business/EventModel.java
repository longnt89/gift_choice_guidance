package android.project.longnt.giftchoiceguidance.Business;

/**
 * Created by LongNT on 22/12/17.
 */

public class EventModel {
    private int evnId;
    private String evnType;

    public EventModel(int evnId, String evnType) {
        this.evnId = evnId;
        this.evnType = evnType;
    }

    public int getEvnId() {
        return evnId;
    }

    public String getEvnType() {
        return evnType;
    }
}
