package TradeCenter.Exceptions.CardExceptions;

public class EmptyCollectionException extends  RuntimeException {

    /**
     * Exception that handle the case a customer have an empty collection
     */

    public EmptyCollectionException() {
        super("Can't have an empty collection");
    }
}
