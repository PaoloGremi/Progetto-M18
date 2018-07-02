package TradeCenter.Exceptions.InterfaceExceptions;

public class EmptyUsernameException extends RuntimeException {
    public EmptyUsernameException(){
        super("Username can't be empty");
    }
}
