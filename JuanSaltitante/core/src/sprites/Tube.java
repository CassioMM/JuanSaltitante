/*
 Lógica dos obstaculos do jogo. Os tubos tem um sprit pra cada posição de cima e baixo,
 isso também influencia na criação uma posição de hitbox pra cada, uma animação e 
 uma logica com o "Rand" para faze-lo aparecer em posições verticais diferentes junto com
 as nozes q são os pontos no jogo e seus inimigos, que também fazem você reiniciar a fase quando
 são encostados.
*/
package sprites;

import java.util.Random;   

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Tube {
	public static final int TUBE_WIDTH = 52;
	public static final int FLUCTUATION = 130;
	public static final int TUBE_GAP = 100;
	public static final int LOWEST_OPENING = 120;
	public Texture topTube, bottomTube, Nut, Enemy;
	public Vector2 posTopTube, posBotTube, posMidGap, posNut, posEnemy;
	public Rectangle boundsTop, boundsBot, boundsGap, boundsNut, boundsEnemy;
	public Animation texture1, texture2;
	public Random rand;

	
	public Tube(float x) {
		
		topTube = new Texture("Tronco Cima.png");
		bottomTube = new Texture("Tronco_Baixo.png");
		Nut = new Texture("Noz.png");
		Enemy = new Texture("bird.png");
		rand = new Random();
		
		posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
		posMidGap = new Vector2(x, posTopTube.y - TUBE_GAP / 2);
		posNut = new Vector2(x + 100, rand.nextInt(300));
		posEnemy = new Vector2(x + 120, rand.nextInt(350));
		
		boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
		boundsBot = new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
		boundsGap = new Rectangle(posMidGap.x, posMidGap.y, 34, 24);
		boundsNut = new Rectangle(posNut.x, posNut.y, Nut.getWidth(), Nut.getHeight());
		boundsEnemy = new Rectangle(posEnemy.x, posEnemy.y, Enemy.getWidth(), Enemy.getHeight());
		
	}
	
	public void update (float dt) {
		
	}

	public Texture getTopTube() {
		return topTube;
	}

	public Texture getBottomTube() {
		return bottomTube;
	}
	
	public Texture getNut() {
		return Nut;
	}
	
	public Texture getEnemy() {
		return Enemy;
	}

	public Vector2 getPosTopTube() {
		return posTopTube;
	}

	public Vector2 getPosBotTube() {
		return posBotTube;
	}
	
	
	public Vector2 getPosNut() {
		return posNut;
	}
	
	public Vector2 getPosEnemy() {
		return posEnemy;
	}
	
	
	public void reposition(float x) {
		posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
		posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
		posMidGap.set(x, posTopTube.y - TUBE_GAP / 2);
		posNut = new Vector2(x + 100, rand.nextInt(300));
		posEnemy = new Vector2(x + 120, rand.nextInt(350));
		
		
		boundsTop.setPosition(posTopTube.x, posTopTube.y);
		boundsBot.setPosition(posBotTube.x, posBotTube.y);
		boundsGap.setPosition(posMidGap.x, posMidGap.y);
		boundsNut.setPosition(posNut.x, posNut.y);
		boundsEnemy.setPosition(posEnemy.x, posEnemy.y);
	}
	
	public boolean collides(Rectangle player) {
		return player.overlaps(boundsTop) || player.overlaps(boundsBot);
	}
	
	public boolean collidesGap(Rectangle player) {
		return player.overlaps(boundsGap);
	}
	
	public boolean collidesNut(Rectangle player) {
		return player.overlaps(boundsNut);
	}
	
	public boolean collidesEnemy(Rectangle player) {
		return player.overlaps(boundsEnemy);
	}
	
	
	public void dispose() {
		topTube.dispose();
		bottomTube.dispose();
		Nut.dispose();
		Enemy.dispose();
	}
}
