package de.novatec.marioai.tools;

import ch.idsia.mario.engine.LevelScene;

public class Node {
	public LevelScene scence;
	String name;
	MarioInput action;
	Node parent;
	public int depth =0;
	public float heuristik=0;
	public float kosten= 0;
	
	public Node(String name, MarioInput action, Node parent) {
			this.name = name;
		this.action = action;
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MarioInput getAction() {
		return action;
	}
	public void setAction(MarioInput action) {
		this.action = action;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public float getF() {
		return kosten + heuristik;
	}


}
