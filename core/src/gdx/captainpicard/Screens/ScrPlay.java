package gdx.captainpicard.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import gdx.captainpicard.utils.CameraStyles;
import gdx.captainpicard.utils.TiledObjUtils;

import static gdx.captainpicard.utils.Constants.PPM;

import gdx.captainpicard.GamMenu;

public class ScrPlay extends ApplicationAdapter implements Screen {

    GamMenu gamMenu;
    private boolean DEBUG = false;
    private final float SCALE = 2.0f;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tmr;
    private TiledMap map;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player, Platform;
    private SpriteBatch Batch;
    private Texture tex;
    private MapProperties MapPro;
    private int nWidht, nHeight, nJumps;

    public ScrPlay(GamMenu _gamMenu) {  //Referencing the main class.
        gamMenu = _gamMenu;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);

        world = new World(new Vector2(0, -9.8f), false);
        b2dr = new Box2DDebugRenderer();

        player = createBox(140, 140, 32, 32, false);
        Platform = createBox(140, 130, 64, 32, true);

        Batch = new SpriteBatch();
        tex = new Texture("cat.png");

        map = new TmxMapLoader().load("world map.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);
        MapPro = map.getProperties();
        nWidht = MapPro.get("width", Integer.class);
        nHeight = MapPro.get("height", Integer.class);


        TiledObjUtils.parsedTiledObjectLayer(world, map.getLayers().get("world border").getObjects());
        TiledObjUtils.parsedTiledObjectLayer(world, map.getLayers().get("land").getObjects());
    }

    @Override
    public void resize(int width, int heigth) {
        camera.setToOrtho(false, width / SCALE, heigth / SCALE);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        Batch.dispose();
        tmr.dispose();
        map.dispose();
    }

    public void update(float Delta) {
        world.step(1 / 60f, 6, 2);

        float fstartX = camera.viewportWidth / 2;
        float fstartY = camera.viewportHeight / 2;
        CameraStyles.boundary(camera, fstartX, fstartY, nWidht * 75 - fstartX * 2, nHeight * 75 - fstartY * 2);
        CameraUpdate(Delta);
        InputUpdate(Delta);
        tmr.setView(camera);
        Batch.setProjectionMatrix(camera.combined);
    }

    public void InputUpdate(float delta) {
        int horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && nJumps == 0) {
            player.applyForceToCenter(0, 300, false);
            nJumps++;
        }
        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
    }

    public void CameraUpdate(float Delta) {
        Vector3 position = camera.position;
        position.x = camera.position.x + (player.getPosition().x * PPM - camera.position.x) * .1f;
        position.y = camera.position.y + (player.getPosition().y * PPM - camera.position.y) * .1f;
        camera.position.set(position);

        camera.update();

    }

    public Body createBox(int x, int y, int widht, int height, boolean IsStatic) {
        Body pBody;
        BodyDef def = new BodyDef();

        if (IsStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(widht / 2 / PPM, height / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    @Override
    public void show() {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float fDelta) {

        update(fDelta);

        //Render
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tmr.render();
        Batch.begin();
        Batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth() / 2), player.getPosition().y * PPM - (tex.getWidth() / 2));
        Batch.end();


        b2dr.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void hide() {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
