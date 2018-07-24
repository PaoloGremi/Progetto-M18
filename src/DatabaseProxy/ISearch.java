package DatabaseProxy;

import Interface.searchCard.filterChoice.PokemonAll;
import Interface.searchCard.filterChoice.YuGiOhAll;
import TradeCenter.Card.Description;

import java.util.HashSet;

public interface ISearch {
    /**
     * Search a pokemon card from filter in DB
     *
     * @param pokFilter: To filter
     * @return PokemonDescription matched
     */
    HashSet<Description> getFoundDescrPokemon(PokemonAll pokFilter) ;

    /**
     * Search a YuGiOh card from filter in db
     *
     * @param yugiohFilter: To filter     *
     * @return YuGiOhDescription matched
     */
    HashSet<Description> getFoundDescrYugioh(YuGiOhAll yugiohFilter) ;

    /**
     * Search Descriptions by their name (All types of cards)
     * @param s: String to search for Name of card
     * @return: Description that match
     */
    HashSet<Description> getFoundDescrByString(String s);
}
