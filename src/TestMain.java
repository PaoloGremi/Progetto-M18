import marketPlace.Description;

import java.io.IOException;

public class TestMain {
    public static void main(String[] args) {

        //----------------Prova Description---------------------
        String urlPic="./dragoOcchiBlu.jpg";
        try {
            Description descr1=new Description("Drago Bianco Occhi Blu","8 stelle di potenza",urlPic);
            descr1.printTag();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //------------------------------------------------------

    }

}
