package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HigherOrLower extends Game {
	// Instance variables
	SpriteBatch batch;
	private Music music;

	/* create method acts as a constructor, mainly to set the screen to the mainMenu, and initialize variables.
	   This whole class mainly acts as a blueprint for all screens to go off of. Allowing the game to jump from screen to screen.
	 */
	@Override
	public void create () {
		batch = new SpriteBatch();
		music = Gdx.audio.newMusic(Gdx.files.internal("Cantina.mp3"));
		music.setLooping(true);
		music.setVolume(0.05f);
		music.play();
		this.setScreen(new MainMenu(this));
	}

	// renders using the Game classes render method
	public void render() {
		super.render();
	}

	// disposes of the SpriteBatch when the game is closed
	public void dispose() {
		batch.dispose();
	}

}
