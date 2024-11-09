public class CreditCard {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;

    // Constructor
    public CreditCard(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public boolean validateCard() {
        if (cardNumber.length() == 16 && cvv.length() == 3) {
            return true;
        } else {
            return false;
        }
    }
}
