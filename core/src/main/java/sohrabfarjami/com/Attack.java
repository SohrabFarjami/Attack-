package sohrabfarjami.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Attack extends Game {

    public SpriteBatch spriteBatch;
    public BitmapFont font;
    public FitViewport viewport;
    public TextureAtlas uiAtlas;
    public AssetManager manager;

    public void create() {

        manager = new AssetManager();
        manager.load("cards.atlas", TextureAtlas.class);
        manager.load("uiskin.atlas", TextureAtlas.class);
        manager.load("uiskin.json", Skin.class);
        manager.load("default.fnt", BitmapFont.class);
        manager.finishLoading();

        spriteBatch = new SpriteBatch();
        font = manager.get("default.fnt", BitmapFont.class);
        uiAtlas = manager.get("uiskin.atlas", TextureAtlas.class);
        viewport = new FitViewport(8, 5);

        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {

        super.render(); // important!
    }

    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        uiAtlas.dispose();
    }

}
