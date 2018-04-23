package marketPlace;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Description {

    /**
     * @param name: Card's name
     * @param pic: Card's picture to be displayed on GUI
     * @param picUrl: Url to load picture
     * @param listTag: Card's list of tags to be used during search/filter
     * @param text: Card's text description
     * ? @param cardType: Type of card
      */
    private String name;
    private String text; //cambiare UML
    private String picUrl;
    private BufferedImage pic; //formati supportati: GIF,PNG,JPEG,BMP,WBMP
    private HashSet<String> listTag;

    // Initialize a card description with the name, a text anche a picture of the card.
    public Description(String name,String text,String picUrl) throws IOException {
        this.name=name;
        this.text=text;
        this.picUrl=picUrl;
        this.pic=null;
        this.listTag=new HashSet<>();
        loadImage(picUrl);
        autoGenerateTag();
    }

    // Load an image by its url.
    private void loadImage(String url) throws IOException {
        File picFile= new File(url);
        pic= ImageIO.read(picFile);
    }

    // Generate a casual tag for a card.
    private void autoGenerateTag(){
        /* genera tag dal text inserito nel costruttore
         possibili migliorie:
            -selezionare solo certe sottostrighe di text (es. lunghezza min 2 caratteri, per non memorizzare articoli)
            -oltre tutte le sotto stringhe, avere concatenazioni di esse (per migliorare il risultato di ricerca)

        //prova di push
        //blablaba
        
            
         */

        String[] textSplitted=text.split(" ");
        int length=textSplitted.length;
        for (int i=0;i<length;i++){
            this.addTag(textSplitted[i]);  //aggiungere eccezione
        }
    }

    // Print the card tags.
    public void printTag(){
        for (String tag:
             listTag) {
            System.out.println(tag);
        }
    }
    
    // Add a tag for the card.
    public boolean addTag(String tag ){
        String tagLower=tag.toLowerCase(); // tutti tag lowerCase
        return listTag.add(tagLower);
    }

    public String getText() {
        return text;
    }

    public BufferedImage getPic() {
        return pic;
    }


    public String getName() {
        return name;
    }

    public HashSet<String> getListTag() {
        return listTag;
    }


}
