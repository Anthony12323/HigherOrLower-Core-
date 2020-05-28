package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.Random;

public class Play implements Screen {
    HigherOrLower game;

    // Instance Variables
    SpriteBatch batch;
    BitmapFont font;
    Texture background;
    Texture[] cards;
    Rectangle displayCard;
    Rectangle[] hitboxes;
    int[] deck;
    int currentCardValue;
    Texture currentCardImg;
    Texture hitbox;
    Texture exitBtn;
    Texture OneUp;
    int score = 0;
    int strikes = 0;
    int points;
    float time;
    int oneUps;
    int multiplier;

    // Constructor
    public Play(HigherOrLower game, int p, int ones, int mult) {
        Random rand = new Random();
        int curIndex = rand.nextInt((51 - 0) + 1) + 0;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("Play.png"));
        hitbox = new Texture(Gdx.files.internal("badlogic.jpg"));
        OneUp = new Texture(Gdx.files.internal("OneUp.png"));
        font = new BitmapFont();
        cards = new Texture[52];
        deck = new int[52];
        displayCard = new Rectangle((1280 / 2) - 100, 200, 500/2, 726/2);
        currentCardValue = curIndex / 4;
        createCards();
        currentCardImg = cards[curIndex];
        createDeck();
        hitboxes = new Rectangle[4];
        createHitboxes();
        exitBtn = new Texture(Gdx.files.internal("ExitBtn.png"));
        points = p;
        oneUps = ones;
        multiplier = mult;

