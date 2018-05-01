package TradeCenter.Customers;

public class CardNotFoundException extends RuntimeException {

    /**
     * Exception that handle the case when the card is not found.
     *
     * @param id Id of the customer that calls the method
     * @param username Password of the customer that calls the method
     * @return Message if the card does't exists in the customer collection
     */
    public String cardNotFound(String id, String username){

        return ("Card not found in the collection of the customer: id: "+ id +" username: "+ username);

    }

    /**
     * Exception that handle the case when the card is not found in the system.
     *
     * @return Message if the card doesn't exists in the system
     */
    public String cardNotInTheSystem(){
        return "The card you're searching is not in the system yet";
    }
}
