
public class PreparationType {
    private int prepID;
    private String prepName; // เช่น "ร้อน", "เย็น", "ปั่น"
    private float prepprice;

    // Constructor
    public PreparationType(int prepID, String prepName, float prepprice) {
        this.prepID = prepID;
        this.prepName = prepName;
        this.prepprice = prepprice;

    }

    public int getPrepID() {
        return prepID;
    }

    public String getPrepName() {
        return prepName;
    }

    public float getPrepprice() { // ตรวจสอบให้แน่ใจว่าเมธอดนี้สะกดถูกต้อง
        return prepprice;
    }
}
