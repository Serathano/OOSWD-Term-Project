
package model;


public class ShipStateDead implements ShipState{
    @Override
    public ShipState goNext() {
        return new ShipStateImpervious();
    }
    
    @Override
    public String getImage(boolean IFF){
        if(IFF)
            return "enemy_explosion_0004.png";
        return "Ship_explosion_0004.png";
    }
}
