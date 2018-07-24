## Test di Unità
I Test di Unità vengono eseguiti sulle classi e sottosistemi:

* `src/TradeCenter/TradeCenter.java`
* `src/TradeCenter/Customers/Customer.java`
* `src/TradeCenter/DatabseProxy/DBProxy.java`
* `src/TradeCenter/DatabseProxy/DBSearchDescription.java`

Si è scelto di eseguire dei test sul sistema di persistenza (utilizzando un DB di prova), in quanto parte importante dell'intero sistema.

### Creazione DB per i test
* eseguire lo script `./create_schema_and_tables_TEST.sql`
* eseguire lo script `./dump_all_tables_TEST.sql`
* eseguire lo script `./create_user_TEST.sql`

### Credenziali DB test:

DATABASE = "test_cards"

USERNAME = "tradecenter_test"
 
PASSWORD = "Password1!"

Devono essere configurati nella classe `src/TradeCenter/DatabaseProxy/DBConnectionManager.java`
### Scenario preesistente nei Test e DB:
2 Customers

8 carte di Pokèmon 

8 carte di Yu-Gi-Oh!
