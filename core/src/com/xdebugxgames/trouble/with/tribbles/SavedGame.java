package com.xdebugxgames.trouble.with.tribbles;

import java.io.Serializable;

public class SavedGame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public boolean futureUseBools[];
	
	public int futureUseInts[];
	
	public String futureUseStrings[];
	
	public Long futureUseLongs[];
	
	public Float futureUseFloats[];
	
	public int board [] [],level;
	
	
	
	public SavedGame () {		
	}		

}
