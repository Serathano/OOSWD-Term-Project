
package model;

interface ShipState {
    ShipState goNext();
    String getImage(boolean IFF);
}
