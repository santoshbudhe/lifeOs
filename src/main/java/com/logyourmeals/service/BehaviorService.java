package com.logyourmeals.service;


import com.logyourmeals.dto.BehaviorInsight;


import java.util.UUID;


public interface BehaviorService {


    BehaviorInsight analyzeBehavior(UUID userId);
}
