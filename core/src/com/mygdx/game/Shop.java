package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Shop implements Screen {

    // Instance Variables
    private HigherOrLower game;
    private SpriteBatch batch;
    private int points;
    private Texture background;
    private BitmapFont font;
    private float time = 0f;
    private Rectangle[] hitboxes;
    private Texture box;
    private int oneUps;
    private int multiplier;

    // Constructor
    public Shop(HigherOrLower game, int p, int ones, int mult) {
        batch = new SpriteBatch();
        font = new BitmapFont();
        points = p;
        background = new Texture(Gdx.files.internal("Store.png"));
        box = new Texture(Gdx.files.internal("badlogic.jpg"));
        hitboxes = new Rectangle[3];
        createHitboxes();
        oneUps = ones;
        multiplier = mult;

        this.game = game;
    }

    // renders the fonts and calls the render and input checking methods.
    @Override
    public void render(float delta) {
        time += delta;
        Gdx.gl.glClearColor(1f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.getData().setScale(2f);
        renderHitboxes();
        batch.draw(background,0,0);
        font.draw(batch, "" + points, 550, 670);
        font.draw(batch, "current multiplier: " + multiplier + "x", 980,670);
        font.setColor(0f,0f,0f,1f);
        checkButtonPress();
        batch.end();
    }

    // creates the HitBoxes for their respective buttons
    public void createHitboxes() {
        hitboxes[0] = new Rectangle(1280-250,0,250,150); // back button
        hitboxes[1] = new Rectangle(720,140,275,150); // multiplier
        hitboxes[2] = new Rectangle(720,420,280,150); // +1 up
    }

    // draws the buttons to the screen using their HitBoxes
    private void renderHitboxes() {
        for(int i = 0; i < hitboxes.length; i++) {
            batch.draw(box, hitboxes[i].x, hitboxes[i].y, hitboxes[i].width, hitboxes[i].height);
        }
    }

    // handles input and the logic that occurs as a result of those inputs, such as making purchases in the shop.
    public void checkButtonPress() {
        if((Gdx.input.getX() >= hitboxes[0].x && Gdx.input.getX() <= hitboxes[0].x + hitboxes[0].width) && (720-Gdx.input.getY() >= hitboxes[0].y && 720-Gdx.input.getY() <= hitboxes[0].y + hitboxes[0].height)) {
            if(Gdx.input.isTouched() && time > 0.5f) {
                time = 0;
                game.setScreen(new PlayOptions(game, points, oneUps, multiplier));
            }
        } else if((Gdx.input.getX() >= hitboxes[1].x && Gdx.input.getX() <= hitboxes[1].x + hitboxes[1].width) && (720-Gdx.input.getY() >= hitboxes[1].y && 720-Gdx.input.getY() <= hitboxes[1].y + hitboxes[1].height)) {
            if(Gdx.input.isTouched() && points >= 40 && time > 0.5f) {
                time = 0;
                points -= 40;
                multiplier *= 2;
            }
        } else if((Gdx.input.getX() >= hitboxes[2].x && Gdx.input.getX() <= hitboxes[2].x + hitboxes[2].width) && (720-Gdx.input.getY() >= hitboxes[2].y && 720-Gdx.input.getY() <= hitboxes[2].y + hitboxes[2].height)) {
            if(Gdx.input.isTouched() && points >= 15 && time > 0.5f) {
                time = 0;
                points -= 15;
                oneUps++;
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

    // disposes the SpriteBatch and images/fonts after the screen changes or the game is closed
    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
        box.dispose();
    }
}
