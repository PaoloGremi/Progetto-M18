package ClientServer;



import TradeCenter.Card.Card;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Customers.Customer;
import TradeCenter.TradeCenter;
import com.mysql.cj.protocol.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer implements Runnable {
    Socket csocket;
    TradeCenter td = new TradeCenter();
    MultiThreadServer(Socket csocket) {
        this.csocket = csocket;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket ssock = new ServerSocket(8080);
        System.out.println("Listening");

        while (true) {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock)).start();
        }
    }
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(csocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(csocket.getOutputStream());

            MessageServer m = (MessageServer) in.readObject();

            switch (m.getMessage()){
                case ADDCUSTOMER:
                    td.addCustomer(m.getString1(),m.getString2());
                    Customer c = td.searchCustomer(m.getString1());
                    os.writeObject(c);
                    break;
                case SEARCHCUSTOMER:
                    td.searchCustomer(m.getString1());
                    Customer customer = td.searchCustomer(m.getString1());
                    os.writeObject(customer);
                    break;
                //case CREATEOFFER:
                //case SWITCHCARDS:
                case VERIFYPASSWORD:
                    boolean flagPass = td.verifyPassword(m.getString1(),m.getString2());
                    os.writeObject(flagPass);
                    break;
                case LOGDIN:
                    boolean flagLog = td.loggedIn(m.getString1(),m.getString2());
                    os.writeObject(flagLog);
                    break;

            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
