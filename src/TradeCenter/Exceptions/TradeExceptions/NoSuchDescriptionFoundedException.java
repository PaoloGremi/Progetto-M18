package TradeCenter.Exceptions.TradeExceptions;

import java.util.NoSuchElementException;

public class NoSuchDescriptionFoundedException extends NoSuchElementException {
    /**
     * Exception handling the case when no description is foundend from Trade.searchDescrInPokemonDb(PokemonAll pokemonAll)
     */
    public NoSuchDescriptionFoundedException() {
        super("No description founded with filter!");
    }
}
