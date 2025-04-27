package com.sample.hexagonal.api;

import com.sample.hexagonal.Fleet;

public interface AssembleAFleet {
    Fleet forPassengers(int numberOfPassengers);
}
