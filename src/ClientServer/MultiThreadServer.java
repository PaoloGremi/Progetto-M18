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
    Socket csocket;
    static TradeCenter td = new TradeCenter();
    MultiThreadServer(Socket csocket, TradeCenter td) {
        this.csocket = csocket;
        this.td = td;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket ssock = new ServerSocket(8889);
        System.out.println("Listening");

        while (true) {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock, td)).start();
        }
    }
    public void run() throws CheckPasswordConditionsException {
        try {
            ObjectInputStream in = new ObjectInputStream(csocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(csocket.getOutputStream());

            MessageServer m = (MessageServer) in.readObject();

            switch (m.getMessage()){
                case ADDCUSTOMER:
                    td.addCustomer(m.getString1(), m.getString2());
                    Customer c = td.searchCustomer(m.getString1());
                    os.writeObject(c);
                    csocket.close();
                    break;
                case SEARCHCUSTOMER:
                    td.searchCustomer(m.getString1());
                    Customer customer = td.searchCustomer(m.getString1());
                    os.writeObject(customer);
                    csocket.close();
                    break;
                //case CREATEOFFER:
                //case SWITCHCARDS:
                case VERIFYPASSWORD:
                    boolean flagPass = td.verifyPassword(m.getString1(),m.getString2());
                    os.writeObject(flagPass);
                    csocket.close();
                    break;
                case LOGDIN:
                    boolean flagLog = td.loggedIn(m.getString1(),m.getString2());
                    os.writeObject(flagLog);
                    csocket.close();
                    break;

                case SEARCHUSER:
                    ArrayList<Customer> users = td.searchUsers(m.getString1());
                    os.writeObject(users);
                    csocket.close();
                    break;

                case SEARCHDESCRIPTION:
                    ArrayList<HashMap<Customer, Collection>> descriptions = td.searchByDescription(m.getDescription());
                    os.writeObject(descriptions);
                    csocket.close();
                    break;
                case REMOVEWISH:
                    Customer customer2 = td.searchCustomer(m.getString1());
                    td.removeFromWishList(m.getDescription(),customer2);
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
