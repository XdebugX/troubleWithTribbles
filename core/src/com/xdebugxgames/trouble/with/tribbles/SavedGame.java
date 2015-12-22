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
	
	
	public SavedGame () {
	
		futureUseBools = new boolean [1000];
		futureUseInts = new int [1000];
		futureUseStrings = new String [1000];
		futureUseLongs = new Long [1000];
		futureUseFloats = new Float [1000];
		
		
	}		

}
