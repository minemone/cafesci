import java.util.List;

public class Manager extends Person {
    private String managerID;

    // Constructor
    public Manager(String personID, String name, String phoneNumber, Role role, String managerID) {
        super(personID, name, role); // เรียก Constructor ของ Person และกำหนด Role
        this.managerID = managerID;
    }

    // Getters and Setters
    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    // Method สร้างโปรโมชั่นใหม่
    public void createPromotion(String promotionDetails) {
        System.out.println("New promotion created: " + promotionDetails);
    }

    // Method อัปเดตโปรโมชั่น
    public void updatePromotion(String promotionID, String newDetails) {
        System.out.println("Promotion ID " + promotionID + " updated with new details: " + newDetails);
    }

    // Method ลบโปรโมชั่น
    public void deletePromotion(String promotionID) {
        System.out.println("Promotion ID " + promotionID + " has been deleted.");
    }

    // Method จัดโต๊ะให้ลูกค้า
    public void assignTable(int tableNumber, String customerID) {
        System.out.println("Table number " + tableNumber + " assigned to customer " + customerID);
    }

    // Method อัปเดตสถานะโต๊ะ
    public void updateTableStatus(int tableNumber, String status) {
        System.out.println("Table number " + tableNumber + " status updated to: " + status);
    }

    // Method ปล่อยโต๊ะ
    public void releaseTable(int tableNumber) {
        System.out.println("Table number " + tableNumber + " has been released.");
    }

    // Method สั่งเครื่องดื่มให้ลูกค้า
    public void orderDrink(int drinkID, String customerID) {
        System.out.println("Drink ID " + drinkID + " ordered for customer " + customerID);
    }

    // Method อัปเดตการสั่งเครื่องดื่ม
    public void updateDrinkOrder(int orderID, int drinkID, String newDetails) {
        System.out.println("Order ID " + orderID + " with Drink ID " + drinkID + " updated with details: " + newDetails);
    }

    // Method ยกเลิกคำสั่งซื้อเครื่องดื่ม
    public void cancelDrinkOrder(int orderID) {
        System.out.println("Drink order with Order ID " + orderID + " has been canceled.");
    }

    // Method สร้างคำสั่งซื้อใหม่
    public void createOrder(String customerID, List<String> orderDetails) {
        System.out.println("New order created for customer " + customerID);
        for (String detail : orderDetails) {
            System.out.println("Order detail: " + detail);
        }
    }

    // Method อัปเดตสถานะคำสั่งซื้อ
    public void updateOrderStatus(int orderID, String newStatus) {
        System.out.println("Order ID " + orderID + " status updated to: " + newStatus);
    }

    // Method แสดงสถานะคำสั่งซื้อ
    public String displayOrderStatus(int orderID) {
        String status = "Processing"; // ตัวอย่างสถานะ
        System.out.println("Order ID " + orderID + " status is: " + status);
        return status;
    }

    // Method ยกเลิกคำสั่งซื้อ
    public void cancelOrder(int orderID) {
        System.out.println("Order ID " + orderID + " has been canceled.");
    }
}
