import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Promotion {
    private int promotionId;
    private String promotionName;
    private List<Drink> topSellingDrinks;
    private List<Drink> lowSellingDrinks;
    private String promotionType;
    private PreparationType preparationType;
    private String selectPromotion;
    private LocalDate promotionEndDate;
    private int sales;
    private boolean isActive = true;
    private String promotionDescription;
    private LocalDate startDate;
    private LocalDate endDate;

    // Constructor for top-selling and low-selling drinks with duration
    public Promotion(String promotionDescription, LocalDate startDate, LocalDate endDate) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.topSellingDrinks = topSellingDrinks;
        this.lowSellingDrinks = lowSellingDrinks;
        this.promotionDescription = promotionDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
    }

    // Constructor for setting promotion based on a specific drink and duration
    public Promotion(int promotionId, String promotionName, String selectPromotion, Drink drink, int promotionPeriodDays) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.selectPromotion = selectPromotion;
        this.preparationType = drink.getPreparationType();
        this.sales = drink.getSalesCount();
        this.promotionEndDate = calculateEndDate(promotionPeriodDays);
        this.isActive = true;
    }

    public Promotion(List<Drink> topSelling, List<Drink> lowSelling, String promotionType2, int duration) {
        //TODO Auto-generated constructor stub
    }

    // Method to calculate the promotion end date based on a given period (days)
    private LocalDate calculateEndDate(int days) {
        return LocalDate.now().plusDays(days);
    }

    // Update promotion details and check values
    public void updatePromotion(String newPromotionName, String newSelectPromotion, Drink drink, int newPromotionPeriodDays) {
        if (newPromotionName != null && !newPromotionName.isEmpty()) {
            this.promotionName = newPromotionName;
        }
        if (newSelectPromotion != null && !newSelectPromotion.isEmpty()) {
            this.selectPromotion = newSelectPromotion;
        }
        if (drink != null) {
            this.preparationType = drink.getPreparationType();
            this.sales = drink.getSalesCount();
        }
        if (newPromotionPeriodDays > 0) {
            this.promotionEndDate = calculateEndDate(newPromotionPeriodDays);
        }
    }

    // Check if the promotion is expired
    public boolean isPromotionExpired() {
        return LocalDate.now().isAfter(promotionEndDate);
    }

    // Getters and Setters
    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public PreparationType getPreparationType() {
        return preparationType;
    }

    public void setPreparationType(PreparationType preparationType) {
        this.preparationType = preparationType;
    }

    public String getSelectPromotion() {
        return selectPromotion;
    }

    public void setSelectPromotion(String selectPromotion) {
        this.selectPromotion = selectPromotion;
    }

    public LocalDate getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionEndDate(int days) {
        this.promotionEndDate = calculateEndDate(days);
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public boolean isActive() {
        return isActive && !isPromotionExpired();
    }

    // Display promotion details
    public void displayPromotionDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Promotion Name: " + promotionName);
        if (preparationType != null) {
            System.out.println("Drink Type: " + preparationType.getPrepName());
        }
        System.out.println("Selected Promotion: " + selectPromotion);
        System.out.println("Sales: " + sales);
        System.out.println("End Date: " + promotionEndDate.format(formatter));
        System.out.println("Promotion Status: " + (isPromotionExpired() ? "Expired" : "Active"));
    }
}
