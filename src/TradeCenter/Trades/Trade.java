package TradeCenter.Trades;

import java.util.ArrayList;

/**
 * Trade class
 * @author Roberto Gallotta
 */
public class Trade extends ATrade {

    /**
     * @param history List of all past offers in the current trade
     * @param tradeCounter Counter of how many offers have been made in the current trade
     * @param doneDeal Boolean value to know when a deal is over, ending the current trade
     */
    private ArrayList<Offer> history = new ArrayList<>();
    private boolean doneDeal;

    /**
     * Start a trade from an offer
     * @param offer Starting offer
     */
    public Trade(Offer offer) {
        super(offer.getCustomer1(), offer.getCustomer2(), offer.getOffer1(), offer.getOffer2());
        this.history.add(offer);
        this.doneDeal = false;
    }

    /**
     * Method to update the current trade
     * @param offer New offer to update current trade and save into history
     * @return boolean to check wheter or not the method ran fine
     */
    public void update(Offer offer) {
        this.history.add(offer);
        super.updateParameters(offer.getOffer1(), offer.getOffer2(), offer.getDate());
        this.checkDeal(offer);
    }

    /**
     * Method to check if a trade is over
     * @param offer Last offer to compare to previous one
     * @return if last offer has same plate as the previous one, meaning the user accepted the offer
     */
    private void checkDeal(Offer offer) {
        if(history.get(history.size()-1).isAcceptedOffer()) {
            this.doneDeal = true;
        }
    }

    /**
     * Returns wheter or not the trade is over and to be moved to the DoneTrades
     * @return boolean
     */
    public boolean isDoneDeal() {
        return doneDeal;
    }

    public boolean betweenUsers(String username) {
        if(super.getCustomer1().getUsername().equals(username) || super.getCustomer2().getUsername().equals(username)) {
            return true;
        }
        else return false;
    }

    /**
     * Improved printing method
     * @return improved listing of trade properties and offers' history
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        /*
        tmp.append("Offer from ");
        tmp.append(getCustomer1().getUsername());
        tmp.append(" to ");
        tmp.append(getCustomer2().getUsername());
        for (int i=0; i<history.size(); i++) {
            tmp.append(history.get(i).toString());
        }

        */

        tmp.append(getCustomer1().getUsername());
        tmp.append(" - ");
        tmp.append(getCustomer2().getUsername());
        return tmp.toString();
    }

}