        this.game = game;
    }

    /*
        render method draws to the screen the score, the high score, the card, as well as the up and down arrows, the exit button,
        and a health potion. The potion, the arrows and exit are buttons. This method handles the main logic of the game as well.
        If the person guesses higher but the next card is lower they gain a strike, if they guess correctly they gain a point. After 3 strikes
        the game ends. If they use a health potion they lose a strike.
     */
    @Override
    public void render(float delta) {
        time += delta;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.getData().setScale(2f);
        renderHitboxes();
        batch.draw(background,0,0);
        renderCards();
        font.draw(batch, "" + score, 250, 650);
        font.draw(batch, "" + strikes, 250, 565);
        font.setColor(0f,0f,0f,1f);
        batch.draw(exitBtn, hitboxes[2].x, hitboxes[2].y, hitboxes[2].width, hitboxes[2].height);
        batch.draw(OneUp, hitboxes[3].x, hitboxes[3].y, hitboxes[3].width, hitboxes[3].height);
        font.draw(batch, "" + oneUps, hitboxes[3].x + 40, hitboxes[3].y + 35);
        batch.end();
        if(strikes < 3) {
            if((Gdx.input.getX() >= hitboxes[0].x && Gdx.input.getX() <= hitboxes[0].x + hitboxes[0].width) && (720 - Gdx.input.getY() >= hitboxes[0].y && 720 - Gdx.input.getY() <= hitboxes[0].y + hitboxes[0].height)) {
                if (Gdx.input.isTouched() && time > 0.5f) { // if down arrow is clicked
                    time = 0;
                    Random random = new Random();
                    int newIndex = random.nextInt((51 - 0) + 1) + 0;
                    int newValue = newIndex / 4;
                    if(currentCardValue == newValue) {

                    } else if (currentCardValue < newValue) {
                        score += 1 * multiplier;
                    } else {
                        strikes++;
                    }
                    currentCardImg = cards[newIndex];
                    currentCardValue = newValue;
                }
            } else if ((Gdx.input.getX() >= hitboxes[1].x && Gdx.input.getX() <= hitboxes[1].x + hitboxes[1].width) && (720 - Gdx.input.getY() >= hitboxes[1].y && 720 - Gdx.input.getY() <= hitboxes[1].y + hitboxes[1].height)) {
                if (Gdx.input.isTouched() && time > 0.5f) { // if up arrow is clicked
                    time = 0;
                    Random random = new Random();
                    int newIndex = random.nextInt((51 - 0) + 1) + 0;
                    int newValue = newIndex / 4;
                    if(currentCardValue == newValue) {

                    } else if (currentCardValue > newValue) {
                        score += 1 * multiplier;
                    } else {
                        strikes++;
                    }
                    currentCardImg = cards[newIndex];
                    currentCardValue = newValue;
                }
            } else if ((Gdx.input.getX() >= hitboxes[2].x && Gdx.input.getX() <= hitboxes[2].x + hitboxes[2].width) && (720 - Gdx.input.getY() >= hitboxes[2].y && 720 - Gdx.input.getY() <= hitboxes[2].y + hitboxes[2].height)) {
                if (Gdx.input.isTouched() && time > 0.5f) { // if exit button is clicked
                    time = 0;
                    game.setScreen(new PlayOptions(game, points, oneUps, multiplier));
                }
            } else if ((Gdx.input.getX() >= hitboxes[3].x && Gdx.input.getX() <= hitboxes[3].x + hitboxes[3].width) && (720 - Gdx.input.getY() >= hitboxes[3].y && 720 - Gdx.input.getY() <= hitboxes[3].y + hitboxes[3].height)) {
                if (Gdx.input.isTouched() && oneUps >= 1 && time > 0.5f) { // if OneUp is used
                    time = 0;
                    oneUps--;
                    strikes--;
                }
            }
        } else {
            game.setScreen(new PlayOptions(game, score + points, oneUps, multiplier));
        }
    }

    //  sets up the deck so the create cards method can properly assign the images in increasing rank order
    public void createDeck() {
        int i = 1;
        int temp = 0;
        while(i < 13) {
            deck[temp] = i;
            temp++;
            if(temp % 4 == 0 && temp != 1) {
                i++;
            }
        }
        for(int x = 0; x < deck.length; x++) { // sets kings from 0 to 13
            if(deck[x] == 0) {
                deck[x] = 13;
            }
        }
    }

    // creates the HitBoxes for the buttons
    public void createHitboxes() {
        hitboxes[0] = new Rectangle(1025, 90, 180, 270); // down arrow
        hitboxes[1] = new Rectangle(1025, 395, 180, 270); // up arrow
        hitboxes[2] = new Rectangle(0,0, 200, 100); // exit button
        hitboxes[3] = new Rectangle(400,0,90,90);
    }

    // draws the buttons to the screen with their assigned hitboxes
    public void renderHitboxes() {
        for(int i = 0; i < hitboxes.length - 2; i++) {
            batch.draw(hitbox, hitboxes[i].x, hitboxes[i].y, hitboxes[i].width, hitboxes[i].height);
        }
    }

    // draws the current card to the screen
    public void renderCards() {
        batch.draw(currentCardImg, displayCard.x, displayCard.y, displayCard.width, displayCard.height);
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

    // disposes all sprites, buttons, hitboxes, fonts and images after the screen changes or game is closed.
    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        batch.dispose();
        hitbox.dispose();
        exitBtn.dispose();
        currentCardImg.dispose();
    }

    // initializes all cards in the deck to contain their respective image
    public void createCards() {
        int i = 0;
        cards[i] = new Texture(Gdx.files.internal("ace_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("ace_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("ace_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("ace_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("2_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("2_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("2_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("2_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("3_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("3_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("3_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("3_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("4_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("4_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("4_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("4_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("5_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("5_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("5_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("5_of_spades.png")); // 20
        i++;
        cards[i] = new Texture(Gdx.files.internal("6_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("6_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("6_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("6_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("7_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("7_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("7_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("7_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("8_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("8_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("8_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("8_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("9_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("9_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("9_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("9_of_spades.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("10_of_clubs.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("10_of_diamonds.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("10_of_hearts.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("10_of_spades.png")); // 40
        i++;
        cards[i] = new Texture(Gdx.files.internal("jack_of_clubs2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("jack_of_diamonds2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("jack_of_hearts2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("jack_of_spades2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("queen_of_clubs2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("queen_of_diamonds2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("queen_of_hearts2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("queen_of_clubs2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("king_of_clubs2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("king_of_diamonds2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("king_of_hearts2.png"));
        i++;
        cards[i] = new Texture(Gdx.files.internal("king_of_spades2.png"));
    }
}
