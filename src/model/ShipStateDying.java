
package model;


public class ShipStateDying implements ShipState{
    @Override
    public ShipState goNext() {
        return new ShipStateDying2();
    }
    
    @Override
    public String getImage(boolean IFF){
        if(IFF)
            return "enemy_explosion_0001.png";
        return "Ship_explosion_0001.png";
    }
}
