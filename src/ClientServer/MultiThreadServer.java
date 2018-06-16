package ClientServer;

import TradeCenter.Card.PokemonDescription;
import TradeCenter.Customers.Collection;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.TradeExceptions.AlreadyStartedTradeException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UsernameAlreadyTakenException;
import TradeCenter.TradeCenter;
import TradeCenter.Trades.Offer;
import TradeCenter.Trades.Trade;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MultiThreadServer implements Runnable {
    private Socket csocket;
    private static TradeCenter tradeCenter = new TradeCenter();
    private ServerProxy proxy;
    Map<MessageType, Method> methodMap = new HashMap<>();
    MultiThreadServer(Socket csocket, TradeCenter tradeCenter) throws NoSuchMethodException {
        this.csocket = csocket;
        this.tradeCenter = tradeCenter;
        proxy = new ServerProxy(tradeCenter);

        methodMap.put(MessageType.VERIFYPASSWORD, ServerProxy.class.getMethod("verifyPassword", MessageServer.class));
        methodMap.put(MessageType.LOGDIN, ServerProxy.class.getMethod("loggedIn", MessageServer.class));
        methodMap.put(MessageType.ADDCUSTOMER, ServerProxy.class.getMethod("addCustomer", MessageServer.class));
        methodMap.put(MessageType.SEARCHCUSTOMER, ServerProxy.class.getMethod("searchCustomer", MessageServer.class));
        methodMap.put(MessageType.POSSIBLETRADE, ServerProxy.class.getMethod("possibleTrade", MessageServer.class));
        methodMap.put(MessageType.CREATEOFFER, ServerProxy.class.getMethod("createTrade", MessageServer.class));
        methodMap.put(MessageType.RAISEOFFER, ServerProxy.class.getMethod("updateOffer", MessageServer.class));
        methodMap.put(MessageType.SEARCHOFFER, ServerProxy.class.getMethod("searchOffer", MessageServer.class));
        methodMap.put(MessageType.SEARCHTRADE, ServerProxy.class.getMethod("searchTrade", MessageServer.class));
        methodMap.put(MessageType.ENDTRADE, ServerProxy.class.getMethod("endTrades", MessageServer.class));
        methodMap.put(MessageType.SEARCHDESCRIPTION, ServerProxy.class.getMethod("searchDescription", MessageServer.class));
        methodMap.put(MessageType.SEARCHUSER, ServerProxy.class.getMethod("searchUsers", MessageServer.class));
        methodMap.put(MessageType.REMOVEWISH, ServerProxy.class.getMethod("removeFromWishList", MessageServer.class));


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
            Object result = returnMessage(m.getMessage(),m);
            os.writeObject(result);
            csocket.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object returnMessage(MessageType tag, MessageServer messageServer) throws InvocationTargetException, IllegalAccessException {
        Object result = null;
        try {
            result = methodMap.get(tag).invoke(proxy ,messageServer);
        }
        catch (CheckPasswordConditionsException | UsernameAlreadyTakenException e){
            result = e;
        }
        catch (AlreadyStartedTradeException e){
            result = false;
        }

        return result;
    }
}
