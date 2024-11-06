import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private List<Order> orderHistory;
    private int points; // เพิ่มฟิลด์สำหรับเก็บคะแนนพอยต์

    // Constructor
    public Customer(String personID, String name, String email, Role role) {
        super(personID, name, role);
        this.orderHistory = new ArrayList<>();
        this.points = 0; // เริ่มต้นด้วยพอยต์ 0
    }

    // Method to add an order to the history
    public void addOrder(Order order) {
        orderHistory.add(order);
        int earnedPoints = (int) (order.getTotalAmount() * 0.10); // ได้พอยต์ 10% ของยอดสั่งซื้อ
        addPoints(earnedPoints); // เพิ่มพอยต์จากยอดสั่งซื้อ
        System.out.printf("ได้รับ %d พอยต์จากการสั่งซื้อนี้\n", earnedPoints);
    }

    // Method to get the order history
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    // เพิ่มเมธอดสำหรับจัดการพอยต์
    public void addPoints(int points) {
        this.points += points;
    }

    public void reducePoints(double pointsToUse) {
        if (pointsToUse <= this.points) {
            this.points -= pointsToUse;
        } else {
            System.out.println("พอยต์ไม่เพียงพอ");
        }
    }

    public int getPoints() {
        return points;
    }
}
