package sohrabfarjami.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import sohrabfarjami.com.GameState.RoundState;

public class Ui implements RoundStateListener, Warnable {
    private Stage stage;
    private Table table;
    TextureAtlas uiAtlas;
    Table buttonTable;
    TextButton passButon;
    Label warning;
    Container<Label> warningContainer;

    public Ui(GameController gameController, GameState gameState) {
        stage = new Stage(new FitViewport(800, 500));
        Gdx.input.setInputProcessor(stage);

        uiAtlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));

        table = new Table();
        table.setFillParent(true);
        buttonTable = new Table();
        stage.addActor(table);

        // table.setDebug(true);
        // buttonTable.setDebug(true);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        warning = new Label("This is a label", skin);
        warningContainer = new Container<>(warning);
        TextButton playButton = new TextButton("Button", skin);
        passButon = new TextButton("Pass", skin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameController.playRound();
            }
        });

        passButon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameController.clickPass();
                ;
            }
        });

        warningContainer.setOrigin(Align.center);
        warningContainer.setPosition(400, 350);
        warning.setColor(1, 1, 1, 0);

        stage.addActor(warningContainer);

        buttonTable.add(passButon).size(70, 30).padBottom(30);
        buttonTable.row();
        buttonTable.add(playButton).bottom().size(70, 30);
        stage.addActor(buttonTable);
        buttonTable.setPosition(600, 150);

        passButon.setVisible(false); // Comeup better solution
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

    @Override
    public void notifyStateChange(RoundState newState) {
        if (newState == RoundState.DEFENDING) {
            passButon.setVisible(true);
        } else {
            passButon.setVisible(false);
        }
    }

    @Override
    public void warn(String warning) {
        this.warning.setText(warning);
        showWarning();
    }

    // TODO remove these hardcodes
    public void showWarning() {
        warning.clearActions();
        warning.addAction(Actions.sequence(
                Actions.fadeIn(0.1f),
                Actions.delay(1.3f),
                Actions.fadeOut(0.4f)));
    }

    public Stage getStage() {
        return this.stage;
    }
}
