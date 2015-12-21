package com.xdebugxgames.trouble.with.tribbles;

import java.io.Serializable;

public class Options implements Serializable {
	
	/**
	 * Version 1
	 */
	private static final long serialVersionUID = 1L;

	public boolean sfxOn,musicOn;
	
	public boolean futureUseBools[];
	
	public int futureUseInts[];
	
	public String futureUseStrings[];
	
	public Long futureUseLongs[];
	
	public Float futureUseFloats[];
	
	public Options () {
		futureUseBools = new boolean [100];
		futureUseInts = new int [100];
		futureUseStrings = new String [100];
		futureUseLongs = new Long [100];
		futureUseFloats = new Float [100];
		
		sfxOn=true;
		musicOn=true;
	}

}
