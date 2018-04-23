package marketPlace;

import java.util.ArrayList;

public class Trade extends ATrade {
    /**
     * @param history: List of all past offers in the current trade
     * @param tradeCounter: Counter of how many offers have been made in the current trade
     * @param doneDeal: Boolean value to know when a deal is over, ending the current trade
     */
    private ArrayList<Offer> history;
    private int tradeCounter;
    private boolean doneDeal;

    // Start a trade from an offer.
    protected Trade(Offer offer) {
        super(offer.getUser1(), offer.getUser2(), offer.getColl1(), offer.getColl2());
        this.history.add(offer);
        this.tradeCounter = 1;
        this.doneDeal = false;
    }

    // Method to update the current trade.
    protected boolean update(Offer offer) {
        this.history.add(offer);
        this.tradeCounter += 1;
        super.setColl1(offer.getColl1());
        super.setColl2(offer.getColl2());
        super.setDate(offer.getDate());
        return true;
    }

    // Method to check if a trade is over.
    protected boolean checkDeal(Offer offer) {
        if(history.get(tradeCounter).getColl1().equals(offer.getColl1()) && history.get(tradeCounter).getColl2().equals(offer.getColl2())) {
            this.doneDeal = true;
        }
        return doneDeal;
    }
    
    // Improved printing method.
    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("Offer from ");
        tmp.append(getUser1().toString());
        tmp.append(" to ");
        tmp.append(getUser2().toString());
        for (int i=0; i<history.size(); i++) {
            tmp.append(history.get(i).toString());
        }

        return tmp.toString();
    }

}
