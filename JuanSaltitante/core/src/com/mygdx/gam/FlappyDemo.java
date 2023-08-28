/*
 Este é um jogo feito para a faculdade Fatec de São Caetano do Sul Antonio Russo, do curso
 Jogos Digitais no 3° Semestre.
 A ideia é fazermos um jogo usando a linguagem de programação JAVA com a framework libGDX.
 
 Nesta parte os primeiros parametros são feitos para o funcionamento do jogo. Começa com
 as funções para se abrir uma tela e introduzindo uma camera, sprits, movimentos de um mouse
 e musicas.
 */

package com.mygdx.gam;

import com.badlogic.gdx.Game;   
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import states.Menu;

public class FlappyDemo extends Game {
	public static final int WIDTH = 780;
	public static final int HEIGHT = 840;
	public static final String TITLE = "Flappy Bird";
	public SpriteBatch batch;
	public Music music;
	public OrthographicCamera cam;
	public Vector3 mouse;
	public BitmapFont font;
	public String grava;

	@Override
	public void create() {
		this.setScreen(new Menu(this));
	
		
		cam = new OrthographicCamera();
		mouse = new Vector3();
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);	
	}
	
	@Override
	public void render () {
		super.render();
		Gdx.graphics.getDeltaTime();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		music.dispose();
		batch.dispose();
		font.dispose();
		
	}
}
