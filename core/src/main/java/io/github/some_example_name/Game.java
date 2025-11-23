package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Game implements ApplicationListener {
    SpriteBatch spriteBatch;
    TextureAtlas atlas;
    FitViewport viewport;
    Sprite front;
    BitmapFont font;
    Sprite back;
    Deck deck;
    GameController gameController;
    GameState gameState;
    Player currentPlayer;
    Vector2 touchPos;
    ShapeRenderer shapeRenderer;
    Rectangle button;

   public void create () {
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
        atlas = new TextureAtlas(Gdx.files.internal("assets/cards.atlas"));
        gameController = new GameController(atlas);
        gameState = gameController.getGameState();
        viewport = new FitViewport(8, 5);
        touchPos = new Vector2();


        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        currentPlayer = gameState.getCurrentPlayer();

        shapeRenderer = new ShapeRenderer();

        gameState.getTrumpCard().setPosition(7, 3);

        button = new Rectangle(6f,1f,0.7f,0.2f);
   }

   public void render () {
       input();
       logic();
       draw();
   }

   private void input(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            touchPos.set(Gdx.input.getX(),Gdx.input.getY());
            viewport.unproject(touchPos);
            for(Card card : currentPlayer.getHand()){
                if(card.getBoundingRectangle().contains(touchPos)){
                    gameController.clickCard(card);
                    System.out.printf("%s clicked! %n",card);
                }
            }
            if(button.contains(touchPos)){
                System.out.println("Play Round!");
            }
            }


   }

   private void logic(){
   }

   private void draw(){
    ScreenUtils.clear(Color.BLACK);
    viewport.apply();

    spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

    spriteBatch.begin();
    font.draw(spriteBatch, "Player " + gameState.getCurrentPlayer().getPlayer() + "'s turn", 2, 3);
    font.draw(spriteBatch,"Trump Card",7,4);
    gameState.getTrumpCard().draw(spriteBatch);

    int pos = 1;
    for(Card card: currentPlayer.getHand()){
        if(gameController.getChosenCards().contains(card)){
            card.setPosition(pos++, 1.1f);
        }else{
            card.setPosition(pos++, 1f);
        }
        card.draw(spriteBatch);
    }
    spriteBatch.end();

    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(Color.RED);
    shapeRenderer.rect(button.x,button.y,button.width,button.height);
    shapeRenderer.rect(touchPos.x - 0.05f, touchPos.y - 0.05f, 0.1f, 0.1f);
    shapeRenderer.end();
   }

   public void resize (int width, int height) {
       viewport.update(width,height,true);
   }

   public void pause () {
   }

   public void resume () {
   }

   public void dispose () {
       spriteBatch.dispose();
       atlas.dispose();
   }
}

