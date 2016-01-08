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
	
	public long futureUseLongs[];
	
	public float futureUseFloats[];
	
	public Options () {
		futureUseBools = new boolean [100];
		futureUseInts = new int [100];
		futureUseStrings = new String [100];
		futureUseLongs = new long [100];
		futureUseFloats = new float [100];
		
		sfxOn=true;
		musicOn=true;
	}

}
