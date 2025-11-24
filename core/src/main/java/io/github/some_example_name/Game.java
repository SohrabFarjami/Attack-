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

import io.github.some_example_name.GameState.RoundState;

public class Game implements ApplicationListener {
    SpriteBatch spriteBatch;
    TextureAtlas atlas;
    FitViewport viewport;
    Sprite front;
    BitmapFont font;
    Sprite back;
    GameController gameController;
    GameState gameState;
    Player currentPlayer;
    Vector2 touchPos;
    ShapeRenderer shapeRenderer;
    Rectangle button;
    Rectangle passButton;
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
        passButton = new Rectangle(6f,1.5f,0.7f,0.2f);
	
	float i = 2f;
	float distanceMargin = 0.02f;
	for(Card card : gameController.getDeck().getAll()){
		card.setPosition(6f, i);
		i += distanceMargin;
	}
	gameState.getTrumpCard().rotate90(false);
	gameState.getTrumpCard().setPosition(6f - 9f/64f - 10f/64f, 2f - (9f/64f) + distanceMargin);
	gameState.getTrumpCard().turn(false);
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
			System.out.printf("%s clicked! %n",card);
                    	gameController.clickCard(card);
                }
            }

            if(button.contains(touchPos)){
                System.out.println("Play Round!");
		gameController.playRound();
		currentPlayer = gameState.getCurrentPlayer();
            }
	    if(gameState.getRoundState() == RoundState.DEFENDING){
		    if(passButton.contains(touchPos)){
			gameController.clickPass();
		    }
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
    font.draw(spriteBatch, "Player " + gameState.getCurrentPlayer().getPlayer() + "'s turn", 3, 3);
    font.draw(spriteBatch,gameState.getRoundState().toString(), 4, 3);
    font.draw(spriteBatch,"Trump Card",7,4);
    gameState.getTrumpCard().draw(spriteBatch);

    int pos = 2;

    for(Card card : gameController.getDeck().getAll()){
	    card.draw(spriteBatch);
    }

    for(Card card: currentPlayer.getHand()){
        if(gameController.getChosenCards().contains(card)){
            card.setPosition(pos++, 0.1f);
        }else{
            card.setPosition(pos++, 0f);
        }
        card.draw(spriteBatch);
    }
    pos = 2;
    for(Card card : gameState.getRiverCards()){
	card.setPosition(pos++, 2f);
	card.draw(spriteBatch);
    }
    pos = 2;
    for(Card card : gameState.getNextPlayer().getHand()){ // Optimize this garbage code
	    card.setPosition(pos++, 4);
	    card.drawBack(spriteBatch);
    }
    spriteBatch.end();

    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(Color.RED);
    shapeRenderer.rect(button.x,button.y,button.width,button.height);
    if(gameState.getRoundState() == RoundState.DEFENDING){
	    shapeRenderer.rect(passButton.x, passButton.y, passButton.width, passButton.height);
    }
    shapeRenderer.rect(touchPos.x - 0.05f, touchPos.y - 0.05f, 0.1f, 0.1f);
    shapeRenderer.end();
    shapeRenderer.begin(ShapeType.Line);
    for(int y = 0; y < 5 ; y++){
	shapeRenderer.line(0,y,8,y);
    }
    for(int i = 0; i < 8 ; i++){
	    shapeRenderer.line(i,0,i,5);
    }
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

