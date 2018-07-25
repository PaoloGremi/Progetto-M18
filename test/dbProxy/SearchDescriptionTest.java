package dbProxy;

import DatabaseProxy.DBProxy;
import Interface.searchCard.filterChoice.PokemonAll;
import Interface.searchCard.filterChoice.YuGiOhAll;
import TradeCenter.Card.Card;
import TradeCenter.Card.Description;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Customer;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchDescriptionTest {
    private DBProxy proxy;
    private Customer c1;
    private Customer c2;
    private PokemonDescription pd1;
    private PokemonDescription pd2;
    private YuGiOhDescription yd1;
    private YuGiOhDescription yd2;
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    /**
     * DB initialized with two Customer and 8 Pokemon cards and 8 YuGIOH cards.
     */

    /**
     * Get instance of DBProxy and first case
     */
    @Before
    public void getDBProxyInstance() {
        DBProxy proxy = DBProxy.getInstance();
        this.proxy = proxy;

        Customer c1 = new Customer("USER-1", "User1", "Password1");
        Customer c2 = new Customer("USER-2", "User2", "Password1");
        this.c1 = c1;
        this.c2 = c2;

        //Description used in tests
        BufferedImage bfNull=null;

        PokemonDescription pd1=new PokemonDescription("Blastoise","Shellfish Pokémon jewels",bfNull,2,"Water",100,189,"5’3’’",52);
        PokemonDescription pd2=new PokemonDescription("Chansey","Egg Pokémon",bfNull,3,"Fairy",120,76,"3’7’’",55);

        YuGiOhDescription yd1=new YuGiOhDescription("Alexandrite Dragon","Many of the czars' lost jewels can be found in the scales of this priceless dragon. Its creator remains a mystery, alogn with how they acquired the imperial treasures. But whosoever finds this dragon has hit the jackpot... whether they know it or not.",bfNull,1,"YS15-ENY01",4,2000,100,"Dragon","Normal Monster");
        YuGiOhDescription yd2=new YuGiOhDescription("Archfiend Soldier","An expert at battle who belongs to a crack diabolical unit",bfNull,3,"YS15-END02",4,1900,1500,"Dragon","Normal Monster");

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
        //Case

        c1.addCard(card1);
        c1.addCard(card3);
        c2.addCard(card2);
        c2.addCard(card4);

        this.proxy.updateCustomer(c1);
        this.proxy.updateCustomer(c2);
    }
    /**
     * Search Description By string considering Description's Name
     */
    @Test
    public void getDescriptionByString() {
        //FIRST CASE
        String nameSearched="Dragon";
        HashSet<Description> descrFounded1 = proxy.getFoundDescrByString(nameSearched);
        HashSet<Description> descrExpected=new HashSet<>();
        descrExpected.add(yd1);
        for (Description currDesc : descrFounded1) {
            assertTrue(currDesc.getName().contains(nameSearched));
        }
        //SECOND CASE: String is empty.
        HashSet<Description> descrFounded2 = proxy.getFoundDescrByString("");
        assertTrue(descrFounded2.size() == 16);
        //THIRD CASE: No Description founded
        HashSet<Description> descrFounded3 = proxy.getFoundDescrByString("mkmlkmsdakm");
        assertTrue(descrFounded3.isEmpty());
    }

    /**
     * 4 CASES of Pokèmon filtering
     */

    @Test
    public void searchPokèmonDescription(){
        //1 CASE: Nothing is set.All Pokemon Description Founded.
        PokemonAll filter1=new PokemonAll("","",0,0,0,"","");
        HashSet<Description> descrFounded1 = proxy.getFoundDescrPokemon(filter1);
        assertTrue(descrFounded1.size()==8);

        //2 CASE: Set Type of Pokemon
        PokemonAll filter2=new PokemonAll("","Water",0,0,0,"","");
        HashSet<Description> descrFounded2 = proxy.getFoundDescrPokemon(filter2);
        if(!descrFounded2.isEmpty()) {
            for (Description currDescr : descrFounded2) {
                assertEquals(filter2.getType(), ((PokemonDescription) currDescr).getType());
            }
        }
        //3 CASE: set hp,lev,weigth.    RANGE used: (10,50,15)
        int hp1=90;
        int lev1=42;
        int weigth1=180;

        int hpRange=10;
        int levRange=15;
        int weiRange=50;
        PokemonAll filter3=new PokemonAll("","",hp1,lev1,weigth1,"","");
        HashSet<Description> descrFounded3 = proxy.getFoundDescrPokemon(filter3);
        if(!descrFounded3.isEmpty()){
            for (Description currDescr : descrFounded3) {
                PokemonDescription currPok=(PokemonDescription) currDescr;
                int hpCurr=currPok.getHp();
                int levCurr=currPok.getLevel();
                int weCurr=currPok.getWeight();
                boolean condition1=Math.abs(hp1-hpCurr)<=hpRange;
                boolean condition2=Math.abs(lev1-levCurr)<=levRange;
                boolean condition3=Math.abs(weigth1-weCurr)<=weiRange;
                assertTrue(condition1 && condition2 && condition3);
            }
        }
        //4CASE: set len1 and len 2
        String len1="5";
        String len2="7";
        PokemonAll filter4=new PokemonAll("","",0,0,0,len1,len2);
        HashSet<Description> descrFounded4 = proxy.getFoundDescrPokemon(filter4);
        if(!descrFounded3.isEmpty()){
            for (Description currDescr : descrFounded4) {
                PokemonDescription currPok=(PokemonDescription) currDescr;
                assertEquals(len1+"’"+len2+"’’",currPok.getLength());
            }

            }
    }

    @Test
    public void searchYuGiOhDescription(){
        //1 CASE: Nothing is set.Retrun all YuGiOh Description in DB
        YuGiOhAll filter1=new YuGiOhAll("","",0,0,0,"","");
        HashSet<Description> descrFounded1=proxy.getFoundDescrYugioh(filter1);
        assertTrue(descrFounded1.size()==8);

        //2 CASE: Set monsterType ,type and reference
        String ref="YS15-ENY01";
        String monsterType="Dragon";
        String type="Normal monster";
        YuGiOhAll filter2=new YuGiOhAll("",ref,0,0,0,monsterType,type);
        HashSet<Description> descrFounded2=proxy.getFoundDescrYugioh(filter2);
        for(Description currentDescr: descrFounded2){
            YuGiOhDescription currYu=(YuGiOhDescription) currentDescr;
            assertEquals(ref,currYu.getReference());
            assertEquals(monsterType,currYu.getMonster_type_id());
            assertEquals(type,currYu.getCard_type_id());
        }
        //3 CASE: set lev,atk,def RANGE used: (2,500,500)
        int lev1=2;
        int atk1=1500;
        int def1=800;

        int atkRange=500;
        int levRange=2;
        int defRange=500;
        YuGiOhAll filter3=new YuGiOhAll("","",lev1,atk1,def1,"","");
        HashSet<Description> descrFounded3=proxy.getFoundDescrYugioh(filter3);
        if(!descrFounded3.isEmpty()){
            for (Description currDescr : descrFounded3) {
                YuGiOhDescription currYu=(YuGiOhDescription) currDescr;
                int levCurr=currYu.getLevel();
                int atkCurr=currYu.getAtk();
                int defCurr=currYu.getDef();

                boolean condition1=Math.abs(lev1-levCurr)<=levRange;
                boolean condition2=Math.abs(atk1-atkCurr)<=atkRange;
                boolean condition3=Math.abs(def1-defCurr)<=defRange;
                assertTrue(condition1 && condition2 && condition3);
            }
        }

    }
}