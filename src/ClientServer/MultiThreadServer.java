package ClientServer;

import TradeCenter.Card.PokemonDescription;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.TradeExceptions.AlreadyStartedTradeException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UsernameAlreadyTakenException;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Offer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MultiThreadServer implements Runnable {
    private Socket csocket;
    private static TradeCenter tradeCenter = new TradeCenter();
    MultiThreadServer(Socket csocket, TradeCenter tradeCenter) {
        this.csocket = csocket;
        this.tradeCenter = tradeCenter;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket ssock = new ServerSocket(8889);
        System.out.println("Listening");

        while (true) {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock, tradeCenter)).start();
        }
    }
    public void run() throws CheckPasswordConditionsException, UsernameAlreadyTakenException {
        try {
            ObjectInputStream in = new ObjectInputStream(csocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(csocket.getOutputStream());

            MessageServer m = (MessageServer) in.readObject();

            switch (m.getMessage()){
                case ADDCUSTOMER:
                    try {
                        tradeCenter.addCustomer(m.getString1(), m.getString2());
                        Customer c = tradeCenter.searchCustomer(m.getString1());
                        os.writeObject(c);
                    }
                    catch (CheckPasswordConditionsException | UsernameAlreadyTakenException e){
                        os.writeObject(e);
                    }
                    break;
                case SEARCHCUSTOMER:
                    tradeCenter.searchCustomer(m.getString1());
                    Customer customer = tradeCenter.searchCustomer(m.getString1());
                    os.writeObject(customer);
                    break;
                case POSSIBLETRADE:
                    os.writeObject(tradeCenter.notAlreadyTradingWith(m.getCustomer1(), m.getCustomer2()));
                case CREATEOFFER:
                    try{
                        tradeCenter.createTrade(m.getCustomer1(), m.getCustomer2(), m.getOffer1(), m.getOffer2());
                        os.writeObject(Boolean.TRUE);
                    }catch (AlreadyStartedTradeException e){
                        os.writeObject(Boolean.FALSE);
                    }
                    break;
                case RAISEOFFER:
                    tradeCenter.updateTrade(new Offer(m.getCustomer1(), m.getCustomer2(), m.getOffer1(), m.getOffer2()));
                    os.writeObject(Boolean.TRUE);       //per avere la stampa
                    break;
                case SEARCHOFFER:
                    os.writeObject(tradeCenter.showUserTrades(m.getCustomer1()));
                    break;
                case SEARCHTRADE:
                    os.writeObject(tradeCenter.takeStartedTrade(m.getCustomer1(), m.getCustomer2()));
                    break;
                case SWITCHCARDS:
                    tradeCenter.endTrade(m.getTrade(), true);
                    break;
                case ENDTRADE:
                    tradeCenter.endTrade(m.getTrade(), false);
                    break;
                case VERIFYPASSWORD:
                    boolean flagPass = tradeCenter.verifyPassword(m.getString1(),m.getString2());
                    os.writeObject(flagPass);
                    break;
                case LOGDIN:
                    boolean flagLog = tradeCenter.loggedIn(m.getString1(),m.getString2());
                    os.writeObject(flagLog);
                    break;

                case SEARCHUSER:
                    ArrayList<Customer> users = tradeCenter.searchUsers(m.getString1(), m.getCustomer1().getUsername());
                    os.writeObject(users);
                    break;

                case SEARCHDESCRIPTION:
                    ArrayList<HashMap<Customer, Collection>> descriptions = tradeCenter.searchByDescription(m.getDescription());
                    os.writeObject(descriptions);
                    break;
                case REMOVEWISH:
                    Customer customer2 = tradeCenter.searchCustomer(m.getString1());
                    tradeCenter.removeFromWishList(m.getDescription(),customer2);
                    break;
                case FILTERPOKEMONDESCR:
                    HashSet<PokemonDescription> descrMatched = tradeCenter.searchDescrInPokemonDb(m.getPokemonAll());
                    if(descrMatched.size()==1) {
                        ArrayList<HashMap<Customer, Collection>> list = tradeCenter.searchByDescription(descrMatched.iterator().next());
                        os.writeObject(list);
                    }
                    else{
                        ArrayList<HashMap<Customer, Collection>> listVuota=new ArrayList<>();
                        os.writeObject(listVuota);
                        System.err.println("Piu descrizioni trovate o nessuna, ancora da implementare");}
                    break;

            }
            csocket.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
