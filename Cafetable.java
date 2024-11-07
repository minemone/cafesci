import java.time.LocalDateTime;

public class Cafetable {
    private int tableID;
    private String tableName;
    private String status;
    private double tablePrice;
    private LocalDateTime reservationDateTime; // ฟิลด์สำหรับเก็บเวลาที่จอง
    private String memberID; // เพิ่มฟิลด์สำหรับเก็บรหัสสมาชิก

    // Constructor สำหรับสร้างโต๊ะพร้อมข้อมูลพื้นฐาน
    public Cafetable(int tableID, String tableName, double tablePrice) {
        this.tableID = tableID;
        this.tableName = tableName;
        this.status = "ว่าง"; // กำหนดสถานะเริ่มต้นเป็น "ว่าง"
        this.tablePrice = tablePrice; // กำหนดราคาของโต๊ะตามที่กำหนด
    }

    // Getter และ Setter สำหรับ reservationDateTime
    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
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
    } public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    // Getter สำหรับ tableID
    public int getTableID() {
        return tableID;
    }

    // Getter สำหรับ tableName
    public String getTableName() {
        return tableName;
    }

    // Getter สำหรับ status
    public String getStatus() {
        return status;
    }

    // Getter สำหรับ tablePrice
    public double getTablePrice() {
        return tablePrice;
    }

    // Setter สำหรับ status
    public void setStatus(String status) {
        this.status = status;
    }

    // Method แสดงรายละเอียดของโต๊ะ โดยให้คำว่า "โต๊ะ" ปรากฏเพียงครั้งเดียว
    public void displayTableStatus() {
        System.out.println(tableName + " (ID: " + tableID + ") สถานะ: " + status + " + ราคา: " + tablePrice + " บาท");
    }
}
