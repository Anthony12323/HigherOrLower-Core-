package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayOptions implements Screen {
    HigherOrLower game;

    // Instance variables
    private SpriteBatch batch;
    private Texture background;
    private Texture exitBtn;
    private Texture hitbox;
    private Texture helpBtn;
    private Rectangle exit;
    private Rectangle help;
    private Rectangle[] hitboxes;
    private int points = 0;
    private int oneUps;
    private int multiplier ;
    private float timer = 0f;

    // Constructor
    public PlayOptions(HigherOrLower game, int p, int ones, int mult) {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("PlayOptions.png"));
        hitbox = new Texture(Gdx.files.internal("badlogic.jpg"));
        exitBtn = new Texture(Gdx.files.internal("ExitBtn.png"));
        helpBtn = new Texture(Gdx.files.internal("Help.png"));
        help = new Rectangle(1280-100, 720-89,100,89);
        exit = new Rectangle(1080, 0, 200, 100);
        hitboxes = new Rectangle[2];
        hitboxes[0] = new Rectangle(215,285,300,150);
        hitboxes[1] = new Rectangle(780,285,300,150);
        this.game = game;
        points = p;
        oneUps = ones;
        multiplier = mult;
    }

    // draws all buttons to the screen and handles their input accordingly when they are clicked. Uses a timer to prevent bugs
    @Override
    public void render(float delta) {
        timer += delta;
        Gdx.gl.glClearColor(1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        renderHitboxes();
        batch.draw(background,0,0);
        batch.draw(exitBtn, exit.x, exit.y, exit.width, exit.height);
        batch.draw(helpBtn, help.x, help.y, help.width, help.height);
        batch.end();
        if((Gdx.input.getX() >= 1080 && Gdx.input.getX() <= 1080 + 200) && (720-Gdx.input.getY() >= 0 && 720-Gdx.input.getY() <= 0 + 100)) {
            if(Gdx.input.isTouched() && timer > 0.8f) {
                timer = 0;
                Gdx.app.exit();
            }
        } else if((Gdx.input.getX() >= hitboxes[0].x && Gdx.input.getX() <= hitboxes[0].x + hitboxes[0].width) && (720-Gdx.input.getY() >= hitboxes[0].y && 720-Gdx.input.getY() <= hitboxes[0].y + hitboxes[0].height)) {
            if(Gdx.input.isTouched() && timer > 0.8f) {
                timer = 0;
                game.setScreen(new Play(game, points, oneUps, multiplier));
            }
        } else if((Gdx.input.getX() >= hitboxes[1].x && Gdx.input.getX() <= hitboxes[1].x + hitboxes[1].width) && (720-Gdx.input.getY() >= hitboxes[1].y && 720-Gdx.input.getY() <= hitboxes[1].y + hitboxes[1].height)) {
            if(Gdx.input.isTouched() && timer > 0.8f) {
                timer = 0;
                game.setScreen(new Shop(game, points, oneUps, multiplier));
            }
        } else if((Gdx.input.getX() >= help.x && Gdx.input.getX() <= help.x + help.width) && (720-Gdx.input.getY() >= help.y && 720-Gdx.input.getY() <= help.y + help.height)) {
            if(Gdx.input.isTouched() && timer > 0.8f) {
                timer = 0;
                game.setScreen(new Help(game, points, oneUps, multiplier));
            }
        }
    }

    // draws the the HitBoxes to the screen with their respective button images
    public void renderHitboxes() {
        for(int i = 0; i < hitboxes.length; i++) {
            batch.draw(hitbox, hitboxes[i].x, hitboxes[i].y, hitboxes[i].width, hitboxes[i].height);
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

    // Disposes the batch and background image after the screen changes or the game closes
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
