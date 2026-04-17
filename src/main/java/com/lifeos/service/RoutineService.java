package com.lifeos.service;

import java.util.UUID;

public interface RoutineService {

    void logRoutine(UUID userId);

    String detectRoutineWindow(UUID userId);

}
