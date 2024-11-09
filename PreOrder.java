import java.time.LocalDateTime;
import java.util.List;

public class PreOrder {
    private int orderId;
    private List<Drink> drinks;
    private LocalDateTime pickupTime; // เวลาที่ต้องการรับเครื่องดื่ม

    public PreOrder(int orderId, List<Drink> drinks, LocalDateTime pickupTime) {
        this.orderId = orderId;
        this.drinks = drinks;
        this.pickupTime = pickupTime;
    }

    public void displayPreOrderDetails() {
        System.out.println("รายละเอียดการสั่งแบบล่วงหน้า:");
        System.out.println("หมายเลขออร์เดอร์: " + orderId);
        System.out.println("เวลารับสินค้า: " + pickupTime);
        System.out.println("รายการเครื่องดื่ม:");
        for (Drink drink : drinks) {
            System.out.println("- " + drink.getName());
        }
    }
}
