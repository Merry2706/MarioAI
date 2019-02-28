package de.novatec.marioai.agents.included;

import de.novatec.marioai.tools.MarioInput;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import ch.idsia.mario.engine.MarioComponent;
import de.novatec.mario.engine.generalization.Entities.EntityType;
import de.novatec.mario.engine.generalization.Entity;
import de.novatec.marioai.tools.LevelConfig;
import de.novatec.marioai.tools.MarioAiAgent;
import de.novatec.marioai.tools.MarioAiRunner;

public class ExampleAgent extends MarioAiAgent {

	@Override
	public String getName() {
		return "Test Agent";
	}

	@Override
	public void reset() {

	}

	@Override
	// wo steh ich gerade, punkte
	public MarioInput doAiLogic() {

		List<Entity> enemies = getAllEnemiesOnScreen();
		for (Entity enemy : enemies) {
			if (getDistance(enemy) < 5) {
				if (enemy.getType() == EntityType.ENEMY_FLOWER) {
					// System.out.println(entity.getRelX()+ entity.getRelY());
					if (enemy.getRelY() >= 2) {

						jump();
						shoot();
						moveRight();
						jump();

					}
					return getMarioInput();
				}
				if (enemy.getType() == EntityType.SPIKY) {
					System.out.println(enemy.getCoordinates());
					if (enemy.getRelY() >= -1) {
						shoot();
						moveRight();
						jump();
					}
					return getMarioInput();
				}
				if (enemy.getType() == EntityType.GOOMBA_WINGED) {
					if (enemy.getRelY() >= -1) {

						shoot();
						moveRight();

					}
					return getMarioInput();

				}
				if (enemy.getType() == EntityType.GOOMBA) {
					if (enemy.getRelY() >= -3) {

						shoot();
						jump();
						shoot();
						moveRight();

					}
					return getMarioInput();

				}
//				if (enemy.getType() == EntityType.GREEN_KOOPA) {
//					System.out.println(EntityType.GREEN_KOOPA);
//					if (enemy.getRelY() >= -1) {
//
//						shoot();
//						moveRight();
//						jump();
//
//					}
				//}

//			}
//			if (enemy.getType() == EntityType.RED_KOOPA) {
//				System.out.println(EntityType.RED_KOOPA);
//				if (enemy.getRelY() >= -1) {
//
//					shoot();
//					moveRight();
//
//				}
//			}
//
//		}

		if (mayShoot() && isEnemyAhead()) {
			shoot();
			jump();
			moveRight();
		}
		

	 if(isEnemyAhead()) 
	 {moveRight();}

		if (isSlopeAhead() && !isHoleAhead() && !(getDeepCopyOfLevelScene().getMarioXA() < 2)) 
		{	moveRight();
			jump();
			shoot();
			
		}
		
		

		if (isHoleAhead() || isBrickAhead() || isQuestionbrickAbove()) {
			jump();
			moveRight();
			
		}
			
			
	
		
		}
		}
		return getMarioInput();}
			

	public static void main(String[] args) {
		MarioAiRunner.run(new ExampleAgent(), LevelConfig.LEVEL_6);

	}
}
