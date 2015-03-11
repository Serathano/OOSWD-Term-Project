
package model;


public class ShipStateAlive implements ShipState{
    @Override
    public ShipState goNext() {
        return new ShipStateDying();
    }
    
    @Override
    public String getImage(boolean IFF){
        if(IFF)
            return "level_marker_0002";//should neve be called.
        return "Ship_White.png";
    }
}
