package gdx.captainpicard.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
 //import gdx.captainpicard.Screens.ScrPlay.player;
public class TiledObjUtils {

    public Body body;
    private BodyDef bdef;
    private FixtureDef FixDef;

    public TiledObjUtils() {
    }

    public void parsedTiledObjectLayer(World world, MapObjects objects,boolean isJumped,boolean isBonce) {
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }
           if (isJumped==false){
                bdef = new BodyDef();
            
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            //FixDef = new FixtureDef();
            //FixDef.shape = shape;
           // FixDef.density = 1.0f;
            body.createFixture(shape,1.0f);
            shape.dispose();
           }else if(isJumped==true) {
                bdef = new BodyDef();
            
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            FixDef = new FixtureDef();
            FixDef.shape = shape;
            FixDef.density = 1.0f;
            body.createFixture(FixDef).setUserData(this);
            shape.dispose();
            if (isBonce=true){
               //  player.applyForceToCenter(0, 600, false);
            }
           }
        }
    }

    private ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldvertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldvertices.length; i++) {
            worldvertices[i] = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);

        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldvertices);

        return cs;
    }
}
