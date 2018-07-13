package TradeCenter.Exceptions.UserExceptions;

public class AlreadyLoggedInException extends RuntimeException {
    public AlreadyLoggedInException(){
        super("The account is already logged in another client");
    }
}
