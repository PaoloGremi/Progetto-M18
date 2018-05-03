package TradeCenter.Trades;

import java.util.ArrayList;


public class Trade extends ATrade {

    /**
     * Trade class
     * @param history List of all past offers in the current trade
     * @param tradeCounter Counter of how many offers have been made in the current trade
     * @param doneDeal Boolean value to know when a deal is over, ending the current trade
     */
    private ArrayList<Offer> history = new ArrayList<>();
    private int tradeCounter;
    private boolean doneDeal;

    /**
     * Start a trade from an offer
     * @param offer Starting offer
     */
    public Trade(Offer offer) {
        super(offer.getCustomer1(), offer.getCustomer2(), offer.getCollection1(), offer.getCollection2());
        this.history.add(offer);
        this.tradeCounter = 1;
        this.doneDeal = false;
    }

    /**
     * Method to update the current trade
     * @param offer New offer to update current trade and save into history
     * @return boolean to check wheter or not the method ran fine
     */
    public void update(Offer offer) {
        this.history.add(offer);
        this.tradeCounter += 1;
        super.updateParameters(offer.getCollection1(), offer.getCollection2(), offer.getDate());
        this.checkDeal(offer);
    }

    /**
     * Method to check if a trade is over
     * @param offer Last offer to compare to previous one
     * @return if last offer has same plate as the previous one, meaning the user accepted the offer
     */
    private void checkDeal(Offer offer) {
        if(history.get(tradeCounter-1).isAcceptedOffer()) {
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

    /**
     * Improved printing method
     * @return improved listing of trade properties and offers' history
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("Offer from ");
        tmp.append(getCustomer1().toString());
        tmp.append(" to ");
        tmp.append(getCustomer2().toString());
        for (int i=0; i<history.size(); i++) {
            tmp.append(history.get(i).toString());
        }

        return tmp.toString();
    }

}
