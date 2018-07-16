package TradeCenter.Exceptions.InterfaceExceptions;

public class EmptyUsernameException extends RuntimeException {

    /**
     *Exception that handle the case someone try to sign in without an username
     */

    public EmptyUsernameException(){
        super("Username can't be empty");
    }
}
