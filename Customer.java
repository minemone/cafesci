import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private List<Order> orderHistory;
    private int points; 

    // Constructor
    public Customer(String personID, String name, String email, Role role) {
        super(personID, name, role);
        this.orderHistory = new ArrayList<>();
        this.points = 0; 
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
        int earnedPoints = (int) (order.getTotalAmount() * 0.10); // ได้พอยต์ 10% ของยอดสั่งซื้อ
        addPoints(earnedPoints); 
        System.out.printf("ได้รับ %d พอยต์จากการสั่งซื้อนี้\n", earnedPoints);
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
        } else {
            System.out.println("พอยต์ไม่เพียงพอ");
        }
    }

    public int getPoints() {
        return points;
    }
}
