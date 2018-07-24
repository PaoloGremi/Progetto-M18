package customer;

import TradeCenter.Card.Card;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

public class customerTest {
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


    @Before
    public void initTradeCenter(){
        Customer c1=new Customer("User-1","user1","Password1");
        Customer c2=new Customer("User-2","user2","Password2");

        BufferedImage bfNull=null;

        PokemonDescription pd1=new PokemonDescription("Blastoise","Shellfish Pokémon jewels",bfNull,1,"Water",100,189,"5’3’’",52);
        PokemonDescription pd2=new PokemonDescription("Chansey","Egg Pokémon",bfNull,2,"Fairy",120,76,"3’7’’",55);

        YuGiOhDescription yd1=new YuGiOhDescription("Alexandrite Dragon","Many of the czars' lost jewels can be found in the scales of this priceless dragon",bfNull,1,"YS15-ENY01",4,2000,100,"Dragon","Normal Monster");
        YuGiOhDescription yd2=new YuGiOhDescription("Archfiend Soldier","An expert at battle who belongs to a crack diabolical unit",bfNull,2,"YS15-END02",4,1900,1500,"Dragon","Normal Monster");

        Card card1=new Card(1,pd1);
        Card card2=new Card(2,pd2);
        Card card3=new Card(3,yd1);
        Card card4=new Card(4,yd2);

        c1.addCard(card1);
        c1.addCard(card3);

        c2.addCard(card2);
        c2.addCard(card4);

        this.c1=c1;
        this.c2=c2;
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
     * Search a one Description in Customer's collection of cards by a string
     */
    @Test
    public void searchByString1(){
      /* Collection coll=c1.searchByString("priceless");
       assertEquals(true,coll.isInTheCollection(card3));*/
    }
    /**
     * Search a two Description in Customer's collection of cards by a string
     */
    @Test
    public void searchByString2(){
        /*Collection coll=c1.searchByString("jewels");
        assertEquals(true,coll.isInTheCollection(card3));
        assertEquals(true,coll.isInTheCollection(card1));*/
    }

}
