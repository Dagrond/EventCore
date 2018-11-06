package com.gmail.ZiomuuSs.Utils;

import org.bukkit.ChatColor;

public enum msg {
  ERROR_PERMISSION("&5[&6Event&5] &cNie posiadasz odpowiednich uprawnie� aby u�y� tej komendy!"),
  ERROR_CANNOT_DO_THAT_HERE("&5[&6Event&5] &cNie mo�esz tego tutaj zrobi�."),
  ERROR_USAGE("&5[&6Event&5] &cU�ycie: &c&l%1"),
  ERROR_MUST_BE_PLAYER("&5[&6Event&5] &cNie mo�na tego dokona� z poziomu konsoli!"),
  ERROR_EVENT_TYPE_NOT_EXIST("&5[&6Event&5] &cEvent typu &c&l%1&r&c nie istnieje! List� wszystkich typ�w sprawdzisz pod &c&l/ce types&r&c."),
  ERROR_EVENT_NOT_EXIST("&5[&6Event&5] &cEvent typu &c&l%1&r&c o nazwie &c&l%2&r&c nie istnieje! Sprawd� list� dost�pnych event�w danego typu pod &c&l/e list (typ)&r&c."),
	ERROR_ALREADY_IN_LOBBY("&5[&6Event&5] &cJeste� ju� w lobby! Aby wyj��, wpisz &c&l/e wyjdz&r&c."),
	ERROR_ALREADY_IN_EVENT("&5[&6Event&5] &cJeste� ju� na evencie! Aby wyj��, wpisz &c&l/e wyjdz&r&c lub &c&l/e lobby&r&c."),
	ERROR_COMBAT("&5[&6Event&5] &cNie mo�esz do��czy� do lobby event�w, poniewa� jeste� w trakcie walki!"),
	ERROR_ALREADY_IN_PROGRESS("&5[&6Event&5] &cNie mo�na wystartowa� eventu, poniewa� jaki� ju� trwa!"),
	ERROR_NOT_IN_LOBBY("&5[&6Event&5] &cNie jeste� w lobby! Aby do��czy� do lobby, wpisz &c&l/e lobby&r&c."),
	ERROR_LOAD_NOT_CONFIGURED("&5[&6Event&5] &cNie mo�na za�adowa� eventu &c&l%1 &r&ctypu &c&l%2&r&c: Brakuj�ca opcja: &c&l%3"),
	ERROR_LOAD_WORLD_NOT_EXIST("&5[&6Event&5] &cNie mo�na za�adowa� eventu &c&l%1 &r&ctypu &c&l%2&r&c: �wiat &c&l%3 &r&cnie istnieje!"),
	ERROR_LOAD_NO_REGIONS("&5[&6Event&5] &cNie mo�na za�adowa� eventu &c&l%1 &r&ctypu &c&l%2&r&c: nie znaleziono �adnych region�w!"),
	ERROR_LOAD_NO_STARTPOINTS("&5[&6Event&5] &cNie mo�na za�adowa� eventu &c&l%1 &r&ctypu &c&l%2&r&c: nie znaleziono �adnych punkt�w startowych!"),
	ERROR_LOAD_NO_SUCH_TEAMOPTION("&5[&6Event&5] &cNie mo�na za�adowa� opcji &c&l\"%1\"&r&c w evencie &c&l%2 &r&ctypu &c&l%3&r&c: ta opcja mo�e przyjmowa� tylko warto�ci: &c&lALWAYS&r&c, &c&lNEVER&r&c, &c&lFOR_OTHER_TEAMS&r&coraz &c&lFOR_OWN_TEAM&r&c. Pomijam..."),
	ERROR_LOAD_MATERIAL_NOT_EXIST("&5[&6Event&5] &cNie mo�na za�adowa� eventu &c&l%1 &r&ctypu &c&l%2&r&c: Materia� &c&l%3 &r&cnie istnieje!"),
	ERROR_LOAD_MATERIAL_NOT_SOLID("&5[&6Event&5] &cNie mo�na za�adowa� eventu &c&l%1 &r&ctypu &c&l%2&r&c: Materia� &c&l%3 &r&cnie jest solidny!"),
	ERROR_LOAD_BAD_STARTPOINT("&5[&6Event&5] &cNie mo�na za�adowa� punktu startowego &c&l\"%1\"&r&c w evencie &c&l%2 &r&ctypu &c&l%3&r&c: lokacja musi si� sk�ada� z 3 koordynat�w: &c&lx&r&c, &c&ly &r&coraz &c&lz&r&c. Pomijam..."),
	ERROR_LOAD_PLUGIN_NOT_FOUND("&5[&6Event&5] &cNie znaleziono pluginu &c&l%1&r&c! Cz�� funkcji nie b�dzie dzia�a�."),
	ERROR_LOAD_CANNOT_LOAD_EVENT_NO_PLUGIN_FOUND("&5[&6Event&5] &cNie mo�na za�adowa� event�w typu &c&l%1&4&c, poniewa� nie znaleziono wymaganego pluginu(�w): %2"),
	ERROR_LOAD_REGION_NOT_EXIST("&5[&6Event&5] &cNie mo�na za�adowa� regionu &c&l%1 &r&cw �wiecie &c&l%2&r&cdla eventu &c&l%3&r&c typu &c&l%4&r&c, pomijam..."),
	ERROR_STARTING_CANCELLED("&5[&6Event&5] &cEvent &c&l%1&r&c zosta� przerwany!"),
	ERROR_WORLDGUARD_API_NOT_RESPONDING("&5[&6Event&5] &cEvent &c&l%1&r&c typu &c&l%2 &r&cnie zosta� za�adowany: API WorldGuarda nie odpowiada!"),
	ERROR_STARTING_NOT_ENOUGH_PLAYERS("&5[&6Event&5] &cEvent &c&l%1&r&c nie rozpocznie si�, poniewa� w lobby znajduje si� zbyt ma�a ilo�� graczy :( (&c&l%2&r&c/&c&l%3&r&c)"),
	COUTDOWN("&5[&6Event&5] &a&l%1 &r&arozpocznie si� za %2 sekund!"),
	LOADED("&5[&6Event&5] &aZa�adowano &a&l%1 &r&aevent(�w)! W tym: &a&l%2"),
	INFO_TIP("&5[&6Event&5] &aNie wiesz na czym polega ten event? Wpisz &a&l/e info&r&a."),
	LOBBY_JOINED("&5[&6Event&5] &aPomy�lnie do��czy�e� do lobby."),
  LOBBY_LEAVE("&5[&6Event&5] &aPomy�lnie opu�ci�e� lobby."),
  LOBBY_INVITE("&5[&6Event&5] &aZa &a&l%1 &r&asekund rozpoczyna si� event! Wpisz &a&l/e lobby &r&aaby do niego do��czy�."),
  LOBBY_LOCATION_SET("&5[&6Event&5] &aPomy�lnie ustawi�e� punkt spawnu lobby na Twojej pozycji!"),
  LOBBY_MESSAGE_HEADER("&a~~~~~~~~~&a&lLobby&r&a~~~~~~~~~"),
  LOBBY_MESSAGE_CURRENT("&aObecnie trwa event: &a&l%1&r&a (&a&l%2&r&a)"),
  LOBBY_MESSAGE_PLAYERS_IN_EVENT("&aNa evencie znajduje si� &a&l%1&r&a graczy."),
  LOBBY_MESSAGE_PLAYERS_IN_LOBBY("&aW lobby znajduje si� &a&l%1&r&a graczy."),
  LOBBY_MESSAGE_TIME("&aEvent trwa ju� &a&l%1&r&a."),
  LOBBY_MESSAGE_SPECTATE_INVITE("&aWpisz &a&l/e spec&r&a aby obejrze� event."),
  SPECTATOR_TIP("&5[&6Event&5] &aWpisz &a&l/e spec &r&aaby obserwowa� event."),
  BACK_TO_LOBBY("&5[&6Event&5] Pomy�lnie powr�ci�e� do lobby."),
  EVENT_LEAVE("&5[&6Event&5] &aPomy�lnie opu�ci�e� event."),
  REWARD_INFO("&5[&6Event&5] &aWpisz &l&a/e nagrody &r&aaby wy�wietli� nagrody za wygranie tego eventu"),
  REWARD_ADDED("&5[&6Event&5] &aOtrzyma�e� nagrod� za udzia� w evencie! Odbierz j� wpisuj�c &a&l/e nagroda&r&a."),
  REWARD_NOTIFICATION("&5[&6Event&5] &aMasz nieodebrane nagrody! Odbierz je wpisuj�c &a&l/e nagroda&r&a. Nieodebrane nagrody usuwaj� si� po 3 dniach."),
  REWARD_OPENED("&5[&6Event&5] &aOtworzono menu odbierania nagr�d."),
  REWARD_NO_REWARDS("&5[&6Event&5] &cNie posiadasz �adnych nagr�d do odebrania!"),
  REWARD_CLAIMED("&5[&6Event&5] &aDzi�kujemy za odebranie wszystkich nagr�d!"),
  REWARD_NOT_ALL_CLAIMED("&5[&6Event&5] &cUWAGA! Nie odebra�e� wszystkich nagr�d. Nieodebrane nagrody s� usuwane po 3 dniach od ich otrzymania."),
  REWARD_NOT_IN_LOBBY("&5[&6Event&5] &cAby odebra� nagrody, musisz najpierw opu�ci� lobby komend� &c&l/e wyjdz&r&c."),
  REWARD_NOT_IN_EVENT("&5[&6Event&5] &cAby odebra� nagrody, musisz najpierw opu�ci� event komend� &c&l/e wyjdz&r&c."),
  EVENT_STARTING_SOON("&5[&6Event&5] &aEvent &a&l%1 &r&a(&a&l%2&r&a) rozpocznie si� za &a&l%3&r&a sekund! Wpisz &a&l/e lobby &r&aaby do niego do��czy�."),
  EVENT_PLAYER_LEAVED("&5[&6Event&5] &aGracz &a&l%1 &r&aopu�ci� event! Pozosta�o &a&l%2&r&a graczy."),
  STARTED("&5[&6Event&5] &aPomy�lnie zacz��e� event &a&l%1 &r&atypu &a&l%2&r&a!");
  
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

