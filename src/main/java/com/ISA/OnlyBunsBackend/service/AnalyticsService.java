package com.ISA.OnlyBunsBackend.service;

import java.time.LocalDate;
import java.util.Map;

public interface AnalyticsService {
    Map<String, Object> getAnalytics(String intervalType, LocalDate startDate, LocalDate endDate);
    Map<String, Double> getUserActivityStatistics();
}
