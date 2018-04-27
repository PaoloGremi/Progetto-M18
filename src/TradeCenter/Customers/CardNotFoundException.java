package TradeCenter.Customers;

public class CardNotFoundException extends RuntimeException {
    public void cardNotFound(String id, String username){

        System.out.println("Card not found in the collection of the customer: id: "+ id +" username: "+ username);

    }
}
