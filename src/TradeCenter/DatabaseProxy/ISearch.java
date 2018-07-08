package TradeCenter.DatabaseProxy;

import Interface.searchCard.filterChoice.PokemonAll;
import Interface.searchCard.filterChoice.YuGiOhAll;
import TradeCenter.Card.PokemonDescription;
import TradeCenter.Card.YuGiOhDescription;

import java.util.HashSet;

public interface ISearch {
    /**
     * Search a pokemon card from filter in DB
     *
     * @param pokFilter: To filter
     * @return PokemonDescription matched
     */
    public HashSet<PokemonDescription> getFoundDescrPokemon(PokemonAll pokFilter) ;

    /**
     * Search a YuGiOh card from filter in db
     *
     * @param yugiohFilter: To filter     *
     * @return YuGiOhDescription matched
     */
    public HashSet<YuGiOhDescription> getFoundDescrYugioh(YuGiOhAll yugiohFilter) ;
}
