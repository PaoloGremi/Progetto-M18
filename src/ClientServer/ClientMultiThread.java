package ClientServer;

import javafx.application.Application;

class Client extends Thread{

            @Override
            public void run() {
                Application.launch(LogIn.class);
            }

}

public class ClientMultiThread{

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Client();
        t1.start();
        t1.join();
    }

}