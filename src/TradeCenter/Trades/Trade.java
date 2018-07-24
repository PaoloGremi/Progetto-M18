package TradeCenter.Trades;

import DatabaseProxy.FakeOffer;

/**
 * The actual trade between teo customers
 */

public class Trade extends ATrade {

    /**
     * @param tradeCounter Counter of how many offers have been made in the current trade
     * @param doneDeal Boolean value to know when a deal is over, ending the current trade
     */
    private boolean doneDeal;
    private boolean positiveEnd;
    private int id;

    /**
     * Start a trade from an offer
     * @param offer Starting offer
     */
    public Trade(Offer offer, int id) {
        super(offer.getCustomer1(), offer.getCustomer2(), offer.getOffer1(), offer.getOffer2());
        this.doneDeal = false;
        this.positiveEnd = false;
        this.id = id;
    }

    /**
     * Start a trade from a fake offer (used by dbproxy)
     * @param fakeOffer: resumed offer
     */
    public Trade(FakeOffer fakeOffer) {
        super(fakeOffer.getCustomer1(), fakeOffer.getCustomer2(), fakeOffer.getOffer1(), fakeOffer.getOffer2());
        this.doneDeal = fakeOffer.getDoneDeal();
        super.setDate(fakeOffer.getDate());
        this.positiveEnd = fakeOffer.isPositiveEnd();
        this.id = fakeOffer.getId();
    }

    /**
     * Method to update the current trade
     * @param offer New offer to update current trade and save into history
     * @return boolean to check wheter or not the method ran fine
     */
    public void update(Offer offer, boolean flag) {
        super.updateParameters(offer.getCustomer1(), offer.getCustomer2(), offer.getOffer1(), offer.getOffer2(), offer.getDate(), flag);
    }

    /**
     * Returns wheter or not the trade is over and to be moved to the DoneTrades
     * @return boolean
     */
    public boolean isDoneDeal() {
        return doneDeal;
    }

    /**
     * Set the deal status (done or not done)
     * @param result: deal status
     */
    public void doneDeal(boolean result) {
        this.doneDeal = true;
        this.positiveEnd = result;
    }

    /**
     * Return if the trade had a positive end
     * @return boolean
     */
    public boolean isPositiveEnd() {
        return positiveEnd;
    }

    /**
     * Return if the trade is between a given customer ID
     * @param id
     * @return
     */
    public boolean betweenUsers(String id) {
        return (super.getCustomer1().equals(id) || super.getCustomer2().equals(id));
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
        return getCustomer1() + " - " + getCustomer2() + "\nOn the " + super.getDate().toString() + "\n";
    }



    /**
     * Override of the equals method
     * @param obj a card
     * @return if two cards are equals to each other
     */
    @Override
    public boolean equals(Object obj) {
        if(obj!=null) {
            Trade t = (Trade) obj;
            return this.id == t.id;
        }
        return false;
    }

    /**
     *Method that make the same trade have the same ID
     *
     * @return hashcode
     */

    public int hashCode() {
        int hash = 1;
        hash = hash * this.id;
        return hash;
    }
}
