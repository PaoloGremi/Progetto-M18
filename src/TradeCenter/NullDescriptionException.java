package TradeCenter;

public class NullDescriptionException extends NullPointerException {
    public NullDescriptionException(){
        super("Non è possibile aggiungere una descrizione nulla");
    }
}
