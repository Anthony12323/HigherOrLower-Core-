package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu implements Screen {
    HigherOrLower game;

    // Instance Variables
    SpriteBatch batch;
    Texture background;
    float time = 0f;
    int oneUps = 0;
    int multiplier = 1;

    // Constructor
    public MainMenu(HigherOrLower game) {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("MainMenu.png"));
        this.game = game;
    }

    /* renders the background image for the start screen. Once the screen is clicked on (anywhere) the game sets the screen
       to the PlayOptions screen. A timer is in place as well in order to prevent bugs.
     */
    @Override
    public void render(float delta) {
        time += delta;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background,0,0);
        batch.end();
        if(Gdx.input.isTouched() && time > 0.2f) {
            game.setScreen(new PlayOptions(game, 0, oneUps, multiplier));
            dispose();
        }
    }

    // Unused LibGdx methods
    @Override
    public void show() { }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }

    // disposes the SpriteBatch and background when the screen changes or game closes.
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
