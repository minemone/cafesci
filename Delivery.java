public class Delivery {
    private int deliveryId;
    private String address;
    private String status;

    public Delivery(int deliveryId, String address) {
        this.deliveryId = deliveryId;
        this.address = address;
        this.status = "Pending"; // ตั้งค่าเริ่มต้นเป็นสถานะ "รอดำเนินการ"
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public String getStatus() {
        return status;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("สถานะการจัดส่งอัปเดตเป็น: " + status);
    }

    public void displayDeliveryDetails() {
        System.out.println("หมายเลขการจัดส่ง: " + deliveryId);
        System.out.println("ที่อยู่: " + address);
        System.out.println("สถานะ: " + status);
    }
}
