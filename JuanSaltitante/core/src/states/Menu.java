/*
 Todas as telas teram o "Screen" incrementedas, para seguirem um modelo. Aqui vamos
 definir apenas os botões, com suas posições e sprits. Cada botão quando estiverem com o 
 click do mouse em suas posições, as suas respectivas classes seram chamadas mudando assim
 as telas e funções.  
*/


package states;

import com.badlogic.gdx.Gdx;   
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gam.FlappyDemo;

public class Menu implements Screen{
	
	private static final int PLAY_BUTTON_WIDTH = 104;//Sprite size
	private static final int PLAY_BUTTON_HEIGHT = 58;//Play Button
	private static final int PLAY_BUTTON_X = 225;
	private static final int PLAY_BUTTON_Y = 335;
	
	private static final int SCORE_BUTTON_WIDTH = 104;//Sprite size
	private static final int SCORE_BUTTON_HEIGHT = 58;//Play Button
	private static final int SCORE_BUTTON_X = 445;
	private static final int SCORE_BUTTON_Y = 300;
	
	private static final int CREDITS_BUTTON_WIDTH = 104;//Sprite size
	private static final int CREDITS_BUTTON_HEIGHT = 58;//Play Button
	private static final int CREDITS_BUTTON_X = 620;
	private static final int CREDITS_BUTTON_Y = 430;
	
	private static final int EXIT_BUTTON_WIDTH = 104;//Sprite size
	private static final int EXIT_BUTTON_HEIGHT = 58;//Exit Button
	private static final int EXIT_BUTTON_Y = 160;
	
	
	private static final int LOGO_WIDTH = 58;//Sprite size
	private static final int LOGO_HEIGHT = 58;// Logo
	private static final int LOGO_X = 15; //15
	private static final int LOGO_Y = 100;
	
	public Texture background;
	public Texture playBtn, ScoreBtn, CreditsBtn, exitBtn;
	public Texture ComunaLogo;
	
	FlappyDemo game;
	
	public Menu(FlappyDemo game) {
		this.game = game;
	}
	
	public void handleInput() {
		int x = PLAY_BUTTON_X;
		if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && FlappyDemo.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && FlappyDemo.HEIGHT - Gdx.input.getY() >  PLAY_BUTTON_Y) {
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new PlayState(game));
			}
		}
		
		x = SCORE_BUTTON_X;
		if(Gdx.input.getX() < x + SCORE_BUTTON_WIDTH && Gdx.input.getX() > x && FlappyDemo.HEIGHT - Gdx.input.getY() < SCORE_BUTTON_Y + SCORE_BUTTON_HEIGHT && FlappyDemo.HEIGHT - Gdx.input.getY() >  SCORE_BUTTON_Y) {
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new Score(game));
			}
		}
		
		x = CREDITS_BUTTON_X;
		if(Gdx.input.getX() < x + CREDITS_BUTTON_WIDTH && Gdx.input.getX() > x && FlappyDemo.HEIGHT - Gdx.input.getY() < CREDITS_BUTTON_Y + CREDITS_BUTTON_HEIGHT && FlappyDemo.HEIGHT - Gdx.input.getY() >  CREDITS_BUTTON_Y) {
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new Credits(game));
			}
		}
		
		
		x = FlappyDemo.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 - 160;
		if(Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && FlappyDemo.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && FlappyDemo.HEIGHT - Gdx.input.getY() >  EXIT_BUTTON_Y) {
			if(Gdx.input.isTouched()) {
				this.dispose();
				Gdx.app.exit();
			}
		}
		
	
	}
	
	@Override
	public void show() {
		background = new Texture("Menu.png");
		playBtn = new Texture("botaoJogar.png");
		ScoreBtn = new Texture("botaoScore.png");
		CreditsBtn = new Texture("botaoCredito.png");
		exitBtn = new Texture ("botaoSair.png");
		ComunaLogo = new Texture ("Comuna_Logo.png");
	}

	@Override
	public void render(float delta) {
		handleInput();

		game.batch.begin();
		game.batch.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
		game.batch.draw(playBtn, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		game.batch.draw(ScoreBtn, SCORE_BUTTON_X, SCORE_BUTTON_Y, SCORE_BUTTON_WIDTH, SCORE_BUTTON_HEIGHT);
		game.batch.draw(CreditsBtn, CREDITS_BUTTON_X, CREDITS_BUTTON_Y, CREDITS_BUTTON_WIDTH, CREDITS_BUTTON_HEIGHT);
		game.batch.draw(exitBtn, (FlappyDemo.WIDTH / 2) - (EXIT_BUTTON_WIDTH / 2) - 160, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		game.batch.draw(ComunaLogo, LOGO_X, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);
		game.batch.end();
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

	@Override
	public void dispose() {
		background.dispose();
		playBtn.dispose();
		ScoreBtn.dispose();
		CreditsBtn.dispose();
		exitBtn.dispose();
	}

}


