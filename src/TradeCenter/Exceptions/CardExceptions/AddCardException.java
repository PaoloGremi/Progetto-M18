package TradeCenter.Exceptions.CardExceptions;

public class AddCardException extends  RuntimeException {
    /**
     * Exception that handle the case when the card is not valid.
     */
    public AddCardException() {
        super("Impossible to add the card");
    }
}
