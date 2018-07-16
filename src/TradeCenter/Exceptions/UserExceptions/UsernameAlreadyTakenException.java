package TradeCenter.Exceptions.UserExceptions;

public class UsernameAlreadyTakenException extends RuntimeException {

    /**
     * Exception that handle the case a username's already in use.
     */

    public UsernameAlreadyTakenException(){
        super("Username already in use");
    }
}
