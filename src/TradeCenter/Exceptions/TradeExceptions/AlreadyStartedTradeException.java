package TradeCenter.Exceptions.TradeExceptions;

public class AlreadyStartedTradeException extends RuntimeException {

    /**
     * exception thrown when a user tries to trade with a customer who's already trading with
     */
    public AlreadyStartedTradeException(String otherCustomer){
        super("You are already trading with "+ otherCustomer + "!");
    }
}
