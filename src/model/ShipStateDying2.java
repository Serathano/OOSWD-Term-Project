
package model;


public class ShipStateDying2 implements ShipState{
    @Override
    public ShipState goNext() {
        return new ShipStateDying3();
    }
    
    @Override
    public String getImage(boolean IFF){
        if(IFF)
            return "enemy_explosion_0002.png";
        return "Ship_explosion_0002.png";
    }
}
