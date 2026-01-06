package sohrabfarjami.com;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Attack extends Game {

    public SpriteBatch spriteBatch;
    public BitmapFont font;
    public FitViewport viewport;
    public TextureAtlas uiAtlas;

    public void create() {
        spriteBatch = new SpriteBatch();
        // use libGDX's default font
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);

        uiAtlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));

        // font has 15pt, but we need to scale it to our viewport by ratio of viewport
        // height to screen height
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
    }

}
