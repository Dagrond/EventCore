package com.gmail.ZiomuuSs.Utils;

import org.bukkit.ChatColor;

public enum msg {
  ERROR_PERMISSION("&5[&6Event&5] &cNie posiadasz odpowiednich uprawnieñ aby u¿yæ tej komendy!"),
  ERROR_CANNOT_DO_THAT_HERE("&5[&6Event&5] &cNie mo¿esz tego tutaj zrobiæ."),
  ERROR_ITEM_IN_MAIN_HAND_AIR("&5[&6Event&5] &cNie trzymasz ¿adnego przedmiotu w rêce!"),
  ERROR_NOT_ENOUGH_SPACE_IN_INVENTORY("&5[&6Event&5] &cTwój ekwipunek jest pe³ny!"),
  ERROR_USAGE("&5[&6Event&5] &cU¿ycie: &c&l%1"),
  ERROR_MUST_BE_PLAYER("&5[&6Event&5] &cNie mo¿na tego dokonaæ z poziomu konsoli!"),
  ERROR_EVENT_TYPE_NOT_EXIST("&5[&6Event&5] &cEvent typu &c&l%1&r&c nie istnieje! Listê wszystkich typów sprawdzisz pod &c&l/ce types&r&c."),
  ERROR_EVENT_NOT_EXIST("&5[&6Event&5] &cEvent typu &c&l%1&r&c o nazwie &c&l%2&r&c nie istnieje! SprawdŸ listê dostêpnych eventów danego typu pod &c&l/e list (typ)&r&c."),
	ERROR_ALREADY_IN_LOBBY("&5[&6Event&5] &cJesteœ ju¿ w lobby! Aby wyjœæ, wpisz &c&l/e wyjdz&r&c."),
	ERROR_ALREADY_IN_EVENT("&5[&6Event&5] &cJesteœ ju¿ na evencie! Aby wyjœæ, wpisz &c&l/e wyjdz&r&c lub &c&l/e lobby&r&c."),
	ERROR_COMBAT("&5[&6Event&5] &cNie mo¿esz do³¹czyæ do lobby, poniewa¿ jesteœ w trakcie walki!"),
	ERROR_LOBBY_COMMAND("&5[&6Event&5] &cNie mo¿esz u¿yæ tej komendy bêd¹c w lobby!"),
	ERROR_ALREADY_IN_PROGRESS("&5[&6Event&5] &cNie mo¿na wystartowaæ eventu, poniewa¿ jakiœ ju¿ trwa!"),
	ERROR_NOT_IN_LOBBY("&5[&6Event&5] &cNie jesteœ w lobby! Aby do³¹czyæ do lobby, wpisz &c&l/e lobby&r&c."),
	ERROR_LOAD_NOT_CONFIGURED("&5[&6Event&5] &cNie mo¿na za³adowaæ eventu &c&l%1 &r&ctypu &c&l%2&r&c: Brakuj¹ca opcja: &c&l%3"),
	ERROR_LOAD_LOBBY_NOT_SET("&5[&6Event&5] &cNie za³adowano punktu lobby! Ustaw punkt spawnu lobby za pomoc¹ &c&l/ce lobbyspawn&r&c!"),
	ERROR_LOAD_WORLD_NOT_EXIST("&5[&6Event&5] &cNie mo¿na za³adowaæ eventu &c&l%1 &r&ctypu &c&l%2&r&c: Œwiat &c&l%3 &r&cnie istnieje!"),
	ERROR_LOAD_NO_REGIONS("&5[&6Event&5] &cNie mo¿na za³adowaæ eventu &c&l%1 &r&ctypu &c&l%2&r&c: nie znaleziono ¿adnych regionów!"),
	ERROR_LOAD_NO_STARTPOINTS("&5[&6Event&5] &cNie mo¿na za³adowaæ eventu &c&l%1 &r&ctypu &c&l%2&r&c: nie znaleziono ¿adnych punktów startowych!"),
	ERROR_LOAD_NO_SUCH_TEAMOPTION("&5[&6Event&5] &cNie mo¿na za³adowaæ opcji &c&l\"%1\"&r&c w evencie &c&l%2 &r&ctypu &c&l%3&r&c: ta opcja mo¿e przyjmowaæ tylko wartoœci: &c&lALWAYS&r&c, &c&lNEVER&r&c, &c&lFOR_OTHER_TEAMS&r&coraz &c&lFOR_OWN_TEAM&r&c. Pomijam..."),
	ERROR_LOAD_MATERIAL_NOT_EXIST("&5[&6Event&5] &cNie mo¿na za³adowaæ eventu &c&l%1 &r&ctypu &c&l%2&r&c: Materia³ &c&l%3 &r&cnie istnieje!"),
	ERROR_LOAD_MATERIAL_NOT_SOLID("&5[&6Event&5] &cNie mo¿na za³adowaæ eventu &c&l%1 &r&ctypu &c&l%2&r&c: Materia³ &c&l%3 &r&cnie jest solidny!"),
	ERROR_LOAD_BAD_STARTPOINT("&5[&6Event&5] &cNie mo¿na za³adowaæ punktu startowego &c&l\"%1\"&r&c w evencie &c&l%2 &r&ctypu &c&l%3&r&c: lokacja musi siê sk³adaæ z 3 koordynatów: &c&lx&r&c, &c&ly &r&coraz &c&lz&r&c. Pomijam..."),
	ERROR_LOAD_PLUGIN_NOT_FOUND("&5[&6Event&5] &cNie znaleziono pluginu &c&l%1&r&c! Czêœæ funkcji nie bêdzie dzia³aæ."),
	ERROR_LOAD_CANNOT_LOAD_EVENT_NO_PLUGIN_FOUND("&5[&6Event&5] &cNie mo¿na za³adowaæ eventów typu &c&l%1&4&c, poniewa¿ nie znaleziono wymaganego pluginu(ów): %2"),
	ERROR_LOAD_REGION_NOT_EXIST("&5[&6Event&5] &cNie mo¿na za³adowaæ regionu &c&l%1 &r&cw œwiecie &c&l%2&r&cdla eventu &c&l%3&r&c typu &c&l%4&r&c, pomijam..."),
	ERROR_STARTING_CANCELLED("&5[&6Event&5] &cEvent &c&l%1&r&c zosta³ przerwany!"),
	ERROR_EVENT_NOT_IN_EVENT_OR_LOBBY("&5[&6Event&5] &cNie znajdujesz siê ani w lobby, ani w evencie!"),
	ERROR_WORLDGUARD_API_NOT_RESPONDING("&5[&6Event&5] &cEvent &c&l%1&r&c typu &c&l%2 &r&cnie zosta³ za³adowany: API WorldGuarda nie odpowiada!"),
	ERROR_STARTING_NOT_ENOUGH_PLAYERS("&5[&6Event&5] &cEvent &c&l%1&r&c nie rozpocznie siê, poniewa¿ w lobby znajduje siê zbyt ma³a iloœæ graczy :( (&c&l%2&r&c/&c&l%3&r&c)"),
	ERROR_CANNOT_LOAD_ACTION_MISSING("&5[&6Event&5] &cNie mo¿na za³adowaæ akcji &c&l\"%1\n&r&c w evencie &c&l%2&r&c: Nie mo¿na znalezæ atrybutu &c&l%3&r&c!"),
	ERROR_CANNOT_LOAD_ACTION_INVALID_TYPE("&5[&6Event&5] &cNie mo¿na za³adowaæ akcji &c&l\"%1\n&r&c w evencie &c&l%2&r&c: Nieprawid³owy typ akcji: &c&l%3&r&c!"),
	ERROR_CANNOT_LOAD_ACTION_INVALID_POTION_EFFECT("&5[&6Event&5] &cNie mo¿na za³adowaæ akcji &c&l\"%1\n&r&c\" w evencie &c&l%2&r&c: Nieprawid³owy efekt mikstury: &c&l%3&r&c!"),
	ERROR_CANNOT_LOAD_ACTION_INVALID_ITEM("&5[&6Event&5] &cNie mo¿na za³adowaæ akcji &c&l\"%1\"&r&c w evencie &c&l%2&r&c: Przedmiot &c&l\"%3\"&r&c nie istnieje!"),
	ERROR_CANNOT_LOAD_ACTION_MUST_BE_POSITIVE("&5[&6Event&5] &cNie mo¿na za³adowaæ akcji &c&l\"%1\"&r&c w evencie &c&l%2&r&c: Wartoœæ pola &c&l\"%3\"&r&c musi byæ wiêksza od 0!"),
	COUTDOWN("&5[&6Event&5] &a&l%1 &r&arozpocznie siê za %2 sekund!"),
	LOADED("&5[&6Event&5] &aZa³adowano &a&l%1 &r&aevent(ów)! W tym: &a&l%2"),
	LOADED_LOBBY("&5[&6Event&5] &aPomyœlnie za³adowano punkt spawnu lobby!"),
	INFO_TIP("&5[&6Event&5] &aNie wiesz na czym polega ten event? Wpisz &a&l/e info&r&a."),
	LOBBY_JOINED("&5[&6Event&5] &aPomyœlnie do³¹czy³eœ do lobby."),
  LOBBY_LEAVE("&5[&6Event&5] &aPomyœlnie opuœci³eœ lobby."),
  LOBBY_INVITE("&5[&6Event&5] &aZa &a&l%1 &r&asekund rozpoczyna siê event! Wpisz &a&l/e lobby &r&aaby do niego do³¹czyæ."),
  LOBBY_LOCATION_SET("&5[&6Event&5] &aPomyœlnie ustawi³eœ punkt spawnu lobby na Twojej pozycji!"),
  LOBBY_MESSAGE_HEADER("&a~~~~~~~~~&a&lLobby&r&a~~~~~~~~~"),
  LOBBY_MESSAGE_CURRENT("&aObecnie trwa event: &a&l%1&r&a (&a&l%2&r&a)"),
  LOBBY_MESSAGE_STARTING("&aZa chwilê rozpocznie siê event: &a&l%1&r&a (&a&l%2&r&a)"),
  LOBBY_MESSAGE_NO_EVENT("&aObecnie nie odbywa siê ¿aden event."),
  LOBBY_MESSAGE_PLAYERS_IN_EVENT("&aNa evencie znajduje siê &a&l%1&r&a graczy."),
  LOBBY_MESSAGE_PLAYERS_IN_LOBBY("&aW lobby znajduje siê &a&l%1&r&a graczy."),
  LOBBY_MESSAGE_TIME("&aEvent trwa ju¿ &a&l%1&r&a."),
  LOBBY_MESSAGE_START_IN("&aEvent zacznie siê za &a&l%1&r&a."),
  LOBBY_MESSAGE_SPECTATE_INVITE("&aWpisz &a&l/e spec&r&a aby obejrzeæ event."),
  LOBBY_MESSAGE_INFO_TIP("&aWpisz &a&l/e info&r&a aby dowiedzieæ siê wiêcej na temat zasad tego eventu."),
  LOBBY_MESSAGE_REWARD_TIP("&aWpisz &a&l/e nagroda&r&a aby zobaczyæ nagrody za ten event."),
  BACK_TO_LOBBY("&5[&6Event&5] Pomyœlnie powróci³eœ do lobby."),
  EVENT_LEAVE("&5[&6Event&5] &aPomyœlnie opuœci³eœ event."),
  REWARD_INFO("&5[&6Event&5] &aWpisz &l&a/e nagrody &r&aaby wyœwietliæ nagrody za wygranie tego eventu"),
  REWARD_ADDED("&5[&6Event&5] &aOtrzyma³eœ nagrodê za udzia³ w evencie! Odbierz j¹ wpisuj¹c &a&l/e nagroda&r&a."),
  REWARD_NOTIFICATION("&5[&6Event&5] &aMasz nieodebrane nagrody! Odbierz je wpisuj¹c &a&l/e nagroda&r&a. Nieodebrane nagrody usuwaj¹ siê po 3 dniach."),
  REWARD_OPENED("&5[&6Event&5] &aOtworzono menu odbierania nagród."),
  REWARD_NO_REWARDS("&5[&6Event&5] &cNie posiadasz ¿adnych nagród do odebrania!"),
  REWARD_CLAIMED("&5[&6Event&5] &aDziêkujemy za odebranie wszystkich nagród!"),
  REWARD_NO_SPACE("&5[&6Event&5] &cNagroda przepad³a, poniewa¿ skrytka z Twoimi nagrodami jest pe³na! Odbierz nagrody wpisuj¹c &c&l/e nagroda&r&c."),
  REWARD_NOT_ALL_CLAIMED("&5[&6Event&5] &cUWAGA! Nie odebra³eœ wszystkich nagród. Nieodebrane nagrody s¹ usuwane po 3 dniach od ich otrzymania."),
  REWARD_NOT_IN_LOBBY("&5[&6Event&5] &cAby odebraæ nagrody, musisz najpierw opuœciæ lobby komend¹ &c&l/e wyjdz&r&c."),
  REWARD_NOT_IN_EVENT("&5[&6Event&5] &cAby odebraæ nagrody, musisz najpierw opuœciæ event komend¹ &c&l/e wyjdz&r&c."),
  EVENT_SPLEEF_START_WARNING("&5[&6Event&5] &aNiszczenie bloków zostanie w³¹czone za &a&l%1&r&a sekund! Przygotujcie siê!"),
  EVENT_SPLEEF_START("&5[&6Event&5] &aNiszczenie bloków zosta³o w³¹czone! Powodzenia!"),
  EVENT_WIN("&5[&6Event&5] &aEvent &a&l%1&r&a wygra³ gracz &a&l%2&r&a! Gratulacje!"),
  EVENT_QUIT("&5[&6Event&5] &aGracz &a&l%1&r&a wyszed³ z eventu! Pozosta³o &a&l%2&r&a graczy!"),
  EVENT_STARTING_SOON("&aWpisz &a&l/e lobby&r&a aby do³¹czyæ do eventu (&a&l%1&r&a)."),
  EVENT_PLAYER_LEAVED("&5[&6Event&5] &aGracz &a&l%1 &r&aopuœci³ event! Pozosta³o &a&l%2&r&a graczy."),
  NONE("Brak"),
  ITEM_LIST("&5[&6Event&5] &aLista zapisanych przedmiotów: &a&l%1"),
  ITEM_REMOVED("&5[&6Event&5] &aPomyœlnie usuniêto przedmiot &a&l%1&r&a!"),
  ITEM_NOT_EXIST("&5[&6Event&5] &cPrzedmiot o nazwie &c&l%1 &r&anie istnieje! Wpisz &c&l/ce item list &r&caby wyœwietliæ listê zapisanych ekwipunków"),
  ITEM_SAVED("&5[&6Event&5] &aPomyœlnie zapisano przedmiot o nazwie &a&l%1&r&a!"),
  ITEM_LOADED("&5[&6Event&5] &aPomyœlnie za³adowano przedmiot o nazwie &a&l%1&r&a!"),
  ITEM_ALREADY_EXIST("&5[&6Event&5] &cPrzedmiot o nazwie &c&l%1 &r&cju¿ istnieje! Wpisz &c&l/ce item remove %1 &r&caby wyœwietliæ listê zapisanych ekwipunków"),
  INVENTORY_LIST("&5[&6Event&5] &aLista zapisanych ekwipunków: &a&l%1"),
  INVENTORY_REMOVED("&5[&6Event&5] &aPomyœlnie usuniêto ekwipunek &a&l%1&r&a!"),
  INVENTORY_NOT_EXIST("&5[&6Event&5] &cEkwipunek o nazwie &c&l%1 &r&anie istnieje! Wpisz &c&l/ce inv list &r&caby wyœwietliæ listê zapisanych ekwipunków"),
  INVENTORY_SAVED("&5[&6Event&5] &aPomyœlnie zapisano ekwipunek o nazwie &a&l%1&r&a!"),
  INVENTORY_LOADED("&5[&6Event&5] &aPomyœlnie za³adowano ekwipunek o nazwie &a&l%1&r&a!"),
  INVENTORY_ALREADY_EXIST("&5[&6Event&5] &cEkwipunek o nazwie &c&l%1 &r&cju¿ istnieje! Wpisz &c&l/ce inv remove %1 &r&caby wyœwietliæ listê zapisanych ekwipunków"),
  STARTED("&5[&6Event&5] &aPomyœlnie zacz¹³eœ event &a&l%1 &r&atypu &a&l%2&r&a!");
  
  private String message;
  msg(String message) {
    this.message = message;
  }
  
  public String get(String...args) {
  	String temp = message;
    for (int i = 0; i<args.length; i++) {
      temp = temp.replaceAll("%"+(i+1), args[i]);
    }
  return ChatColor.translateAlternateColorCodes('&', temp);
  }
}

