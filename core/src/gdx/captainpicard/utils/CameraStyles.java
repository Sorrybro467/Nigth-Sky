/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.captainpicard.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * @author burss9866
 */
public class CameraStyles {

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
