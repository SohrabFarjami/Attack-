package sohrabfarjami.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PauseScreen {
    private Stage stage;
    private Table table;
    TextureAtlas uiAtlas;
    TextButton passButon;
    private GameState gamestate;

    public PauseScreen(GameController gameController, Attack game, GameScreen gameScreen) {
        gamestate = gameController.getGameState();
        stage = new Stage(new FitViewport(800, 500));
        Gdx.input.setInputProcessor(stage);

        uiAtlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // table.setDebug(true);
        // buttonTable.setDebug(true);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton saveButton = new TextButton("Save", skin);
        TextButton mainMenuButton = new TextButton("Returnt to Menu", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameController.saveGameState();
                System.out.println("saved game");
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.dispose();
                game.setScreen(new MainMenuScreen(game));

            }
        });
        table.add(saveButton).size(70, 30);
        table.row();
        table.add(mainMenuButton).size(70, 30);

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return this.stage;
    }

}
