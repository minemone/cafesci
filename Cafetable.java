public class Cafetable {
    private int cafetableID;
    private String tableName;
    private float tablePrice;
    private String status; // เพิ่มสถานะของโต๊ะ เช่น Available, Reserved, Occupied

    // Constructor
    public Cafetable(int cafetableID, String tableName, float tablePrice) {
        this.cafetableID = cafetableID;
        this.tableName = tableName;
        this.tablePrice = tablePrice;
        this.status = "Available"; // ค่าเริ่มต้นของสถานะ
    }

    // Method จองโต๊ะ
    public void reserveTable() {
        if (status.equals("Available")) {
            status = "Reserved";
            System.out.println("Table ID " + cafetableID + " has been reserved.");
        } else {
            System.out.println("Table ID " + cafetableID + " is currently not available.");
        }
    }

    // Method เปลี่ยนการจัดวางโต๊ะ
    public void changeTableLayout(String layout) {
        System.out.println("Table layout has been changed to: " + layout);
    }

    // Method ติดตามสถานะคำสั่งซื้อ
    public void trackOrderStatus(Order order) {
        System.out.println("Tracking order status for table: " + tableName);
        // System.out.println("Order status: " + order.getStatus());
    }

    // Method ระบุหมายเลขโต๊ะ
    public void specifyTable(int tableID) {
        this.cafetableID = tableID;
        System.out.println("Table ID has been set to: " + tableID);
    }

    // Method อัปเดตสถานะโต๊ะ
    public void updateTableStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Table status has been updated to: " + newStatus);
    }

    // Method ดูใบเสร็จการจองโต๊ะ
    public void viewTableReservationReceipt() {
        System.out.println("Viewing reservation receipt for table: " + tableName);
        // Logic แสดงรายละเอียดใบเสร็จสามารถเพิ่มได้ตามต้องการ
    }

    // Method แสดงรายละเอียดของแต่ละโต๊ะ
    public void showDetailsOfTable() {
        System.out.println("รายละเอียดโต๊ะ: ID = " + cafetableID + ", ชื่อโต๊ะ = " + tableName + 
                           ", ราคาโต๊ะ = " + tablePrice + ", สถานะ = " + status);
    }

    // Method ล้างโต๊ะ
    public void clearTable() {
        if (!status.equals("Available")) {
            status = "Available";
            System.out.println("Table ID " + cafetableID + " has been cleared.");
        } else {
            System.out.println("Table ID " + cafetableID + " is already available.");
        }
    }

    // Method ดึงสถานะโต๊ะ
    public String getTableStatus() {
        return status;
    }

    // Method ดึงข้อมูลโต๊ะ
    public Cafetable getTable() {
        return this;
    }

    // Method ดึงราคาของโต๊ะ
    public float getTablePrice() {
        return tablePrice;
    }

    public int getCafetableID() {
        return cafetableID;
    }

    public String getTableName() {
        return tableName;
    }
}
