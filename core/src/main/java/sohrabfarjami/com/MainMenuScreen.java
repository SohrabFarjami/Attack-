package sohrabfarjami.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen implements Screen {
    final Attack game;
    private Stage stage;
    private Table table;
    private Table buttonTable;

    public MainMenuScreen(final Attack game) {
        this.game = game;
        stage = new Stage(new FitViewport(800, 500));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        buttonTable = new Table();

        // table.setDebug(true);
        Skin skin = game.manager.get("uiskin.json", Skin.class);
        TextButton newGame = new TextButton("NewGame", skin);
        TextButton loadButton = new TextButton("Load Save", skin);

        table.add(newGame).size(70, 30);

        buttonTable.add(newGame).size(100, 30).padBottom(10f);
        buttonTable.row();
        buttonTable.add(loadButton).size(100, 30);
        table.add(buttonTable);

        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Json json = new Json();
                FileHandle file = Gdx.files.local("saves/savegame.json");
                GameState loadedGamestate = json.fromJson(GameState.class, file.readString());
                TextureAtlas atlas = game.manager.get("cards.atlas", TextureAtlas.class);
                GameController gameController = new GameController(atlas, loadedGamestate);
                Array<Card> cards = loadedGamestate.getAllCards(); // Comeup with better logic
                for (Card card : cards) {
                    card.setAssetsAfterLoad(atlas);
                }
                for (Player player : loadedGamestate.getPlayers()) {
                    for (Card card : player.getHand()) {
                        System.out.println(card);
                    }
                }

                game.setScreen(new GameScreen(game, gameController));
            }
        });

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.spriteBatch.begin();
        game.font.draw(game.spriteBatch, "welcome", 1f, 1f);
        game.spriteBatch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
