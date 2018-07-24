package ClientServer;

import javafx.application.Application;

/**
 * Launch the LogIn in the new Thread
 */
class Client extends Thread{

            @Override
            public void run() {
                Application.launch(LogIn.class);
            }

}

/**
 * Create a new client Thread
 */
public class ClientMultiThread{

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Client();
        t1.start();
        t1.join();
    }

}