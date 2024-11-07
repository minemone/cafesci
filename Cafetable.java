public class Cafetable {
    private int tableID;
    private String tableName;
    private String status;
    private double tablePrice;

    // Constructor สำหรับสร้างโต๊ะพร้อมข้อมูลพื้นฐาน
    public Cafetable(int tableID, String tableName, double tablePrice) {
        this.tableID = tableID;
        this.tableName = tableName;
        this.status = "ว่าง"; // กำหนดสถานะเริ่มต้นเป็น "ว่าง"
        this.tablePrice = tablePrice; // กำหนดราคาของโต๊ะตามที่กำหนด
    }

    // Method จองโต๊ะ และคืนค่า boolean เพื่อบอกว่าจองสำเร็จหรือไม่
    public boolean reserveTable() {
        if (status.equals("ว่าง")) {
            status = "จองแล้ว";
            System.out.println(tableName + " ถูกจองแล้ว.");
            return true; // คืนค่า true ถ้าจองสำเร็จ
        } else {
            System.out.println(tableName + " ไม่สามารถจองได้.");
            return false; // คืนค่า false ถ้าจองไม่สำเร็จ
        }
    }

    public int getTableID() {
        return tableID;
    }

    public String getTableName() {
        return tableName;
    }

    public String getStatus() {
        return status;
    }

    public double getTablePrice() {
        return tablePrice;
    }
      // สร้างเมธอด setStatus เพื่อปรับปรุงสถานะ
    public void setStatus(String status) {
        this.status = status;
    }

    // Method แสดงรายละเอียดของโต๊ะ โดยให้คำว่า "โต๊ะ" ปรากฏเพียงครั้งเดียว
    public void displayTableStatus() {
        System.out.println(tableName + " (ID: " + tableID + ") สถานะ: " + status + " + ราคา: " + tablePrice + " บาท");
    }
}
