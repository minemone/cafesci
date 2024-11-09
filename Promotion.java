import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Promotion {
    private String promotionName;
    private String promotionType;
    private PreparationType preparationType;
    private LocalDate startDate;
    private LocalDate endDate;
    private float discountPercentage;
    private List<Drink> applicableDrinks;

    // Constructor for promotions with a duration and applicable drinks
    public Promotion(Drink topSelling, Drink lowSelling, String promotionType, String preparationType,
            int durationDays) {
        this.promotionName = "Promotion for " + topSelling.getName() + " and " + lowSelling.getName();
        this.promotionType = promotionType;
        this.preparationType = PreparationType.valueOf(preparationType); // สมมติว่า preparationType เป็น enum
        this.startDate = LocalDate.now();
        this.endDate = calculateEndDate(durationDays);

        // กำหนดเครื่องดื่มที่ใช้ได้กับโปรโมชัน
        this.applicableDrinks = new ArrayList<>();
        this.applicableDrinks.add(topSelling);
        this.applicableDrinks.add(lowSelling);
    }

    public enum PreparationType {
        ร้อน(0), เย็น(5), ปั่น(10); // กำหนดราคาพิเศษที่เพิ่มขึ้นตามประเภท

        private double prepPrice;

        // Constructor สำหรับเก็บค่าราคาเพิ่ม
        PreparationType(double prepPrice) {
            this.prepPrice = prepPrice;
        }

        public double getPrepPrice() {
            return prepPrice;
        }

        public String getPrepName() {
            return name(); // คืนชื่อของประเภทการเตรียม
        }
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

    // ส่งคืนรายการเครื่องดื่มที่สามารถใช้โปรโมชันนี้ได้
    public List<Drink> getApplicableDrinks() {
        return applicableDrinks;
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

    public float getDiscountPercentage() {
        return discountPercentage; // คืนค่าดังกล่าวเป็นเปอร์เซ็นต์ส่วนลด
    }
}
