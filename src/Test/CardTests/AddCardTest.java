package Test.CardTests;

import TradeCenter.Card.Card;
import TradeCenter.Card.CardType;
import TradeCenter.Card.Description;
import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;

import java.io.IOException;

public class AddCardTest {
    public static void main(String[] args) {
        //caso favorevole
        TradeCenter tradeCenter = new TradeCenter();
        tradeCenter.addCustomer("nome", "1234");
        Customer customer = tradeCenter.searchCustomer("nome");         //rendere più effieciente il prendere un customer
        try{
            //todo vedere perche devo dare io l'id alla carta, cosi non sarebbe univoco
            Card card = new Card(1, new Description("drago","bianco occhi blu",CardType.YuGiOh,"src/Test/CustomerTests/drago.jpg"));
            customer.addCard(card);
            customer.removeCard(card);
        }catch (IOException e) {
            e.printStackTrace();
        }

        //caso sfavorevole - provo ad aggiungere una carta ma l'url dell'immagine è sbagliato
        try{
            Card card = new Card(2, new Description("drago","bianco occhi blu",CardType.YuGiOh,"src/Test/CustomerTests/drag.jpg"));
            customer.addCard(card);
            customer.removeCard(card);
        }catch (IOException e) {
            e.printStackTrace();
        }

        //caso sfavorevole - provo a rimuovere una carta che non c'è
        try{
            customer.removeCard(new Card(1, new Description("drago","bianco occhi blu",CardType.YuGiOh,"src/Test/CustomerTests/drago.jpg")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
