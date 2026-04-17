package com.lifeos.service;


import java.util.UUID;

import com.lifeos.dto.BehaviorInsight;


public interface BehaviorService {


    BehaviorInsight analyzeBehavior(UUID userId);
}
