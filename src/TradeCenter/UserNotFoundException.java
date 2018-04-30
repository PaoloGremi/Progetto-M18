package TradeCenter;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("Utente non trovato");
    }
}
