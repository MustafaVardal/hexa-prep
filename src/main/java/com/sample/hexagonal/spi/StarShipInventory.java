package com.sample.hexagonal.spi;

import com.sample.hexagonal.StarShip;

import java.util.List;

public interface StarShipInventory {
    List<StarShip> starShips();
}
