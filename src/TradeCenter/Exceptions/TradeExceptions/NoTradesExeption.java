package TradeCenter.Exceptions.TradeExceptions;

public class NoTradesExeption extends RuntimeException {
    public NoTradesExeption() {
        super("No trade yet");
    }
}
