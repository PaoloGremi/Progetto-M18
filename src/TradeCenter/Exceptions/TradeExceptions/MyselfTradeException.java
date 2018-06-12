package TradeCenter.Exceptions.TradeExceptions;

public class MyselfTradeException extends RuntimeException{

    /**
     * exception thrown when a user tries to trade with himself
     */
    public MyselfTradeException(){
        super("You cannot trade with yourself!");
    }
}
