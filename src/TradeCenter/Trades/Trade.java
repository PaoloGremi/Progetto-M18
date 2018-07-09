package TradeCenter.Trades;

import TradeCenter.DatabaseProxy.FakeOffer;

import java.util.ArrayList;

public class Trade extends ATrade { //todo change check donedeal method

    /**
     * @param history List of all past offers in the current trade
     * @param tradeCounter Counter of how many offers have been made in the current trade
     * @param doneDeal Boolean value to know when a deal is over, ending the current trade
     */
    private ArrayList<Offer> history = new ArrayList<>();
    private boolean doneDeal;
    private boolean positiveEnd;
    private int id;

    /**
     * Start a trade from an offer
     * @param offer Starting offer
     */
    public Trade(Offer offer, int id) {
        super(offer.getCustomer1(), offer.getCustomer2(), offer.getOffer1(), offer.getOffer2());
        this.history.add(offer);
        this.doneDeal = false;
        this.positiveEnd = false;
    }

    public Trade(FakeOffer fakeOffer) {
        super(fakeOffer.getCustomer1(), fakeOffer.getCustomer2(), fakeOffer.getOffer1(), fakeOffer.getOffer2());
        this.doneDeal = fakeOffer.getDoneDeal();
        super.date = fakeOffer.getDate();
        this.id = fakeOffer.getId();
    }

    /**
     * Method to update the current trade
     * @param offer New offer to update current trade and save into history
     * @return boolean to check wheter or not the method ran fine
     */
    public void update(Offer offer, boolean flag) {
        this.history.add(offer);
        super.updateParameters(offer.getCustomer1(), offer.getCustomer2(), offer.getOffer1(), offer.getOffer2(), offer.date, flag);
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

    //todo add javadocs
    public void doneDeal(boolean result) {
        //todo cosi il metono checkdeal è inutile
        this.doneDeal = true;
        this.positiveEnd = result;
    }

    //todo add javadocs
    public boolean isPositiveEnd() {
        return positiveEnd;
    }
    //todo vedere perchè c'è in offer una cosa simile

    public boolean betweenUsers(String id) {
        if(super.getCustomer1().equals(id) || super.getCustomer2().equals(id)) {
            return true;
        }
        else return false;
    }

    /**
     * Get the trade's id
     * @return trade's id
     */
    public int getId() {
        return id;
    }

    /**
     * Improved printing method
     * @return improved listing of trade properties and offers' history
     */
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append(getCustomer1());
        tmp.append(" - ");
        tmp.append(getCustomer2());
        //tmp.append("\n" + date.toString());
        return tmp.toString();
    }

    public String extensivePrint() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("\nTrade between ");
        tmp.append(getCustomer1());
        tmp.append(" & ");
        tmp.append(getCustomer2());
        tmp.append(" (trade n. ");
        tmp.append(id);
        tmp.append(")");
        tmp.append("\n on the ");
        tmp.append(date.toString());
        tmp.append("\nExchanged ");
        tmp.append(getOffer1().toString());
        tmp.append(" for ");
        tmp.append(getOffer2().toString());
        tmp.append("\n");
        return tmp.toString();
    }
}