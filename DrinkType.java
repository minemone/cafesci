
public class DrinkType {
    private int drinktypeID;
    private String drinktypeName; // เช่น "ร้อน", "เย็น", "ปั่น"
    private float drinktypeprice;

    // Constructor
    public DrinkType(int drinktypeID, String drinktypeName, float drinktypeprice) {
        this.drinktypeID = drinktypeID;
        this.drinktypeName = drinktypeName;
        this.drinktypeprice = drinktypeprice;

    }

    public int getdrinktypeID() {
        return drinktypeID;
    }

    public String getdrinktypeName() {
        return drinktypeName;
    }

    public float getdrinktypeprice() { // ตรวจสอบให้แน่ใจว่าเมธอดนี้สะกดถูกต้อง
        return drinktypeprice;
    }
}
