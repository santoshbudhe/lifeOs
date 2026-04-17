package com.logyourmeals.service;

import java.util.UUID;

public interface RewardService {

    void updateReward(UUID userId);

    String getRewardMessage(UUID userId);

}
