package TradeCenter.Exceptions.TradeExceptions;

public class NoTradesExeption extends RuntimeException {

    /**
     *Exception that handle the case a customer haven't done a trade yet
     */

    public NoTradesExeption() {
        super("No trade yet");
    }
}
