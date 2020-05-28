package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Help implements Screen {

    HigherOrLower game;

    // Instance variables
    private SpriteBatch batch;
    private Texture background;
    private Texture back;
    private Texture textbox;
    private Rectangle hitbox;
    private Rectangle backBtn;
    private int oneUps;
    private int multiplier;
    private int points;
    private int textCounter = 1;
    private float timer;
    private Sound speech;

    // Constructor
    public Help(HigherOrLower game, int p, int ones, int mult) {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("HelpScreen.png"));
        back = new Texture(Gdx.files.internal("badlogic.jpg"));
        textbox = new Texture(Gdx.files.internal("Text1.png"));
        backBtn = new Rectangle(1280-250,0,250,150);
        hitbox = new Rectangle((1280/2)-(578/2),(720/2)-(152/2),578,152); // 578 x 152
        points = p;
        oneUps = ones;
        multiplier = mult;
        speech = Gdx.audio.newSound(Gdx.files.internal("sans.mp3"));
        this.game = game;
    }

    /* This method renders all images and handles input. Such as drawing the background, the buttons, as well as
    adding the sound effects to the help textbox. It also implements a timer that increments every frame in order to prevent the user
    from spamming the text button.
     */
    @Override
    public void render(float delta) {
        timer += delta;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(back, backBtn.x, backBtn.y, backBtn.width, backBtn.height);
        batch.draw(background,0,0);
        batch.draw(textbox, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        batch.end();
        if((Gdx.input.getX() >= hitbox.x && Gdx.input.getX() <= hitbox.x + hitbox.width) && (720-Gdx.input.getY() >= hitbox.y && 720-Gdx.input.getY() <= hitbox.y + hitbox.height)) {
            if(Gdx.input.isTouched() && timer > 0.8f) {
                timer = 0;
                if(textCounter < 13) {
                    speech.play(0.5f);
                    textCounter++;
                    textbox = new Texture(Gdx.files.internal("Text" + textCounter + ".png"));
                }
            }
        } else if((Gdx.input.getX() >= backBtn.x && Gdx.input.getX() <= backBtn.x + backBtn.width) && (720-Gdx.input.getY() >= backBtn.y && 720-Gdx.input.getY() <= backBtn.y + backBtn.height)) {
            if(Gdx.input.isTouched() && timer > 0.5f) {
                timer = 0;
                game.setScreen(new PlayOptions(game, points, oneUps, multiplier));
            }
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
    @Override
    public void dispose() { }
}