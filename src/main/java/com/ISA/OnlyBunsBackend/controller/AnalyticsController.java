package com.ISA.OnlyBunsBackend.controller;

import com.ISA.OnlyBunsBackend.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(value = "api/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/posts-comments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAnalytics(
            @RequestParam String interval,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        Map<String, Object> analyticsData = analyticsService.getAnalytics(interval, startDate, endDate);
        return ResponseEntity.ok(analyticsData);
    }

    @GetMapping("/user-activity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getUserActivityStatistics() {
        return ResponseEntity.ok(analyticsService.getUserActivityStatistics());
    }
}
