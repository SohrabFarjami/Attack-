package sohrabfarjami.com;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import sohrabfarjami.com.GameState.RoundState;

public class PauseScreen{
	private Stage stage;
	private Table table;
	TextureAtlas uiAtlas;
	TextButton passButon;

	public PauseScreen(GameState gameState) {
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

		saveButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
                System.out.println("saving");
			}
		});

        table.add(saveButton).size(70,30);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    gameState.setPaused(!gameState.getPaused());
                    System.out.println("UnPaused");
                    return true; // Event handled
                }
                return false;
            }
        });


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

