package TradeCenter.Exceptions.UserExceptions;

public class NoTradesExeption extends RuntimeException {
    public NoTradesExeption() {
        super("No trade yet");
    }
}
