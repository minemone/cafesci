import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private LocalDateTime orderDateTime;
    private double totalAmount;
    private double discount; // ฟิลด์ส่วนลด
    private Customer customer;
    private List<Drink> drinks;
    private List<Integer> quantities;
    private String status; // สถานะคำสั่งซื้อ เช่น "กำลังดำเนินการ", "จัดส่งแล้ว", "สำเร็จ"
    private String deliveryAddress; // ที่อยู่สำหรับการจัดส่ง (ถ้ามี)
    private LocalDateTime estimatedDeliveryTime; // เวลาประมาณการจัดส่ง

    public Order(int orderId, Customer customer, double totalAmount, List<Drink> drinks, List<Integer> quantities,
                 double discount) {
        this.orderId = orderId;
        this.orderDateTime = LocalDateTime.now();
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.discount = discount;
        
        // ถ้า drinks หรือ quantities เป็น null ให้ตั้งค่าเป็น empty list
        this.drinks = (drinks != null) ? drinks : new ArrayList<>();
        this.quantities = (quantities != null) ? quantities : new ArrayList<>();
        
        // กำหนดค่าเริ่มต้นของสถานะเป็น "กำลังดำเนินการ"
        this.status = "กำลังดำเนินการ";
        this.deliveryAddress = "";
    }

    // Getters
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public double getTotalAmount() {
        return totalAmount - discount; // คำนวณราคาหลังหักส่วนลด
    }

    public double getOriginalTotalAmount() {
        return totalAmount; // ยอดรวมก่อนหักส่วนลด
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

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    // ฟังก์ชันสำหรับอัพเดตสถานะคำสั่งซื้อ
    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("สถานะคำสั่งซื้อถูกเปลี่ยนเป็น: " + newStatus);
    }

    // ฟังก์ชันแสดงรายละเอียดคำสั่งซื้อ
    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("วันและเวลาที่สั่ง: " + orderDateTime);
        System.out.println("ลูกค้า: " + customer.getName());
        System.out.println("สถานะ: " + status);
        System.out.println("ที่อยู่จัดส่ง: " + (deliveryAddress.isEmpty() ? "ไม่ระบุ" : deliveryAddress));
        System.out.println("เวลาจัดส่งประมาณ: " + (estimatedDeliveryTime != null ? estimatedDeliveryTime : "ไม่ระบุ"));
        System.out.println("ยอดรวมก่อนหักส่วนลด: ฿" + getOriginalTotalAmount());
        System.out.println("ส่วนลด: ฿" + discount);
        System.out.println("ยอดรวมหลังหักส่วนลด: ฿" + getTotalAmount());

        // แสดงรายการเครื่องดื่ม
        System.out.println("\n== รายการเครื่องดื่ม ==");
        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);
            int quantity = quantities.get(i);
            System.out.println(drink.getName() + " จำนวน: " + quantity);
        }
    }
}
