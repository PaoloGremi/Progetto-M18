import marketPlace.Description;
import marketPlace.TradeCenter;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) {
        TradeCenter tradeCenter = new TradeCenter();

        String urlPic="./UML_dominio.png"; //mettere immagine in locale
        try {
            Description descr1=new Description("Drago Bianco Occhi Blu","8 stelle di potenza",urlPic);
            descr1.printTag();
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
