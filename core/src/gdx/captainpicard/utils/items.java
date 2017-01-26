/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.captainpicard.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author forrd6685
 */

//This class is for the key
public class items extends Sprite {

    boolean isCollected = false;
    public static float fX, fY;
    String sFile;
    Texture txImg;
    private Sprite spImg;

    public items(String _sFile, float _fX, float _fY) {
        sFile = _sFile;
        fX = _fX;
        fY = _fY;
        txImg = new Texture(sFile);
        spImg = new Sprite(txImg);
    }

    public Sprite getSprite() {
        return spImg;
    }

    @Override
    public float getY() {
        return fY;

    }

    @Override
    public float getX() {
        return fX;

    }
}
