package TradeCenter.Card;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public abstract class Description {

    /**
     * Class Description
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

    // Initialize a TradeCenter.Card description with the name, a text and a picture of the TradeCenter.Card.
    /**
     * Constructor method
     * @param name  name: Descr's name
     * @param text Descr's text description
     * @param descrType: Descr's type
     * @param picUrl: Url to load descr's picture
     * */
    public Description(String name,String text,CardType descrType,String picUrl) throws IOException {
        this.name=name;
        this.text=text;
        this.descrType=descrType;
        this.picUrl=picUrl;
        this.pic=null;
        this.listTag=new HashSet<>();
        loadImage(picUrl);
        autoSplittedTag();
        autoSubstringTag();
    }


    /**
     * Load an image by its url, set pic
     * @param url url of the pic
     * */
    private void loadImage(String url) throws IOException {
        File picFile= new File(url);
        pic= ImageIO.read(picFile);
    }


    /**
     *Generate a tags splitting text, doesn't consider worlds shorter than two letters (e.g. articles)
     *
     * */
    private void autoSplittedTag(){
        String[] textSplitted=text.split(" ");
        int length=textSplitted.length;

        for (int i=0;i<length;i++){
            if(textSplitted[i].length()>2)
            this.addTag(textSplitted[i]);  //aggiungere eccezione
        }
    }

    /**
     *Generate tags from all possible combination of substring of text
     *
     * */
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

    /**
     * Print the TradeCenter.Card tags.
     * */
    public void printTag(){
        for (String tag:
             listTag) {
            System.out.println(tag);
        }
    }
    
    // Add a tag for the TradeCenter.Card.
    /**
     * Add a tag for the TradeCenter.Card.
     * @param tag
     * @return boolean todo: vedi cosa scrivere
     * */
    public boolean addTag(String tag ){
        String tagLower=tag.toLowerCase(); // tutti tag lowerCase
        return listTag.add(tagLower);
    }
    /**
     * Getter for text
     * @return text
     * */

    public String getText() {
        return text;
    }

    /**
     * Getter of pic
     * */
    public BufferedImage getPic() {
        return pic;
    }

    /**
     * Getter of name
     * */
    public String getName() {
        return name;
    }

    /**
     * Getter of listTag
     * */
    public HashSet<String> getListTag() {
        return listTag;
    }

    /**
     * Getter of descrType
     * */
    public CardType getDescrType() {
        return descrType;
    }

    
    @Override
    public boolean equals(Object obj) {
        Description descr1=(Description)obj;
        boolean condition=this.getName().equals(descr1.getName()) && this.getText().equals(descr1.getText()) && this.getDescrType().equals(descr1.getDescrType());
        if(condition){
            return true;
        }
        else return false;
    }

}
