package TradeCenter.Exceptions.TradeExceptions;

import java.util.NoSuchElementException;

public class NoSuchTradeException extends NoSuchElementException {
    /**
     * Exception handling the case when we try to exchange cards when there isn't a trade
     */
    public NoSuchTradeException() {
        super("This trade doesn't exists!");
    }
}
