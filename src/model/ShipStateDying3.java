
package model;


public class ShipStateDying3 implements ShipState{
    @Override
    public ShipState goNext() {
        return new ShipStateDead();
    }
    
    @Override
    public String getImage(boolean IFF){
        if(IFF)
            return "enemy_explosion_0003.png";
        return "Ship_explosion_0003.png";
    }
}
