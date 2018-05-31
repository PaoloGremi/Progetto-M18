package ClientServer;

import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.TradeCenter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

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
    public void run() throws CheckPasswordConditionsException {
        try {
            ObjectInputStream in = new ObjectInputStream(csocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(csocket.getOutputStream());

            MessageServer m = (MessageServer) in.readObject();

            switch (m.getMessage()){
                case ADDCUSTOMER:
                    tradeCenter.addCustomer(m.getString1(), m.getString2());
                    Customer c = tradeCenter.searchCustomer(m.getString1());
                    os.writeObject(c);
                    csocket.close();
                    break;
                case SEARCHCUSTOMER:
                    tradeCenter.searchCustomer(m.getString1());
                    Customer customer = tradeCenter.searchCustomer(m.getString1());
                    os.writeObject(customer);
                    csocket.close();
                    break;
                //case CREATEOFFER:
                //case SWITCHCARDS:
                case VERIFYPASSWORD:
                    boolean flagPass = tradeCenter.verifyPassword(m.getString1(),m.getString2());
                    os.writeObject(flagPass);
                    csocket.close();
                    break;
                case LOGDIN:
                    boolean flagLog = tradeCenter.loggedIn(m.getString1(),m.getString2());
                    os.writeObject(flagLog);
                    csocket.close();
                    break;

                case SEARCHUSER:
                    ArrayList<Customer> users = tradeCenter.searchUsers(m.getString1());
                    os.writeObject(users);
                    csocket.close();
                    break;

                case SEARCHDESCRIPTION:
                    ArrayList<HashMap<Customer, Collection>> descriptions = tradeCenter.searchByDescription(m.getDescription());
                    os.writeObject(descriptions);
                    csocket.close();
                    break;
                case REMOVEWISH:
                    Customer customer2 = tradeCenter.searchCustomer(m.getString1());
                    tradeCenter.removeFromWishList(m.getDescription(),customer2);
                    csocket.close();
                    break;

            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
