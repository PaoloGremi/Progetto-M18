package Test.CardTests;

import TradeCenter.Card.Card;
import TradeCenter.Card.YuGiOhDescription;
import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;

import java.io.IOException;

public class AddCardTest {
    public static void main(String[] args) {

        //caso favorevole
        TradeCenter tradeCenter = new TradeCenter();
        tradeCenter.addCustomer("nome", "Abcdefghil123");
        Customer customer = new Customer("123", "nome", "Abcdefghil123");        //rendere più effieciente il prendere un customer
        try{
            //todo vedere perche devo dare io l'id alla carta, cosi non sarebbe univoco
            Card card = new Card(1, new YuGiOhDescription("drago","bianco occhi blu", "src/Test/CardTests/drago.jpg", "Reference", 7, 3000, 1200, 1, 2));
            customer.addCard(card);
            customer.removeCard(card);
        }catch (IOException e) {
            e.printStackTrace();
        }

        //caso sfavorevole - provo ad aggiungere una carta ma l'url dell'immagine è sbagliato
        try{
            Card card = new Card(2, new YuGiOhDescription("drago","bianco occhi blu", "src/Test/CardTests/drago.jpg", "Reference", 7, 3000, 1200, 1, 2));
            customer.addCard(card);
            customer.removeCard(card);
        }catch (IOException e) {
            e.printStackTrace();
        }

        //caso sfavorevole - provo a rimuovere una carta che non c'è
        try{
            customer.removeCard(new Card(1, new YuGiOhDescription("drago","bianco occhi blu", "src/Test/CardTests/drago.jpg", "Reference", 7, 3000, 1200, 1, 2)));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
