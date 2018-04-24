package TradeCentre.card;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Description {

    /**
     * @param name: Descr's name
     * @param text: Descr's text description
     * @param picUrl: Url to load descr's picture
     * @param pic: Descr's picture to be displayed on GUI
     * @param listTag: Descr's list of tags to be used during search/filter
     * @param descrType: Descr's type
      */
    private String name;
    private String text; //cambiare UML
    private String picUrl;
    private BufferedImage pic; //formati supportati: GIF,PNG,JPEG,BMP,WBMP
    private HashSet<String> listTag;
    private CardType descrType;

    // Initialize a TradeCentre.card description with the name, a text and a picture of the TradeCentre.card.
    public Description(String name,String text,CardType descrType,String picUrl) throws IOException {
        this.name=name;
        this.text=text;
        this.picUrl=picUrl;
        this.pic=null;
        this.listTag=new HashSet<>();
        loadImage(picUrl);
        autoSplittedTag();
        autoSubstringTag();
    }

    // Load an image by its url.
    private void loadImage(String url) throws IOException {
        File picFile= new File(url);
        pic= ImageIO.read(picFile);
    }

    // Generate a tags splitting text, doesn't consider worlds shorter than two letters (e.g. articles)
    private void autoSplittedTag(){
        String[] textSplitted=text.split(" ");
        int length=textSplitted.length;

        for (int i=0;i<length;i++){
            if(textSplitted[i].length()>2)
            this.addTag(textSplitted[i]);  //aggiungere eccezione
        }
    }

    //Generate tags from all possible combination of substring of text
    private void autoSubstringTag(){
        String[] textSplitted=text.split(" ");
        int length=textSplitted.length;
        for(int i=0;i<length;i++){
            String substring=textSplitted[i];
            for (int j = i+1; j<length-i; j++){
                substring=substring+" "+textSplitted[j];
                this.addTag(substring); //eccezione
            }
        }
    }

    // Print the TradeCentre.card tags.
    public void printTag(){
        for (String tag:
             listTag) {
            System.out.println(tag);
        }
    }
    
    // Add a tag for the TradeCentre.card.
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


    public CardType getDescrType() {
        return descrType;
    }
}
