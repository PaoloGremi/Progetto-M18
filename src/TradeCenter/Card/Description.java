package TradeCenter.Card;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashSet;

/**
 * Abstract class that represents the parameters for an entire collection
 */

public abstract class Description implements Serializable {

    private static final long serialVersionUID = -7217318823889023675L;

    /**
     * Class Description
     * @param name: Descr's name
     * @param text: Descr's text description
     * @param pic: Descr's picture to be displayed on GUI
     * @param listTag: Descr's list of tags to be used during search/filter
     * @param descrType: Descr's type
      */
    private String name;
    private String text;
    transient private BufferedImage pic; //supported formats: GIF,PNG,JPEG,BMP,WBMP
    private HashSet<String> listTag;
    private CardType descrType;

    /**
     * Constructor method
     * @param name  name: Descr's name
     * @param text Descr's text description
     * @param descrType: Descr's type
     * @param pic: descr's picture
     * */
    public Description(String name,String text,CardType descrType,BufferedImage pic) {
        this.name=name;
        this.text=text;
        this.descrType=descrType;
        this.pic=pic;
        this.listTag=new HashSet<>();
        autoSplittedTag();
        autoSubstringTag();
    }

    /**
     *Generate a tags splitting text, doesn't consider worlds shorter than two letters (e.g. articles)
     *
     * */
    private void autoSplittedTag(){
        String[] textSplitted=text.split(" ");
        for (int i=0; i<textSplitted.length; i++){
            if(textSplitted[i].length()>2)
                this.addTag(textSplitted[i]);
        }
    }

    /**
     *Generate tags from all possible combination of substring of text
     *
     * */
    private void autoSubstringTag(){
        String[] textSplitted=text.split(" ");
        for(int i=0;i<textSplitted.length;i++){
            String substring=textSplitted[i];
            for (int j = i+1; j<textSplitted.length-i; j++){
                substring=substring+" "+textSplitted[j];
                this.addTag(substring);
            }
        }
    }

    /**
     * Add a tag for the card.
     * @param tag the tag that will be added
     * @return boolean if the operatinons is done correctly
     * */
    private boolean addTag(String tag){
        return listTag.add(tag.toLowerCase()); //all tags are lowercase
    }

    /**
     * Getter of the name
     *
     * @return the name of the card
     * */
    public String getName() {
        return name;
    }

    /**
     * Getter of the text
     *
     * @return the description of the card
     * */
    public String getText() {
        return text;
    }

    /**
     * Getter of the image
     *
     * @return the image
     * */
    public BufferedImage getPic() {
        return pic;
    }

    /**
     * Getter of the list of the card's tag
     *
     * @return the list of the card's tags
     * */
    public HashSet<String> getListTag() {
        return listTag;
    }

    /**
     *Getter of the description
     *
     * @return the type of the card
     */
    public CardType getDescrType() {
        return descrType;
    }

    /**
     *temporary method for serializable
     *
     * @param output
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeInt(1);
        ImageIO.write(pic, "jpg", output);
    }

    /**
     *
     *temporary method for serializable
     * @param input
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        final int imageCount = input.readInt();     //DONT DELETE, used in reading correctly
        pic = ImageIO.read(input);
    }

    /**
     *
     * @return the name of the card
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     *
     * @param obj the object to check equality
     * @return if the object are equals
     */
    @Override
    public boolean equals(Object obj) {
        Description descr=(Description)obj;
        if(this.getName().equals(descr.getName()) && this.getText().equals(descr.getText()) && this.getDescrType().equals(descr.getDescrType())){
            return true;
        }
        else return false;
    }


}
