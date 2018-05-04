package TradeCenter.Exceptions.UserExceptions;

import TradeCenter.TradeCenter;

public class UserNotFoundException extends RuntimeException{
    /**
     * Exception that handle when there is no user that have the name searched
     */
    public UserNotFoundException() {
        super("User Not Found");
    }
}
