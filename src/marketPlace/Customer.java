package marketPlace;

public class Customer {

    private String user;
    private String pssw;
    private Collection collection;

    protected boolean addCard(Card card){
        if(collection.addCardToColl(card)){
            return true;
        }

        return false;
    }

    protected boolean rmCard(Card card){
        if(collection.remCardFromColl(card)){
            return  true;
        }

        return  false;
    }

    protected boolean createCard(Description d, int id){

        Card newCard = new Card(id, d);

        if(addCard(newCard)){

            return true;

        }

        return  false;
    }

    /*protected Card[] searchByString(String s){



    }*/
}
