package ClientServer;

import TradeCenter.Exceptions.TradeExceptions.AlreadyStartedTradeException;
import TradeCenter.Exceptions.UserExceptions.AlreadyLoggedInException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UsernameAlreadyTakenException;
import TradeCenter.TradeCenter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * Starts the server with a static tradecenter
 */
public class MultiThreadServer implements Runnable {
    private Socket csocket;
    private static TradeCenter tradeCenter = TradeCenter.getInstance();
    private ServerProxy proxy;
    private Map<MessageType, Method> methodMap = new HashMap<>();

    MultiThreadServer(Socket csocket, TradeCenter tradeCenter) throws NoSuchMethodException, SocketException {
        this.csocket = csocket;
        csocket.setTcpNoDelay(true);
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
        methodMap.put(MessageType.ADDCARDPOKEMON, ServerProxy.class.getMethod("addPokemonRandom", MessageServer.class));
        methodMap.put(MessageType.ADDCARDYUGI, ServerProxy.class.getMethod("addYuGiOhRandom", MessageServer.class));
        methodMap.put(MessageType.SEARCHUSERBYID, ServerProxy.class.getMethod("searchCustomerByID", MessageServer.class));
        methodMap.put(MessageType.REMOVETRADE, ServerProxy.class.getMethod("removeTrade", MessageServer.class));
        methodMap.put(MessageType.SEARCHUSERNAME, ServerProxy.class.getMethod("searchUsernameById", MessageServer.class));
        methodMap.put(MessageType.FILTERPOKEMONDESCR,ServerProxy.class.getMethod("filterPokemonDescr", MessageServer.class));
        methodMap.put(MessageType.FILTERYUGIOHDESCR,ServerProxy.class.getMethod("filterYugiohDescr", MessageServer.class));
        methodMap.put(MessageType.ADDDESCRTOWHISLIST,ServerProxy.class.getMethod("addDescrToWhishlist", MessageServer.class));
        methodMap.put(MessageType.SEARCHDESCRSBYTRING,ServerProxy.class.getMethod("searchDescrByString", MessageServer.class));
        methodMap.put(MessageType.ALREADYLOGGED,ServerProxy.class.getMethod("isLoggedIn", MessageServer.class));
        methodMap.put(MessageType.LOGOUT,ServerProxy.class.getMethod("logOut", MessageServer.class));

    }

    /**
     * Create the socket for the server and starts it
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        ServerSocket ssock = new ServerSocket(ServerIP.port);
        System.out.println("Listening");

        while (true) {
            Socket sock = ssock.accept();
            System.out.println("Connected");
            new Thread(new MultiThreadServer(sock, tradeCenter)).start();
        }
    }

    /**
     * Receive the message from the client and send a message back
     * @throws CheckPasswordConditionsException
     * @throws UsernameAlreadyTakenException
     */
    public void run() throws CheckPasswordConditionsException, UsernameAlreadyTakenException, AlreadyLoggedInException {
        try {
            ObjectInputStream in = new ObjectInputStream(csocket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(csocket.getOutputStream());
            MessageServer m = (MessageServer) in.readObject();
            Object result = returnMessage(m.getMessage(),m);
            os.writeObject(result);
            os.flush();
            csocket.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elaborates the message from the client
     * @param tag Type of the message to identify the method to use in rhe server proxy
     * @param messageServer The message from the client
     * @return The message in respond from the tradecenter
     * @throws IllegalAccessException
     * @throws CheckPasswordConditionsException
     */
    private Object returnMessage(MessageType tag, MessageServer messageServer) throws IllegalAccessException, CheckPasswordConditionsException, AlreadyLoggedInException {
        Object result;
        try {
            result = methodMap.get(tag).invoke(proxy ,messageServer);
        }
        catch (InvocationTargetException e){
            if(e.getCause() instanceof AlreadyStartedTradeException){
                result = false;
            }else {
                result = e.getCause();
            }
        }


        return result;
    }
}
