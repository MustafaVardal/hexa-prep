package com.sample.hexagonal;


import com.sample.hexagonal.api.AssembleAFleet;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class AssembleAFleetFunctionalTest {
    @Test
    void should_assemble_a_fleet_for_1050_passengers() {
        //Given

        var numberOfPassengers = 1050;
        var starships = asList(
                new StarShip("X-wing",0),
                new StarShip("millenium Falcon", 6),
                new StarShip("Rebel transport", 90),
                new StarShip("Mon calamari star cruisers", 1200)
        );
        AssembleAFleet assembleAFleet = new FleetAssembler(() -> starships);
        //When

        Fleet fleet = assembleAFleet.forPassengers(numberOfPassengers);

        //Then
        System.out.println(fleet);
        Assertions.assertThat(fleet.starShips())
                .has(enoughCapacityForThePassengers(numberOfPassengers))
                .allMatch(hasCapacity());

    }

    private Predicate<? super StarShip> hasCapacity() {

        return starShip -> starShip.capacity() > 0;
    }

    private Condition<List<? extends StarShip>> enoughCapacityForThePassengers(int numberOfPassengers) {
        return new Condition<>(starShips -> {
            if (starShips == null || starShips.isEmpty()) {
                return false;
            }
            int totalCapacity = starShips.stream()
                    .mapToInt(StarShip::capacity)
                    .sum();
            return totalCapacity >= numberOfPassengers;
        }, "enough capacity for at least %d passengers", numberOfPassengers);
    }
}
