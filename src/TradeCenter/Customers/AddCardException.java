package TradeCenter.Customers;

public class AddCardException extends  RuntimeException {

    public AddCardException() {
        super("Impossible to add the card");
    }
}
