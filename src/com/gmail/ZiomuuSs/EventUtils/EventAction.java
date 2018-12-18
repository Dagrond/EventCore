package com.gmail.ZiomuuSs.EventUtils;

public class EventAction {
	public enum ActionType {
		APPLY_EFFECT, //apply potion effect to all event participants
		GIVE_ITEM, //give item to all event participants
		END_EVENT, //end event
		ANNOUNCE; //announce all players in event
	}
	private ActionType type; //type of action
	
	public EventAction(ActionType type) {
		this.type = type;
	}
	
	public ActionType getType() {
		return type;
	}
	
	

}
