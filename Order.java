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
}
