
package ContactListeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Manifold;
import static gdx.captainpicard.utils.Constants.jumps;

public class MyContactListeners implements ContactListener  {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa= contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        if (fa==null||fb==null)return;
        if (fa.getUserData()==null||fb.getUserData()==null)return;
        
        System.out.println("something hit");
        jumps=0;
        
                
       
        
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa= contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        if (fa==null||fb==null)return;
        if (fa.getUserData()==null||fb.getUserData()==null)return;
        
        System.out.println("something is not hitting");
        jumps=1;
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
       
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
        
    }
    
}
