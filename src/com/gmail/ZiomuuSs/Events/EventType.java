package com.gmail.ZiomuuSs.Events;

public enum EventType {
	BASIC, //main Event class. This shouldn't even be used.
	SPLEEF;
	
	public static boolean isValid(String name) {
		for (EventType e : EventType.values())
			if (e.toString().equalsIgnoreCase(name))
				return true;
		return false;
	}
}
