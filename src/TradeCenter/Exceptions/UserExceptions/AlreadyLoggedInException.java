package TradeCenter.Exceptions.UserExceptions;

public class AlreadyLoggedInException extends RuntimeException {
    /**
     * Exception that handle the case an account is already logged in
     */
    public AlreadyLoggedInException(){
        super("The account is already logged in another client");
    }
}
