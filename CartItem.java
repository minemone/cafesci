public class CartItem {
    private Drink drink;
    private int quantity;
    private double price;

    public CartItem(Drink drink, int quantity, double price) {
        this.drink = drink;
        this.quantity = quantity;
        this.price = price;
    }

    public Drink getDrink() {
        return drink;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ฟังก์ชันอื่น ๆ เช่น getQuantity, setQuantity, ฯลฯ
}
