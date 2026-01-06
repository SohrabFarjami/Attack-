package sohrabfarjami.com;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;

public class Main implements ApplicationListener {
	SpriteBatch spriteBatch;
	TextureAtlas atlas;
	FitViewport viewport;
	Sprite front;
	BitmapFont font;
	Sprite back;
	GameController gameController;
	GameState gameState;
	Vector2 touchPos;
	Sprite sprite;
	List<Card> cards;
	AnimationController animationController;
	boolean clicked = false; // Remove this later
	Ui ui;
    PauseScreen pauseScreen;

	public void create() {
		font = new BitmapFont();
		spriteBatch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("cards.atlas"));

		gameController = new GameController(atlas);
		cards = new ArrayList<>(gameController.getDeck().getAll()); // MAKESURE THIS IS BEFORE!! START GAME
		gameController.startGame();
		gameState = gameController.getGameState();

		animationController = gameController.getAnimationController();

		viewport = new FitViewport(8, 5);
		touchPos = new Vector2();

		font.setUseIntegerPositions(false);
		font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

		ui = new Ui(gameController,gameState);
        pauseScreen = new PauseScreen(gameState);
		gameController.addRoundStateListener(ui);
		gameController.addWarnable(ui);

		gameState.getTrumpCard().rotate90(false);
		gameState.getTrumpCard().setPosition(Position.DECK.x - 9f / 64f - 10f / 64f, 2f - (9f / 64f) + 0.02f); // Todo
															// remove
															// this
															// implementation
		gameState.getTrumpCard().turn(false);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(pauseScreen.getStage());
        multiplexer.addProcessor(ui.getStage());
        Gdx.input.setInputProcessor(multiplexer);

	}

	public void render() {
        if(gameState.getPaused() == false){
            input();
            logic();
            draw();
            ui.render();
        }else{
            pauseScreen.render();
        }
	}

	private void input() {
		if (Gdx.input.justTouched()) {
			clicked = true;
			touchPos.set(Gdx.input.getX(), Gdx.input.getY());
			viewport.unproject(touchPos);

			gameController.click(touchPos); // TODO move to controller

		}

		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY());
			viewport.unproject(touchPos);
			gameController.drag(touchPos);
		} else if (clicked) {
			clicked = false;
			gameController.releaseDrag();
		} else {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY());
			viewport.unproject(touchPos);
			gameController.mouseAt(touchPos);
		}
	}

	private void logic() {
	}

	private void draw() {
		ScreenUtils.clear(Color.BLACK);
		float delta = Gdx.graphics.getDeltaTime();
		viewport.apply();

		spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

		spriteBatch.begin();

		for (Card card : cards) {
			card.draw(spriteBatch);
		}

		animationController.update(delta);

		spriteBatch.end();
	}

	public void resize(int width, int height) {
		viewport.update(width, height, true);
		ui.resize(width, height);
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		spriteBatch.dispose();
		atlas.dispose();
		ui.dispose();
	}
}
