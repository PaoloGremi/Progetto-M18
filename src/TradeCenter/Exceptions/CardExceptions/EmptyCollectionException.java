package TradeCenter.Exceptions.CardExceptions;

public class EmptyCollectionException extends  RuntimeException {

    public EmptyCollectionException() {
        super("Can't have an empty collection");
    }
}
