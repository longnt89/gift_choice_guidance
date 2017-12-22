package android.project.longnt.giftchoiceguidance.Business;

import java.util.ArrayList;

/**
 * Created by LongNT on 22/12/17.
 */

public class AdviceModel {
    private int advId;
    private String advContent;
    private ArrayList<ShopModel> shpModels;

    public AdviceModel(int advId, String advContent) {
        this.advId = advId;
        this.advContent = advContent;
        this.shpModels = new ArrayList<>();
    }

    public int getAdvId() {
        return advId;
    }

    public String getAdvContent() {
        return advContent;
    }

    public void setShpModels(ArrayList<ShopModel> shpModels) {
        this.shpModels = shpModels;
    }

    public ArrayList<ShopModel> getShpModels() {
        return shpModels;
    }
}
