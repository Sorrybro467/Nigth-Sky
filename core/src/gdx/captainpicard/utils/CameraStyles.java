/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.captainpicard.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import static gdx.captainpicard.utils.Constants.PPM;

/**
 * @author burss9866
 */
public class CameraStyles {

    public static void lerptotarget(Camera camera, Vector2 player) {
        Vector3 position = camera.position;
        
        position.x = camera.position.x + (player.x - camera.position.x) * .1f;
        position.y = camera.position.y + (player.y - camera.position.y) * .1f;
        camera.position.set(position);
        camera.update();

    }
    public static void boundary(Camera camera, float fstartX, float fstartY, float fWidht, float fHeight) {
        Vector3 position = camera.position;
        if (position.x < fstartX) {
            position.x = fstartX;
        }
        if (position.y < fstartY) {
            position.y = fstartY;
        }
        if (position.x > fstartX + fWidht) {
            position.x = fstartX + fWidht;
        }
        if (position.y > fstartY + fHeight) {
            position.y = fstartY + fHeight;
        }
        camera.position.set(position);
        camera.update();
    }
}
