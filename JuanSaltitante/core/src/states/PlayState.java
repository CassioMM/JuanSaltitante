/*
 Aqui será o jogo em sí. Com as classes "Bird" e "Tube" sendo chamadas e usadas, elas definem
 como funcionam o personagem e os obstaculos, para depois colocalos em certas lógicas, como movimento
 na tela/camera. Nesta classe tambem teremos a gravação e atualização dos pontos no "Score", botões
 e suas ações e oque acontece quando o personagem quando ele colide com os obstaculos 
 (no caso reiniciando a classe). 
*/

package states;

import java.io.BufferedReader;  
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gam.FlappyDemo;

import sprites.Animation;
import sprites.Bird;
import sprites.Tube;


public class PlayState implements Screen {
	
	private static final int TUBE_SPACING = 200;//150
	private static final int TUBE_COUNT = 4;
	
	private Bird bird;
	public Texture bg;
	public Texture ground;
	public Animation bgAnimation;
	public Animation groundAnimation;
	int Points = 0;

	private Array<Tube> tubes;
	
	FileWriter grava;
	BufferedReader le = null;
	int calcPonto;
	
	FlappyDemo game;
	
	public PlayState(FlappyDemo game) {
		this.game = game;
		
		bird = new Bird(50, 300);
		game.cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
		
		bg = new Texture("Cenário 2.png");
		
		tubes = new Array<Tube>();
		
		for(int i = 1; i <= TUBE_COUNT; i++) {
			tubes.add(new Tube( i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
		}
		
		try {
			le = new BufferedReader(new FileReader("C:\\Users\\Pichau\\Documents\\teste\\FlappyBird2\\Arquivo.txt"));
			String lePonto = null;
			while((lePonto = le.readLine()) != null)
				calcPonto = Integer.valueOf(lePonto);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public PlayState() {
		// TODO Auto-generated constructor stub
	}


	protected void handleInput() {

		if(Gdx.input.justTouched()) {
			bird.jump();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			bird.jump();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		
	}
	
	@Override
	public void render(float dt) {
		handleInput();
		bird.update(dt);
		
		
		game.cam.position.x = bird.getPosition().x + 80;
	

		for(int i = 0; i < tubes.size; i++) {
			Tube tube = tubes.get(i);
			
			if(game.cam.position.x - (game.cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
				tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
			}
		
			if(tube.collides(bird.getBounds())) {
				this.dispose();
				game.setScreen(new PlayState(game));
				
				if(Points > calcPonto) {
					try {
						grava = new FileWriter(new File("C:\\Users\\Pichau\\Documents\\teste\\FlappyBird2\\Arquivo.txt"));
						grava.write(Integer.toString(Points));
						grava.close();
					} catch (IOException e) {
						e.printStackTrace();
					}		
				}
			}
			
			if(tube.collidesEnemy(bird.getBounds())) {
				this.dispose();
				game.setScreen(new PlayState(game));
			}
			
			if(tube.collidesGap(bird.getBounds())) {
				Points ++;
				tube.posMidGap.y = -200;
				tube.boundsGap.y = -200;
			}
			
			if(tube.collidesNut(bird.getBounds())) {
				Points ++;
				tube.posNut.y = -200;
				tube.boundsNut.y = -200;
			}

			tube.update(dt);
		}
		
		
		game.cam.update();
		
		game.batch.setProjectionMatrix(game.cam.combined);
		game.batch.begin();
		game.batch.draw(bg, (game.cam.viewportWidth) - (game.cam.viewportWidth + 60), 0);//- MOVEMENT * dt
		game.batch.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y, 50, 24);
		for(Tube tube : tubes) {
			game.batch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
			game.batch.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
			game.batch.draw(tube.getNut(), tube.getPosNut().x, tube.getPosNut().y);
			game.batch.draw(tube.getEnemy(), tube.getPosEnemy().x, tube.getPosEnemy().y);
		}
		
		game.batch.end();
		
		game.font.setColor(1f, 0f, 0f, 1f);
		game.batch.begin();
		game.font.draw(game.batch, " "+ Points, game.cam.position.x, 400);
		game.batch.end();
	}

	@Override
	public void dispose() {
		bg.dispose();
		bird.dispose();
		for(Tube tube : tubes) {
			tube.dispose();
		}
	}

	
	@Override
	public void show() {
		
	}
	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
