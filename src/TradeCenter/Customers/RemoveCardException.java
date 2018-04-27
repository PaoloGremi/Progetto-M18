package TradeCenter.Customers;

public class RemoveCardException extends  RuntimeException{
    public RemoveCardException() {
        super("Impossible to remove the card");
    }
}
