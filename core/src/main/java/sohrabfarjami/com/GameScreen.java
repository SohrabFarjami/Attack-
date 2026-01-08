package sohrabfarjami.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    Attack game;
    TextureAtlas atlas;
    FitViewport viewport;
    Sprite front;
    Sprite back;
    GameController gameController;
    GameState gameState;
    Vector2 touchPos;
    Sprite sprite;
    Array<Card> cards;
    AnimationController animationController;
    boolean clicked = false; // Remove this later
    Ui ui;
    PauseScreen pauseScreen;
    InputMultiplexer multiplexer;

    public GameScreen(final Attack game) {
        this(game, null);
    }

    public GameScreen(final Attack game, GameController loadedGameController) {
        // TODO change this fucking stupid gamestate gamecontroller logic
        this.game = game;

        atlas = game.manager.get("cards.atlas", TextureAtlas.class);

        if (loadedGameController == null) {
            gameController = new GameController(atlas, gameState);
        } else {
            gameController = loadedGameController;
        }

        gameState = gameController.getGameState();

        if (loadedGameController == null) {
            cards = new Array<>(gameController.getDeck().getAll()); // MAKESURE THIS IS BEFORE!! START GAME //TODO
                                                                    // change logic
            gameController.startGame();
        } else {
            cards = gameState.getAllCards();
        }

        animationController = gameController.getAnimationController();

        viewport = new FitViewport(8, 5);
        touchPos = new Vector2();

        ui = new Ui(gameController, gameState); // remove gamecontroller dependencies
        pauseScreen = new PauseScreen(gameController, game, this);
        gameController.addRoundStateListener(ui);
        gameController.addWarnable(ui);

        gameState.getTrumpCard().rotate90(false);
        gameState.getTrumpCard().setPosition(Position.DECK.x - 9f / 64f - 10f / 64f, 2f - (9f / 64f) + 0.02f); // Todo
        // remove
        // this
        // implementation
        gameState.getTrumpCard().turn(false);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(ui.getStage());
        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        if (gameState.getPaused() == false) {
            input();
            draw();
            ui.render();
        } else {
            pauseScreen.render();
        }
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameState.setPaused(!gameState.getPaused());
            if (gameState.getPaused() == true) {
                multiplexer.addProcessor(pauseScreen.getStage());
            } else {
                multiplexer.removeProcessor(pauseScreen.getStage());
            }
        }
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

    private void draw() {
        float delta = Gdx.graphics.getDeltaTime();
        viewport.apply();
        game.spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        game.spriteBatch.begin();

        for (Card card : cards) {
            card.draw(game.spriteBatch);
        }

        for (Card card : gameState.getCurrentPlayer().getHand()) {
        }

        animationController.update(delta);

        game.spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ui.resize(width, height);
    }

    public void pause() {
    }

    public void resume() {
    }

    public void dispose() {
        ui.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }
}
