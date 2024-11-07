import java.util.Calendar;
import java.util.Date;

public class Promotion {
    private int promotionId;               // รหัสโปรโมชัน
    private String promotionName;          // ชื่อโปรโมชัน
    private PreparationType preparationType;              // ประเภทของเครื่องดื่มจาก Drink
    private String selectPromotion;        // เลือกโปรโมชัน
    private Date promotionPeriod;          // วันสิ้นสุดโปรโมชัน
    private int sales;                     // ยอดขายจาก Drink

    // คอนสตรัคเตอร์ที่ใช้ Drink และกำหนดระยะเวลาเป็นจำนวนวัน
    public Promotion(int promotionId, String promotionName, String selectPromotion, Drink drink, int promotionPeriodDays) {
        this.promotionId = promotionId; // กำหนดรหัสโปรโมชัน
        this.promotionName = promotionName; // ชื่อโปรโมชัน
        this.selectPromotion = selectPromotion; // โปรโมชันที่เลือก
        this.preparationType = drink.getPreparationType(); // ประเภทการเตรียมจาก Drink
        this.sales = drink.getSales(); // ยอดขายของเครื่องดื่ม
        this.promotionPeriod = calculateEndDate(promotionPeriodDays); // คำนวณวันสิ้นสุดโปรโมชัน
    }

    // Method คำนวณวันสิ้นสุดโปรโมชันจากจำนวนวัน
    private Date calculateEndDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    // Method แก้ไขโปรโมชัน
    public void updatePromotion(String newPromotionName, String newSelectPromotion, Drink drink, int newPromotionPeriodDays) {
        this.promotionName = newPromotionName;
        this.selectPromotion = newSelectPromotion;
        this.preparationType = drink.getPreparationType();
        this.sales = drink.getSales();
        this.promotionPeriod = calculateEndDate(newPromotionPeriodDays);
    }

    // Getter และ Setter สำหรับฟิลด์ต่าง ๆ
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

    public Date getPromotionPeriod() {
        return promotionPeriod;
    }

    public void setPromotionPeriodDays(int days) {
        this.promotionPeriod = calculateEndDate(days);
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
