package de.novatec.marioai.agents.included;

import de.novatec.marioai.tools.MarioInput;
import de.novatec.marioai.tools.MarioKey;
import de.novatec.marioai.tools.Node;
import javafx.scene.Scene;

import java.awt.event.InputMethodListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale.LanguageRange;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import ch.idsia.mario.engine.LevelScene;
import ch.idsia.mario.engine.MarioComponent;
import ch.idsia.mario.engine.sprites.Mario.MODE;
import ch.idsia.mario.engine.sprites.Mario.STATUS;
import de.novatec.mario.engine.generalization.Entities.EntityType;
import de.novatec.mario.engine.generalization.Entity;
import de.novatec.marioai.tools.LevelConfig;
import de.novatec.marioai.tools.MarioAiAgent;
import de.novatec.marioai.tools.MarioAiRunner;

public class Astart extends MarioAiAgent {

	@Override
	public String getName() {
		return "Test Agent";
	}

	@Override
	public void reset() {

	}

	public List<MarioInput> generatePossibleInputs() {
		List<MarioInput> inputListe = new ArrayList<MarioInput>();

//		
//		if(toInput[0]) res.press(MarioKey.LEFT);
//		if(toInput[1]) res.press(MarioKey.RIGHT);
//		if(toInput[2]) res.press(MarioKey.DOWN);
//		if(toInput[3]) res.press(MarioKey.JUMP);
//		if(toInput[4]) res.press(MarioKey.SPEED);
//		

		MarioInput jump = new MarioInput();
		jump.press(MarioKey.LEFT);
		inputListe.add(jump);

		MarioInput jump0 = new MarioInput();
		jump0.press(MarioKey.LEFT);
		jump0.press(MarioKey.JUMP);
		inputListe.add(jump0);

		MarioInput jump3 = new MarioInput();
		jump3.press(MarioKey.LEFT);
		jump3.press(MarioKey.SPEED);
		inputListe.add(jump3);

		MarioInput jump2 = new MarioInput();
		jump2.press(MarioKey.RIGHT);
		jump2.press(MarioKey.JUMP);
		inputListe.add(jump2);

		MarioInput jump4 = new MarioInput();
		jump4.press(MarioKey.RIGHT);
		jump4.press(MarioKey.SPEED);
		
		inputListe.add(jump4);


		MarioInput moveRightnow = new MarioInput();
		moveRightnow.press(MarioKey.RIGHT);
		moveRightnow.press(MarioKey.JUMP);
		moveRightnow.press(MarioKey.SPEED);
		inputListe.add(moveRightnow);

		MarioInput temp = new MarioInput();
		temp.press(MarioKey.RIGHT);
		
		inputListe.add(temp);

		return inputListe;
	}

	@Override
	// wo steh ich gerade, punkte
	public MarioInput doAiLogic() {

		List<Node> openSet = new ArrayList<Node>();
		List<Node> closeSet = new ArrayList<Node>();

		Node root = new Node(null, new MarioInput(), null);
		root.scence = getAStarCopyOfLevelScene();

		int kosten = 0;
		int heuristic = 0;

		float ziel = root.scence.getMarioX() + 100;
		openSet.add(root);

		Node minimum = null;
		
		float faktor = 1f;
		
		

		long ts = System.currentTimeMillis();

		while (!openSet.isEmpty() && System.currentTimeMillis() - ts < 50) {
			minimum = openSet.stream().min((a, b) -> Float.compare(a.getF(), b.getF())).get();

//			System.out.println(minimum.getF());
	

			closeSet.add(minimum);
			openSet.remove(minimum);

			// haben wir gewonnen?
			if (minimum.scence.getMarioX() > ziel) {
				break;
			}

			for (MarioInput marioInput : generatePossibleInputs()) {

				LevelScene clone = minimum.scence.getAStarCopy();
				clone.setMarioInput(marioInput);
				clone.tick();
				
			
				float xPos = clone.getMarioX(); // Mario's predicted x position
				float yPos = clone.getMarioY(); // Mario's predicted y position

				Node nachbar = new Node("", marioInput, minimum);
				nachbar.scence = clone;
				nachbar.kosten = minimum.kosten + 200;
				if (nachbar.scence.getMarioMode() == MODE.MODE_LARGE) {
					nachbar.kosten += 100;
				}
				if (nachbar.scence.getMarioMode() == MODE.MODE_SMALL) {
					nachbar.kosten += 500;
				}
				if (minimum.scence.getMarioCoins() < nachbar.scence.getMarioCoins()) {
					nachbar.kosten +=  -600;
				}
				if(minimum.scence.getMarioGainedFowers()< nachbar.scence.getMarioGainedFowers()) {
					nachbar.kosten += -400;
				}
				if(minimum.scence.getMarioGainedMushrooms() < nachbar.scence.getMarioGainedMushrooms())
				{	
					nachbar.kosten += -700;
				}
				if(minimum.scence.getMarioHeight() < nachbar.scence.getMarioHeight()) {
					nachbar.kosten += -300;
				}
				if(nachbar.scence.getMarioStatus() == getMarioStatus().LOSE) {
					
					nachbar.kosten+= -10000;
					
				}
			
			 
					
				// rechte hat mehr heuristik als die linke
				Entity flower = null; 
	
				
				
			
				
				if(getAllEntitiesOnScreen().stream().filter(a-> a.getType() == EntityType.FIRE_FLOWER).count() > 0) {
					flower = getAllEntitiesOnScreen().stream().filter(a-> a.getType() == EntityType.FIRE_FLOWER).findFirst().get();
				}

				if (flower == null)
					nachbar.heuristik = (ziel - clone.getMarioX())*faktor;
				else {
					nachbar.heuristik = flower.getRelX() + flower.getRelY();
				}
				

				openSet.add(nachbar);
			}

			
		}


		while (minimum.getParent() != null && minimum.getParent() != root) {
			minimum = minimum.getParent();
		}
		return minimum.getAction();
		}

	public static void main(String[] args) {
		MarioAiRunner.run(new Astart(), LevelConfig.LEVEL_6);

	}
}
