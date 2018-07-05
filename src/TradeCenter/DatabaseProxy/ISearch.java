package TradeCenter.DatabaseProxy;

import Interface.searchCard.filterChoice.PokemonAll;
import TradeCenter.Card.PokemonDescription;

import java.util.HashSet;

public interface ISearch {
    /**
     * Search a pokemon card from filtering in DB
     *
     * @param pokFilter: To filter
     * @return PokemonDescription matched
     */
    public HashSet<PokemonDescription> getFoundedDescrPokemon(PokemonAll pokFilter) ;
}
