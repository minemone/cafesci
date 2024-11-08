import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Promotion {
    private String promotionName;
    private String promotionType;
    private PreparationType preparationType;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructor for promotions with a duration
    public Promotion(Drink topSelling, Drink lowSelling, String promotionType, PreparationType preparationType,
            int durationDays) {
        this.promotionName = "Promotion for " + topSelling.getName() + " and " + lowSelling.getName();
        this.promotionType = promotionType;
        this.preparationType = preparationType;
        this.startDate = LocalDate.now();
        this.endDate = calculateEndDate(durationDays);
    }

    

    // Method to calculate the promotion end date
    private LocalDate calculateEndDate(int days) {
        return LocalDate.now().plusDays(days);
    }

    // Check if the promotion is expired
    public boolean isPromotionExpired() {
        return LocalDate.now().isAfter(endDate);
    }

    // Display promotion details
    public void displayPromotionDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Promotion Name: " + promotionName);
        System.out.println("Promotion Type: " + promotionType);
        if (preparationType != null) {
            System.out.println("Drink Type: " + preparationType.getPrepName());
        }
        System.out.println("Start Date: " + startDate.format(formatter));
        System.out.println("End Date: " + endDate.format(formatter));
        System.out.println("Promotion Status: " + (isPromotionExpired() ? "Expired" : "Active"));
    }

    // Getters
    public String getPromotionName() {
        return promotionName;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public PreparationType getPreparationType() {
        return preparationType;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return !isPromotionExpired();
    }

    public String getSelectPromotion() {
        return promotionType; // เปลี่ยนให้คืนค่า promotionType
    }

    public boolean isPromotionActive() {
        return !isPromotionExpired(); // ถ้าโปรโมชั่นยังไม่หมดอายุคืนค่า true
    }
    

}
