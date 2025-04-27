package com.sample.hexagonal;

import com.sample.hexagonal.api.AssembleAFleet;
import com.sample.hexagonal.spi.StarShipInventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FleetAssembler implements AssembleAFleet {

    private StarShipInventory starshipsInventory;

    public FleetAssembler(StarShipInventory starshipsInventory) {
        this.starshipsInventory = starshipsInventory;
    }

    @Override
    public Fleet forPassengers(int numberOfPassengers) {

        List<StarShip> starShips = getStarShipsHavingPassengersCapacity();
        List<StarShip> rescueStarShips = selectStarShips(numberOfPassengers, starShips);
        return new Fleet(rescueStarShips);
    }

    private List<StarShip> selectStarShips(int numberOfPassengers, List<StarShip> starShips) {

        List<StarShip> rescueStarShips = new ArrayList<>();
        while (numberOfPassengers >0){
            var starShip = starShips.remove(0);
            numberOfPassengers -= starShip.capacity();
            rescueStarShips.add(starShip);
        }
        return rescueStarShips;
    }

    private List<StarShip> getStarShipsHavingPassengersCapacity() {
        return starshipsInventory.starShips().stream()
                .filter(starShip -> starShip.capacity()>0)
                .sorted(Comparator.comparingInt(StarShip::capacity))
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
