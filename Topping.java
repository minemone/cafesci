
public class Topping {
    private int ToppingID;
    private String ToppingName;

    // Constructor
    public Topping(int toppingID, String toppingName) {
        this.ToppingID = toppingID;
        this.ToppingName = toppingName;
    }

    // Method ดึงชื่อท็อปปิ้ง
    public String getToppingName() {
        return ToppingName;
    }

    // Method ตั้งค่าท็อปปิ้ง
    public void setToppingName(String toppingName) {
        this.ToppingName = toppingName;
    }

    public int getToppingID() {
        return ToppingID;
    }

    public void setToppingID(int toppingID) {
        this.ToppingID = toppingID;
    }

}
