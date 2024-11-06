public class Drink {
    private int drinkID;
    private String name;
    private float Price;
    private String sweetnessLevel;
    private Topping topping;
    private PreparationType preparationType; 
    private DrinkCategory category;
    private int sales;

    // Constructor
    public Drink(int drinkID, String name, float Price, DrinkCategory category) {
        this.drinkID = drinkID;
        this.name = name;
        this.Price = Price;
        this.sweetnessLevel = "หวานปกติ"; 
        this.topping = null; 
        this.preparationType = new PreparationType(1, "ร้อน", 0); // ไม่มีประเภทเครื่องดื่มเริ่มต้น
        this.sales = 0; 
        this.category = category;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public String getName() {
        return name;
    }

    // ดึงราคาพื้นฐานของเครื่องดื่ม
    public float getPrice() {
        return Price;
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

   
}
