package TradeCenter.Customers;

public class CheckPasswordConditionsException extends  RuntimeException {
    /**
     * Exception that handle the case when the password doesn't respect the conditions
     */
    public CheckPasswordConditionsException(){
        super("Invalid password. Must have at least: eight characters, one number, one uppercase and one lowercase.");
    }
}
