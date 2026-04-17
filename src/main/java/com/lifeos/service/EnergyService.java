package com.lifeos.service;

import java.util.UUID;

public interface EnergyService {

    int calculateEnergyScore(UUID userId, int caloriesIn);

    String getEnergyLevel(int energyScore);


}
