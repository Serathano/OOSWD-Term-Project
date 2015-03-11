
package model;


public class ShipStateImpervious implements ShipState{
    @Override
    public ShipState goNext() {
        return new ShipStateAlive();
    }
    
    @Override
    public String getImage(boolean IFF){
        if(IFF)
            return "level_marker_0002";//should neve be called.
        return "Ship_Red.png";
    }
}
