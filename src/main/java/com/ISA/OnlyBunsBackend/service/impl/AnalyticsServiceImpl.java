package com.ISA.OnlyBunsBackend.service.impl;

import com.ISA.OnlyBunsBackend.repository.CommentRepository;
import com.ISA.OnlyBunsBackend.repository.PostRepository;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Object> getAnalytics(String intervalType, LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            Optional<LocalDate> earliestPostDate = postRepository.findEarliestPostDate();
            Optional<LocalDate> earliestCommentDate = commentRepository.findEarliestCommentDate();

            startDate = Stream.of(earliestPostDate, earliestCommentDate)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .min(LocalDate::compareTo)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No posts or comments found."));
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        List<Object[]> postCounts = postRepository.countPostsByInterval(intervalType, startDate, endDate);
        List<Object[]> commentCounts = commentRepository.countCommentsByInterval(intervalType, startDate, endDate);

        Map<String, Object> analyticsData = new HashMap<>();
        Map<String, Object> postsData = postCounts.stream().collect(Collectors.toMap(
                result -> result[0].toString(),  // Interval
                result -> result[1]             // Post count
        ));

        Map<String, Object> commentsData = commentCounts.stream().collect(Collectors.toMap(
                result -> result[0].toString(),  // Interval
                result -> result[1]             // Comment count
        ));

        analyticsData.put("posts", postsData);
        analyticsData.put("comments", commentsData);

        return analyticsData;
    }

    @Override
    public Map<String, Double> getUserActivityStatistics() {
        long totalUsers = userRepository.countUsers();
        long usersWithPosts = postRepository.countDistinctUsersWithPosts();
        long usersWithOnlyComments = commentRepository.countDistinctUsersWithComments() - usersWithPosts;
        long inactiveUsers = totalUsers - usersWithPosts - usersWithOnlyComments;

        Map<String, Double> statistics = new HashMap<>();
        statistics.put("Users with Posts", (usersWithPosts * 100.0) / totalUsers);
        statistics.put("Users with Only Comments", (usersWithOnlyComments * 100.0) / totalUsers);
        statistics.put("Inactive Users", (inactiveUsers * 100.0) / totalUsers);

        return statistics;
    }
}
