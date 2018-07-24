package tradeCenter;
import TradeCenter.Card.Card;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.DatabaseProxy.DBProxy;
import TradeCenter.TradeCenter;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class tradeCenterTest {
    public Customer c1;
    public Customer c2;
    public PokemonDescription pd1;
    public PokemonDescription pd2;
    public YuGiOhDescription yd1;
    public YuGiOhDescription yd2;
    public Card card1;
    public Card card2;
    public Card card3;
    public Card card4;
    public TradeCenter tradeCenter;
    private DBProxy proxy;
    /**
     * DB initialized with two Customer and 8 Pokemon cards and 8 YuGIOH cards.
     *
     *
     */
    @Before
    public void initTradeCenter(){
        tradeCenter=new TradeCenter();
        this.tradeCenter=tradeCenter;
        DBProxy proxy=DBProxy.getInstance();
        this.proxy=proxy;

        Customer c1=proxy.retrieveSingleCustomer("User1");
        Customer c2=proxy.retrieveSingleCustomer("User2");
        this.c1=c1;
        this.c2=c2;

        //Descriptions
        BufferedImage bfNull=null;

        PokemonDescription pd1=new PokemonDescription("Blastoise","Shellfish Pokémon jewels",bfNull,1,"Water",100,189,"5’3’’",52);
        PokemonDescription pd2=new PokemonDescription("Chansey","Egg Pokémon",bfNull,2,"Fairy",120,76,"3’7’’",55);

        YuGiOhDescription yd1=new YuGiOhDescription("Alexandrite Dragon","Many of the czars' lost jewels can be found in the scales of this priceless dragon",bfNull,1,"YS15-ENY01",4,2000,100,"Dragon","Normal Monster");
        YuGiOhDescription yd2=new YuGiOhDescription("Archfiend Soldier","An expert at battle who belongs to a crack diabolical unit",bfNull,2,"YS15-END02",4,1900,1500,"Dragon","Normal Monster");
        //Cards
        Card card1=new Card(1,pd1);
        Card card2=new Card(2,pd2);
        Card card3=new Card(3,yd1);
        Card card4=new Card(4,yd2);
        this.pd1=pd1;
        this.pd2=pd2;
        this.yd1=yd1;
        this.yd2=yd2;
        this.card1=card1;
        this.card2=card2;
        this.card3=card3;
        this.card4=card4;
    }

    /**
     *Check two Customers existing in test DB
     */

    @Test
    public void checkCustomerExisting(){
        String user1="User1";
        String user2="User2";
        boolean condition1=tradeCenter.searchCustomer(user1).getUsername().equals(user1);
        boolean condition2=tradeCenter.searchCustomer(user2).getUsername().equals(user2);
        assertTrue(condition1);
        assertTrue(condition2);
    }

    /**
     * Add Customer in TradeCenter
     * Modify DB adding a customer
     */
    @Test
    public void addCustomer(){
        String user3="User3";
        Customer c3=tradeCenter.addCustomer(user3,"Password1");
        assertEquals(user3,c3.getUsername());
        //TODO togli customer 3
    }

    /**
     *Search Users by a similar Username
     */
    @Test
    public void searchUsers(){
        String s="User";
        ArrayList<String> list=tradeCenter.searchUsers(s,"User1");
        for(String currentUsername: list){
            assertTrue(currentUsername.contains(s));
        }
    }

    /**
     *  Initialize Customer's Collection with 7 cards of Pokémon and Pokemon
     *
     */
    @Test
    public void randomCards(){
        String userId=c1.getId();
        ArrayList<Card> cardsListYuGiOh;
        cardsListYuGiOh=tradeCenter.fromYuGiOhCatalog(userId);
        Collection c1Coll=tradeCenter.searchCustomerById(userId).getCollection();
        for (Card currentCard:cardsListYuGiOh) {
             assertTrue(c1Coll.isInTheCollection(currentCard));
        }

        ArrayList<Card> cardsListPokemon;
        cardsListPokemon=tradeCenter.fromPokemonCatalog(userId);
        Collection c2Coll=tradeCenter.searchCustomerById(userId).getCollection();
        for (Card currentCard:cardsListPokemon) {
            assertTrue(c2Coll.isInTheCollection(currentCard));
        }

        proxy.removeCardsFromCustomer(userId);

    }
}