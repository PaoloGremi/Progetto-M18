package marketPlace;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public class Description {

    /**
     * @param name: Card's name
     * @param pic: Card's picture to be displayed on GUI
     * @param tag: Card's list of tags to be used during search/filter
     * @param description: Card's text description
     * ? @param cardType: Type of card
      */
    


    public String getName() {
        return name;
    }

    public HashSet<String> getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }
}
