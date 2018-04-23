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

<<<<<<< HEAD
    /*protected Card[] searchByString(String s){



    }*/
=======
    protected Card[] searchByString(String s){

        Card[] cardsFound = collection.searchByString(s);

        return cardsFound;

    }
>>>>>>> 357a6be2ff414c472b22ff55ddd87b3adb1bb7e1
}
