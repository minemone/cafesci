
public class Sweetness {
    private int SweetnessID;
    private String SweetNessName;

    // Constructor
    public Sweetness(int SweetnessID, String SweetNessName) {
        this.SweetnessID = SweetnessID;
        this.SweetNessName = SweetNessName;
    }

    public int getSweetnessID() {
        return SweetnessID;
    }

    public void setSweetnessID(int SweetnessID) {
        this.SweetnessID = SweetnessID;
    }

    public String getSweetnessName() {
        return SweetNessName;
    }
    public void setSweetnessName(String SweetNessName) {
        this.SweetNessName = SweetNessName;
    }

}
