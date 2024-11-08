import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private LocalDateTime orderDateTime;
    private double totalAmount;
    private double discount;
    private Customer customer;
    private List<Drink> drinks;
    private List<Integer> quantities;
    private String status; // สถานะคำสั่งซื้อ เช่น "รออนุมัติ", "รับคำสั่งซื้อ", "กำลังจัดเตรียม", "เสร็จสิ้น"
    private LocalDateTime estimatedDeliveryTime;
    private String deliveryAddress = "";
    private Promotion promotionApplied; // โปรโมชั่นที่ใช้ในคำสั่งซื้อ (หากมี)
    

    public Order(int orderId, Customer customer, double totalAmount, List<Drink> drinks, List<Integer> quantities,
                 double discount) {
        this.orderId = orderId;
        this.orderDateTime = LocalDateTime.now();
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.drinks = (drinks != null) ? drinks : new ArrayList<>();
        this.quantities = (quantities != null) ? quantities : new ArrayList<>();
        
        // กำหนดสถานะเริ่มต้น
        this.status = "รออนุมัติ";
    }

    // Getters
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public double getTotalAmount() {
        return totalAmount - discount;
    }

    public double getOriginalTotalAmount() {
        return totalAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    // ฟังก์ชันอัพเดตสถานะคำสั่งซื้อ (ใช้ได้กับผู้จัดการ)
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("สถานะคำสั่งซื้อเปลี่ยนเป็น: " + newStatus);
    }

    public void applyPromotion(Promotion promotion) {
        if (promotion != null && !promotion.isPromotionExpired()) {
            this.promotionApplied = promotion;
    
            // ตรวจสอบประเภทโปรโมชั่นและคำนวณส่วนลด
            String promotionType = promotion.getSelectPromotion();
            
            if ("Buy 2, get 50% off".equals(promotionType)) {
                this.discount = totalAmount * 0.5; // ลดราคา 50%
            } else if ("Buy 3, get 30% off".equals(promotionType)) {
                this.discount = totalAmount * 0.3; // ลดราคา 30%
            } else {
                System.out.println("Unknown promotion type.");
            }
            
            System.out.println("Promotion applied: " + promotion.getPromotionName());
        } else {
            System.out.println("Promotion is either invalid or expired.");
        }
    }
    

    // Method สำหรับคำนวณราคาหลังจากหักส่วนลด
    public double getFinalAmount() {
        return totalAmount - discount;
    }

    

    // ฟังก์ชันแสดงรายละเอียดคำสั่งซื้อ
    public void displayOrderDetails() {
        System.out.println("=================================");
        System.out.println("Order ID: " + orderId);
        System.out.println("วันและเวลาที่สั่ง: " + orderDateTime);
        System.out.println("ลูกค้า: " + customer.getName());
        System.out.println("สถานะ: " + status);
        System.out.println("ที่อยู่จัดส่ง: " + (this.deliveryAddress.isEmpty() ? "ไม่ระบุ" : this.deliveryAddress));
        System.out.println("เวลาจัดส่งประมาณ: " + (estimatedDeliveryTime != null ? estimatedDeliveryTime : "ไม่ระบุ"));
        System.out.println("ยอดรวมก่อนหักส่วนลด: ฿" + getOriginalTotalAmount());
        
        if (promotionApplied != null) {
            System.out.println("โปรโมชั่นที่ใช้: " + promotionApplied.getPromotionName());
            System.out.println("ส่วนลดจากโปรโมชั่น: ฿" + discount);
        } else {
            System.out.println("ส่วนลด: ฿" + discount);
        }
        
        System.out.println("ยอดรวมหลังหักส่วนลด: ฿" + getTotalAmount());
        
        System.out.println("\n== รายการเครื่องดื่ม ==");
        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);
            int quantity = quantities.get(i);
            System.out.printf("%d. %s จำนวน: %d ราคา: ฿%.2f\n", i + 1, drink.getName(), quantity, drink.getPrice());
        }
        System.out.println("=================================");
    }
}
