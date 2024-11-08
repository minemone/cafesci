import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Manager extends Person {
    private String managerID;
    private List<Drink> drinks;
    private List<Promotion> activePromotions;

    // Constructor
    public Manager(String personID, String name, String phoneNumber, Role role, String managerID, List<Drink> drinks) {
        super(personID, name, role); // เรียก Constructor ของ Person และกำหนด Role
        this.managerID = managerID;
        this.drinks = drinks;
        this.activePromotions = new ArrayList<>();
    }

    // Getters and Setters
    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    // Method ดึงเครื่องดื่มยอดขายสูงสุด 3 อันดับแรก
    public List<Drink> getTopSellingDrinks() {
        return drinks.stream()
                     .sorted((d1, d2) -> Integer.compare(d2.getSalesCount(), d1.getSalesCount()))
                     .limit(3)
                     .collect(Collectors.toList());
    }

    // Method ดึงเครื่องดื่มยอดขายน้อยสุด 3 อันดับแรก
    public List<Drink> getLowestSellingDrinks() {
        return drinks.stream()
                     .sorted((d1, d2) -> Integer.compare(d1.getSalesCount(), d2.getSalesCount()))
                     .limit(3)
                     .collect(Collectors.toList());
    }

    // Method สร้างโปรโมชั่นใหม่
    public void createPromotion(List<Drink> topSelling, List<Drink> lowSelling, String promotionType, int duration) {
        Promotion promotion = new Promotion(topSelling, lowSelling, promotionType, duration);
        activePromotions.add(promotion); // เพิ่มโปรโมชั่นใหม่ไปยังรายการโปรโมชั่นที่ใช้งานอยู่
        System.out.println("New promotion created: " + promotionType + " for duration: " + duration + " days");
    }

    // Method แสดงโปรโมชั่นที่ใช้งานอยู่ทั้งหมด (ที่ยังไม่หมดอายุ)
    public List<Promotion> getActivePromotions() {
        return activePromotions.stream()
                               .filter(Promotion::isActive) // กรองเฉพาะโปรโมชั่นที่ยังไม่หมดอายุ
                               .collect(Collectors.toList());
    }

    // Method ลบโปรโมชั่น
    public void deletePromotion(Promotion promotion) {
        if (activePromotions.remove(promotion)) {
            System.out.println("Promotion removed: " + promotion.getPromotionType());
        } else {
            System.out.println("Promotion not found or already removed.");
        }
    }

    // Method สำหรับตรวจสอบและลบโปรโมชั่นที่หมดอายุ
    public void cleanExpiredPromotions() {
        activePromotions.removeIf(promotion -> !promotion.isActive());
        System.out.println("Expired promotions have been cleaned.");
    }
}
