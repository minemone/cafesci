import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private List<Order> orderHistory;
    private int points;
    private Order currentOrder;
    private List<Promotion> availablePromotions;

    // Constructor
    public Customer(String personID, String name, String email, Role role) {
        super(personID, name, role);
        this.orderHistory = new ArrayList<>();
        this.points = 0;
        this.availablePromotions = new ArrayList<>(); // เริ่มต้นด้วยลิสต์ว่างสำหรับโปรโมชั่นที่ลูกค้าใช้ได้
    }

    // ฟังก์ชันสำหรับการสั่งซื้อ
    public void placeOrder(List<Drink> selectedDrinks) {
        currentOrder = new Order(orderHistory.size() + 1, this, calculateTotal(selectedDrinks), selectedDrinks,
                getQuantities(selectedDrinks), 0.0);

        // แสดงโปรโมชั่นที่มีอยู่ให้ลูกค้าเลือก
        System.out.println("\n== โปรโมชั่นที่ลูกค้าเลือกใช้ได้ ==");
        for (Promotion promotion : availablePromotions) {
            if (promotion.isActive()) {
                System.out.println("Available Promotion: " + promotion.getPromotionName());
            }
        }

        // ลูกค้าสามารถเลือกใช้โปรโมชั่น (ถ้ามี)
        if (!availablePromotions.isEmpty()) {
            Promotion selectedPromotion = availablePromotions.get(0); // ตัวอย่างการเลือกโปรโมชั่นที่ 1
                                                                      // (อาจเพิ่มการเลือกจริงในระบบ)
            currentOrder.applyPromotion(selectedPromotion);
        }

        addOrder(currentOrder);
        System.out.println("Order placed successfully.");
    }

    // เพิ่มคำสั่งซื้อและคำนวณพอยต์
    public void addOrder(Order order) {
        orderHistory.add(order);
        int earnedPoints = (int) (order.getTotalAmount() * 0.10); // ได้พอยต์ 10% ของยอดสั่งซื้อ
        addPoints(earnedPoints);
        System.out.printf("ได้รับ %d พอยต์จากการสั่งซื้อนี้\n", earnedPoints);
    }

    // คำนวณยอดรวมจากเครื่องดื่มที่ลูกค้าเลือก
    private double calculateTotal(List<Drink> selectedDrinks) {
        return selectedDrinks.stream().mapToDouble(Drink::getPrice).sum();
    }

    // สร้างจำนวนของเครื่องดื่มที่ลูกค้าเลือกไว้
    private List<Integer> getQuantities(List<Drink> drinks) {
        List<Integer> quantities = new ArrayList<>();
        for (Drink drink : drinks) {
            quantities.add(1); // สมมุติให้ลูกค้าเลือกเครื่องดื่มแต่ละแก้ว 1 แก้วเป็นค่าเริ่มต้น
        }
        return quantities;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void reducePoints(double pointsToUse) {
        if (pointsToUse <= this.points) {
            this.points -= pointsToUse;
            System.out.printf("ใช้ %d พอยต์\n", pointsToUse);
        } else {
            System.out.println("พอยต์ไม่เพียงพอ");
        }
    }

    public int getPoints() {
        return points;
    }

    // เพิ่มโปรโมชั่นในลิสต์โปรโมชั่นที่ใช้ได้สำหรับลูกค้า
    public void addAvailablePromotion(Promotion promotion) {
        if (promotion != null && promotion.isActive()) {
            availablePromotions.add(promotion);
        }
    }

    public List<Promotion> getAvailablePromotions() {
        return availablePromotions;
    }
}
