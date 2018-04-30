package TradeCenter;


public class NullDescriptionException extends NullPointerException {
    /**
     * Exception that handle the case when a user tries to add a description that is not valid
     */
    public NullDescriptionException(){
        super("Impossible adding a null description");
    }
}
