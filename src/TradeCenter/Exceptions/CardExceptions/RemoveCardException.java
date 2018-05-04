package TradeCenter.Exceptions.CardExceptions;

public class RemoveCardException extends  RuntimeException{

    /**
     * Exception that handle the case when the card can't be removed.
     */
    public RemoveCardException() {
        super("Impossible to remove the card");
    }
}
