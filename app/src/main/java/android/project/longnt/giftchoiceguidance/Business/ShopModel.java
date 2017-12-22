package android.project.longnt.giftchoiceguidance.Business;

/**
 * Created by LongNT on 22/12/17.
 */

public class ShopModel {
    private int shpId;
    private String shpName;
    private String shpAddress;

    public ShopModel(int shpId, String shpName, String shpAddress) {
        this.shpId = shpId;
        this.shpName = shpName;
        this.shpAddress = shpAddress;
    }

    public int getShpId() {
        return shpId;
    }

    public String getShpName() {
        return shpName;
    }

    public String getShpAddress() {
        return shpAddress;
    }
}
