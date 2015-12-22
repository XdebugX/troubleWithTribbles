package com.xdebugxgames.trouble.with.tribbles;

public interface IActivityRequestHandler {
	public void sendMsg(String msgg);
	
	public void saveGame (byte [] s);
	
	public void loadGame();
	
}
