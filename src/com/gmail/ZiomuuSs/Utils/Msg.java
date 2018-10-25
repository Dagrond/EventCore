package com.gmail.ZiomuuSs.Utils;

public class Msg {
  
  public enum msg {
  	ERROR_PERMISSION(""),
  	DAS("");
  	
  	private String message;
  	
  	msg(String message) {
  		this.message = message;
  	}
  }
}
