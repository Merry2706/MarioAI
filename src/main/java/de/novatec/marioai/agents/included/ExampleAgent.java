package de.novatec.marioai.agents.included;

import de.novatec.marioai.tools.MarioInput;

import java.util.List;
import java.util.stream.Collectors;

import de.novatec.mario.engine.generalization.Entities.EntityType;
import de.novatec.mario.engine.generalization.Entity;
import de.novatec.mario.engine.generalization.Tiles.TileType;
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
			if(getDistance(enemy) < 5) {
				if (enemy.getType() == EntityType.ENEMY_FLOWER) {
					//System.out.println(entity.getRelX()+ entity.getRelY());
					if(enemy.getRelY()>=-1) {
						shoot();
						moveRight();
						jump();
					}
					return getMarioInput();
				}
				if (enemy.getType() == EntityType.SPIKY) {
					System.out.println(enemy.getCoordinates());
					if(enemy.getRelY()>=-1) {
						shoot();
						moveRight();
						jump();
					}
					return getMarioInput();
				}
			}
		}
//
//		List<Entity> flowers = getAllEnemiesOnScreen().stream().filter(e -> e.getType() == EntityType.ENEMY_FLOWER).collect(Collectors.toList());		
//		for (Entity entity : flowers) {
//			if(getDistance(entity) < 5) {
//				//System.out.println(entity.getRelX()+ entity.getRelY());
//				if(entity.getRelY()>=-1) {
//					shoot();
//					moveRight();
//					jump();
//				}
//				return getMarioInput();
//			}	
//		}
		
		List<Entity> spikies= getAllEnemiesOnScreen().stream().filter(e -> e.getType() == EntityType.SPIKY).collect(Collectors.toList());
		for (Entity entity : spikies) {
			if(getDistance(entity) < 5) {
				System.out.println(entity.getCoordinates());
				if(entity.getRelY()>=-1) {
					shoot();
					moveRight();
					jump();
				}
				return getMarioInput();
			}	
		}
//		for ( int i = -9;i< 10;i++) {
//			for (int j= -9; j< 10 ;j++)
//			{
//				getTile(i, j).equals(TileType.COIN);
//				jump();
//				moveRight();
//				
//			}
//		}
		
		if(mayShoot()&&isEnemyAhead()) {
			shoot();
			sprint();
		}

		if(isEnemyAhead()) jump();

		if(isSlopeAhead()&&!isHoleAhead()&&!(getDeepCopyOfLevelScene().getMarioXA()<2))

			
			shoot();
			//sprint();
			moveRight();


		if(isHoleAhead()||isBrickAhead()||isQuestionbrickAbove()) jump();

		
		return getMarioInput();
	}
	
	public static void main(String[] args) {
		MarioAiRunner.run(new ExampleAgent(), LevelConfig.LEVEL_6);
		
	}
}
