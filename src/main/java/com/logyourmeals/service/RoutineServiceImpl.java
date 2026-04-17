package com.logyourmeals.service;

import com.logyourmeals.entity.RoutineLog;
import com.logyourmeals.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;

    @Override
    public void logRoutine(UUID userId) {

        int hour = LocalDateTime.now().getHour();

        String routineType;

        if (hour >= 6 && hour <= 10) {
            routineType = "MORNING";
        } else if (hour >= 11 && hour <= 15) {
            routineType = "AFTERNOON";
        } else if (hour >= 16 && hour <= 20) {
            routineType = "EVENING";
        } else {
            routineType = "NIGHT";
        }

        RoutineLog log = RoutineLog.builder()
                .userId(userId)
                .routineType(routineType)
                .hourOfDay(hour)
                .timestamp(LocalDateTime.now())
                .build();

        routineRepository.save(log);
    }

    @Override
    public String detectRoutineWindow(UUID userId) {

        List<RoutineLog> logs = routineRepository.findByUserId(userId);

        if (logs.isEmpty()) return null;

        Map<String, Long> frequencyMap = logs.stream()
                .collect(Collectors.groupingBy(RoutineLog::getRoutineType, Collectors.counting()));

        return frequencyMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
