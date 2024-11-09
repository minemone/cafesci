import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Manager extends Person {
    private String managerID;
    private List<Drink> drinks;
    private List<Promotion> activePromotions;

    // Constructor
    public Manager(String personID, String name, String phoneNumber, Role role, String managerID, List<Drink> drinks) {
        super(personID, name, role);
        this.managerID = managerID;
        this.drinks = (drinks != null) ? drinks : new ArrayList<>();
        this.activePromotions = new ArrayList<>();
    }

    // Getters and Setters
    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    // Method for retrieving top 3 selling drinks
    public List<Drink> getTopSellingDrinks() {
        return drinks.stream()
                     .sorted((d1, d2) -> Integer.compare(d2.getSalesCount(), d1.getSalesCount()))
                     .limit(3)
                     .collect(Collectors.toList());
    }

    // Method for retrieving bottom 3 selling drinks
    public List<Drink> getLowestSellingDrinks() {
        return drinks.stream()
                     .sorted((d1, d2) -> Integer.compare(d1.getSalesCount(), d2.getSalesCount()))
                     .limit(3)
                     .collect(Collectors.toList());
    }

    // Method for creating a new promotion
    

    // Method to get all active promotions
    public List<Promotion> getActivePromotions() {
        return activePromotions.stream()
                               .filter(Promotion::isActive)
                               .collect(Collectors.toList());
    }

    // Method to delete a promotion
    public void deletePromotion(Promotion promotion) {
        if (promotion != null && activePromotions.remove(promotion)) {
            System.out.println("Promotion removed: " + promotion.getPromotionType());
        } else {
            System.out.println("Promotion not found or already removed.");
        }
    }

    // Method to clean expired promotions
    public void createPromotion(List<Drink> topSelling, List<Drink> lowSelling, String promotionType,
                             PreparationType preparationType, int durationDays) {
    // ตรวจสอบว่ามีเครื่องดื่มเพียงพอในการสร้างโปรโมชั่น
    if (topSelling.isEmpty() || lowSelling.isEmpty()) {
        System.out.println("ไม่สามารถสร้างโปรโมชั่นได้ เนื่องจากไม่มีเครื่องดื่มที่เพียงพอ");
        return;
    }
    
    // เพิ่มโปรโมชั่นลงในรายการโปรโมชั่นที่ใช้งานอย

}
}
