/*
Uma simples tela que mostra o Score mais alto registrado. Lembrando que sempre que o jogo
começa denovo os pontos começam em 0, mas quando ultrapação o maior valor do score esses valor é
substituido.
*/


package states;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gam.FlappyDemo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Score implements Screen{
	
	private static final int SCOREMAX_BUTTON_Y = 460;
	private static final int SCOREMAX_BUTTON_X = 380;

	private static final int PLAY_BUTTON_WIDTH = 104;//Sprite size
	private static final int PLAY_BUTTON_HEIGHT = 58;//Play Button
	private static final int PLAY_BUTTON_Y = 160;

	private static final int EXIT_BUTTON_WIDTH = 104;//Sprite size
	private static final int EXIT_BUTTON_HEIGHT = 58;//Exit Button
	private static final int EXIT_BUTTON_Y = 160;
	
	private static final int LOGO_WIDTH = 58;//Sprite size
	private static final int LOGO_HEIGHT = 58;// Logo
	private static final int LOGO_X = 15;
	private static final int LOGO_Y = 100;
	
	private Integer Points = 0;
	private Integer MaxPoints = 0;
	
	public Texture background;
	public Texture playBtn;
	public Texture exitBtn;
	public Texture ComunaLogo;
	
	FlappyDemo game;
	
	PlayState Point = new PlayState();
	

	public Score(Integer points, Integer maxPoints) {
		Points = points;
		MaxPoints = maxPoints;
		
	}
	
	public Score(FlappyDemo game) {
		this.game = game;
	}

	
	public Integer getPoints() {
		return Points;
	}

	
	public void setPoints(Integer points) {
		Points = points;
	}

	
	public Integer getMaxPoints() {
		return MaxPoints;
	}

	
	public void setMaxPoints(Integer maxPoints) {
		MaxPoints = maxPoints;
	}
	
	public void handleInput() {
		int x = FlappyDemo.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
		if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && FlappyDemo.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && FlappyDemo.HEIGHT - Gdx.input.getY() >  PLAY_BUTTON_Y) {
			if(Gdx.input.isTouched()) {
				this.dispose();
				game.setScreen(new Menu(game));
			}
		}
		
		x = FlappyDemo.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2 + 160;
		if(Gdx.input.getX() < x + EXIT_BUTTON_WIDTH && Gdx.input.getX() > x && FlappyDemo.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && FlappyDemo.HEIGHT - Gdx.input.getY() >  EXIT_BUTTON_Y) {
			if(Gdx.input.isTouched()) {
				this.dispose();
				Gdx.app.exit();
			}
		}
		
	}

	
	@Override
	public void show() {
		background = new Texture("telaScore.png");
		playBtn = new Texture("botaoJogar.png");
		exitBtn = new Texture ("botaoSair.png");
		ComunaLogo = new Texture ("Comuna_Logo.png");
		
		try {
			Point.le = new BufferedReader(new FileReader("C:\\Users\\Pichau\\Documents\\teste\\FlappyBird2\\Arquivo.txt"));
			String lePonto = null;
			while((lePonto = Point.le.readLine()) != null)
				Point.calcPonto = Integer.valueOf(lePonto);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void render(float delta) {
		handleInput();
	
		game.batch.begin();
		game.batch.draw(background, 0, 0, FlappyDemo.WIDTH, FlappyDemo.HEIGHT);
		game.batch.draw(playBtn, (FlappyDemo.WIDTH / 2) - (PLAY_BUTTON_WIDTH / 2), PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		game.batch.draw(exitBtn, (FlappyDemo.WIDTH / 2) - (EXIT_BUTTON_WIDTH / 2) + 160, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		game.batch.draw(ComunaLogo, LOGO_X, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);
		game.batch.end();
		
		game.font.setColor(1f, 0f, 0f, 1f);
		game.batch.begin();
		game.font.draw(game.batch, " "+ Point.calcPonto, SCOREMAX_BUTTON_X, SCOREMAX_BUTTON_Y);
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
		exitBtn.dispose();		
	}

}
