package TradeCenter.Exceptions.UserExceptions;

public class UsernameAlreadyTakenException extends RuntimeException {

    public UsernameAlreadyTakenException(){
        super("Username already in use");
    }
}
