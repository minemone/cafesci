public class Drink {
    private int drinkID;
    private String name;
    private float price;
    private String sweetnessLevel;
    private Topping topping;
    private PreparationType preparationType;
    private DrinkCategory category;
    private int salesCount; // จำนวนครั้งที่เครื่องดื่มนี้ถูกสั่ง
    private Promotion promotion; // เพิ่ม attribute สำหรับโปรโมชั่น

    // Constructor
    public Drink(int drinkID, String name, float price, DrinkCategory category) {
        this.drinkID = drinkID;
        this.name = name;
        this.price = price;
        this.sweetnessLevel = "หวานปกติ";
        this.topping = null;
        this.preparationType = new PreparationType(1, "ร้อน", 0); // ไม่มีประเภทเครื่องดื่มเริ่มต้น
        this.salesCount = 0; // เปลี่ยนจาก `sales` เป็น `salesCount`
        this.category = category;
        this.promotion = null; // เริ่มต้นไม่มีโปรโมชั่น
    }

    // เมธอดสำหรับตั้งยอดขาย
    public void setSalesCount(int salesCount) {
        if (salesCount >= 0) {
            this.salesCount = salesCount;
        } else {
            System.out.println("ยอดขายต้องเป็นจำนวนที่มากกว่าหรือเท่ากับ 0");
        }
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

    public String getSweetnessLevel() {
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

    // เพิ่มเมธอดสำหรับอัปเดตยอดขาย
    public void addSales(int quantity) {
        this.salesCount += quantity;
    }

    // เพิ่มเมธอดสำหรับกำหนดและดึงโปรโมชั่น
    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void incrementSales(int quantity) {
        salesCount += quantity; // เพิ่มยอดขายตามจำนวนที่สั่ง
    }
}
