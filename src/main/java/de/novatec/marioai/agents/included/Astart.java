package de.novatec.marioai.agents.included;

import de.novatec.marioai.tools.MarioInput;
import de.novatec.marioai.tools.MarioKey;
import de.novatec.marioai.tools.Node;
import javafx.scene.Scene;

import java.awt.event.InputMethodListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import ch.idsia.mario.engine.LevelScene;
import ch.idsia.mario.engine.MarioComponent;
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
	
	public List<MarioInput> generatePossibleInputs () {
		List<MarioInput> inputListe = new ArrayList<MarioInput>();

		MarioInput jump = new MarioInput();
		jump.press(MarioKey.RIGHT);
		jump.press(MarioKey.JUMP);
		jump.press(MarioKey.SPEED);
		inputListe.add(jump);

		MarioInput moveRightnow = new MarioInput();
		moveRightnow.press(MarioKey.RIGHT);
		moveRightnow.press(MarioKey.JUMP);
		inputListe.add(moveRightnow);

		MarioInput superMove = new MarioInput();
		superMove.press(MarioKey.RIGHT);
		superMove.press(MarioKey.SPEED);
		inputListe.add(superMove);

		MarioInput temp  = new MarioInput();
		superMove.press(MarioKey.RIGHT);
		inputListe.add(superMove);
		
		return inputListe;
	}







	@Override
	// wo steh ich gerade, punkte
	public MarioInput doAiLogic() {
		
		List<Node> openSet = new ArrayList<Node>();
		List<Node> closeSet = new ArrayList<Node>();

		Node root =  new Node(null, new MarioInput(), null);
		root.scence = getAStarCopyOfLevelScene();
		//		Node next = new Node(null, null, null);
		//		next.scence = getAStarCopyOfLevelScene();
		int kosten = 0;
		int heuristic = 0 ;

		float ziel = root.scence.getMarioX() + 50;
		openSet.add(root);

		Node minimum = null;

		while (!openSet.isEmpty())
		{
			minimum = openSet.stream().min((a,b) -> Float.compare(a.getF(), b.getF())).get();

			System.out.println(minimum.getF());
			System.out.println(openSet.size());
			
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
				nachbar.kosten = minimum.kosten;
				nachbar.heuristik = (ziel - clone.getMarioX());
				
				openSet.add(nachbar);
			}

			//		
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



