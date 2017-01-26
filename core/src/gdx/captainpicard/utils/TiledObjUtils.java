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
public class TiledObjUtils {
//-------------------------------------Souces-----------------------------------------
    //https://www.youtube.com/watch?v=6tLUekJSDB4 
    //https://www.youtube.com/watch?v=BcbjBEnIWKU
    public Body body;
    private BodyDef bdef;
    private FixtureDef FixDef;

    public TiledObjUtils() {
    }
// what this funtion does is that it gets the objects from tiled and makes them satic (soild)
    public void parsedTiledObjectLayer(World world, MapObjects objects, boolean isJumped, boolean isBounce) {
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                continue;
            }
            if (isJumped == false) {// this checks to see if it can be jumped off
                
                bdef = new BodyDef();
                bdef.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(bdef);
                body.createFixture(shape, 1.0f);
                shape.dispose();
            } else if (isJumped == true) {
                 if (isBounce == true) {//this is for the mushrooms so they are bounce
                    bdef = new BodyDef();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    body = world.createBody(bdef);
                    FixDef = new FixtureDef();
                    FixDef.shape = shape;
                    FixDef.density = 1.0f;
                    FixDef.restitution = 2;
                    body.createFixture(FixDef).setUserData(this);
                    shape.dispose();
                } 
               if (isBounce==false){
                    bdef = new BodyDef();
                    bdef.type = BodyDef.BodyType.StaticBody;
                    body = world.createBody(bdef);
                    FixDef = new FixtureDef();
                    FixDef.shape = shape;
                    FixDef.density = 1.0f;
                    body.createFixture(FixDef).setUserData(this);
                    shape.dispose();
                }
            }
        }
    }
//takes the object from tiled and makes it a shape
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
