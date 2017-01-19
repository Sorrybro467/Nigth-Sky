package ds.captainpicard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import ds.captainpicard.utils.TiledObjUtils;

import static ds.captainpicard.utils.Constants.PPM;

public class CaptianPicard extends ApplicationAdapter {

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

    @Override
    public void create() {
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

        TiledObjUtils.parsedTiledObjectLayer(world, map.getLayers().get("world border").getObjects());
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

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
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.applyForceToCenter(0, 300, false);
        }
        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
    }

    public void CameraUpdate(float Delta) {
        Vector3 position = camera.position;
        position.x = player.getPosition().x * PPM;
        position.y = player.getPosition().y * PPM;
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
}
