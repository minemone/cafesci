public class Drink {
    private int drinkID;
    private String name;
    private float price;
    private String sweetnessLevel;
    private Topping topping;
    private PreparationType preparationType;
    private DrinkCategory category;
    private int sales;
    private Promotion promotion; // เพิ่ม attribute สำหรับโปรโมชั่น

    // Constructor
    public Drink(int drinkID, String name, float price, DrinkCategory category) {
        this.drinkID = drinkID;
        this.name = name;
        this.price = price;
        this.sweetnessLevel = "หวานปกติ";
        this.topping = null;
        this.preparationType = new PreparationType(1, "ร้อน", 0); // ไม่มีประเภทเครื่องดื่มเริ่มต้น
        this.sales = 0;
        this.category = category;
        this.promotion = null; // เริ่มต้นไม่มีโปรโมชั่น
    }

    public int getDrinkID() {
        return drinkID;
    }

    public String getName() {
        return name;
    }

    // ดึงราคาพื้นฐานของเครื่องดื่ม
    public float getPrice() {
        return price;
    }

    // Method กำหนดระดับความหวาน
    public void setSweetnessLevel(String sweetnessLevel) {
        this.sweetnessLevel = sweetnessLevel;
    }

    public String getSweetness() {
        return sweetnessLevel;
    }

    // Method กำหนดท็อปปิ้ง
    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public Topping getTopping() {
        return topping;
    }

    // Method กำหนดประเภทเครื่องดื่ม
    public void setPreparationType(PreparationType preparationType) {
        this.preparationType = preparationType;
    }

    public PreparationType getPreparationType() {
        return preparationType;
    }

    public DrinkCategory getCategory() {
        return category;
    }

    public int getSales() {
        return sales;
    }

    // เพิ่มเมธอดสำหรับอัปเดตยอดขาย
    public void addSales(int quantity) {
        this.sales += quantity;
    }

    // เพิ่มเมธอดสำหรับกำหนดและดึงโปรโมชั่น
    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }
}
