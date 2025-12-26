package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



public class Ui{
	private Stage stage;
	private Table table;
	private Table playRoundTable;
    	TextureAtlas uiAtlas;
	Table buttonTable;
	TextureRegion upRegion;
	TextureRegion downRegion;
	public Ui (GameController gameController) {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		uiAtlas = new TextureAtlas(Gdx.files.internal("assets/uiskin.atlas"));

		table = new Table();
		table.setFillParent(true);
		buttonTable = new Table();
		stage.addActor(table);

		table.setDebug(true); // This is optional, but enables debug lines for tables.

		// Add widgets to the table here.
		Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
		TextButton button1 = new TextButton("Button", skin);
		button1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				gameController.playRound();
			}
		});
		buttonTable.add(button1).bottom();
		table.add(buttonTable).bottom().right().pad(1);
	}

	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void render () {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
	}
}
