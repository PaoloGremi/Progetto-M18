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


    public Description(String name,String text,String picUrl) throws IOException {
        this.name=name;
        this.text=text;
        this.picUrl=picUrl;
        this.pic=null;
        loadImage(picUrl);
    }

    private void loadImage(String url) throws IOException {
        File picFile= new File(url);
        pic= ImageIO.read(picFile);
    }

    private void autoGenerateTag(){



    }

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
