package com.xdebugxgames.trouble.with.tribbles;

import java.io.Serializable;

public class Options implements Serializable {
	
	/**
	 * Version 1
	 */
	private static final long serialVersionUID = 1L;

	public boolean sfxOn,musicOn,loginGP;
	
	public boolean futureUseBools[];
	
	public int futureUseInts[];
	
	public String futureUseStrings[];
	
	public int futureUseLongs[];
	
	public float futureUseFloats[];

}
