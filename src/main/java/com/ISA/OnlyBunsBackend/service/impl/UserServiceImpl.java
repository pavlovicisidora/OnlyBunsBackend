package com.ISA.OnlyBunsBackend.service.impl;

import java.time.LocalDateTime;
import java.util.*;

import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.model.Location;
import com.ISA.OnlyBunsBackend.model.Post;
import com.ISA.OnlyBunsBackend.model.Role;
import com.ISA.OnlyBunsBackend.model.User;
import com.ISA.OnlyBunsBackend.repository.UserRepository;
import com.ISA.OnlyBunsBackend.service.EmailService;
import com.ISA.OnlyBunsBackend.service.RoleService;
import com.ISA.OnlyBunsBackend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ISA.OnlyBunsBackend.dto.UsersViewDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleService roleService;
    @Autowired
    private LocationServiceImpl locationServiceImpl;

    // Mapa koja čuva listu vremenskih oznaka praćenja po korisničkom ID-ju
    private final Map<Integer, Queue<LocalDateTime>> followAttempts = new ConcurrentHashMap<>();

    private static final int FOLLOW_LIMIT = 50;
    private static final int TIME_WINDOW_MINUTES = 1;

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	public User findById(int id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	public List<User> findAll() throws AccessDeniedException {
		return userRepository.findAll();
	}

	@Override
	public User save(UserRegistration userRequest) {
		User u = new User();
		u.setUsername(userRequest.getUsername());
		
		// pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
		// treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		
		u.setFirstName(userRequest.getFirstName());
		u.setLastName(userRequest.getLastName());
		Location location = locationServiceImpl.findById(userRequest.getLocation().getId());
		u.setLocation(location);
		u.setActivated(false);
		u.setEmail(userRequest.getEmail());

		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		Role role = roleService.findByName("ROLE_USER");
		u.setRole(role);
		
		return this.userRepository.save(u);
	}


	@Override
	public User updateUser(User updatedUser) throws AccessDeniedException {
		return userRepository.save(updatedUser);
	}

    @Override
    public Integer getFollowersCount(Integer userId) {
        return userRepository.countFollowers(userId);
    }

    @Override
    public Page<UsersViewDTO> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.getContent().stream()
                .filter(user -> !user.isDeleted())
                .map(user -> {
                    UsersViewDTO userDTO = new UsersViewDTO();
                    userDTO.setId(user.getId());
                    userDTO.setEmail(user.getEmail());
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setFollowersCount(getFollowersCount(user.getId()));
                    userDTO.setPostCount(user.getPostCount());
                    return userDTO;
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), list ->
                        new PageImpl<>(list, pageable, usersPage.getTotalElements())
                ));
    }

    @Override
    public Page<UsersViewDTO> searchUsers(Pageable pageable, String firstName, String lastName, String email, String minPosts, String maxPosts, String sortBy, String sortDirection) {
        List<User> users = userRepository.findByCriteria(firstName, lastName, email);
        if (minPosts != null || maxPosts != null) {
            int parsedMinPosts = (minPosts != null && !minPosts.isEmpty()) ? Integer.parseInt(minPosts) : 0;
            int parsedMaxPosts = (maxPosts != null && !maxPosts.isEmpty()) ? Integer.parseInt(maxPosts) : Integer.MAX_VALUE;

            users = users.stream()
                    .filter(user -> {
                        int postCount = user.getPostCount();
                        boolean validMinPosts = postCount >= parsedMinPosts;
                        boolean validMaxPosts = postCount <= parsedMaxPosts;
                        return validMinPosts && validMaxPosts;
                    })
                    .collect(Collectors.toList());
        }
        List<UsersViewDTO> userDTOs = new ArrayList<>();
        if(sortBy == "" || sortBy == null)
            sortBy = "followersCount";
        if(sortDirection == "" || sortDirection == null)
            sortDirection = "ASC";
        Comparator<User> comparator;
        if ("followersCount".equals(sortBy)) {
            comparator = Comparator.comparing(user -> getFollowersCount(user.getId()));
        } else if ("email".equals(sortBy)) {
            comparator = Comparator.comparing(User::getEmail);
        } else {
            comparator = Comparator.comparing(User::getId); // Default sort by ID
        }

        if ("DESC".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }

        users.sort(comparator);

        for (User user : users) {
            if(user.isDeleted())
                continue;
            UsersViewDTO userDTO = new UsersViewDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setFollowersCount(getFollowersCount(user.getId()));
            userDTO.setPostCount(user.getPostCount());
            userDTOs.add(userDTO);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), userDTOs.size());
        List<UsersViewDTO> pagedContent = userDTOs.subList(start, end);
        return new PageImpl<>(pagedContent, pageable, userDTOs.size());
    }

    @Override
    public UsersViewDTO getUser(Integer id) {
        List<User> users = userRepository.findAll();
        UsersViewDTO userDTO = new UsersViewDTO();

        for(User user : users) {
            if(user.getId().equals(id)) {
                userDTO.setId(user.getId());
                userDTO.setEmail(user.getEmail());
                userDTO.setFirstName(user.getFirstName());
                userDTO.setLastName(user.getLastName());
                userDTO.setFollowersCount(user.getFollowersCount());
                userDTO.setPostCount(user.getPostCount());
            }
        }
        return userDTO;
    }

    public void deleteInactiveUsers() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        List<User> inactiveUsers = userRepository.findByIsActivatedFalseAndCreatedAtBefore(thirtyDaysAgo);

        for (User user : inactiveUsers) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }

    @Scheduled(cron = "0 0 0 28 * ?")
    //@Scheduled(cron = "0 41 12 18 * ?")
    public void scheduleInactiveUserDeletion() {
        deleteInactiveUsers();
    }

    private boolean canFollow(Integer userId) {
        Queue<LocalDateTime> attempts = followAttempts.computeIfAbsent(userId, k -> new ConcurrentLinkedQueue<>());
        LocalDateTime now = LocalDateTime.now();

        // uklanjam stare pokusaje - pre vise od minut
        while (!attempts.isEmpty() && attempts.peek().isBefore(now.minusMinutes(TIME_WINDOW_MINUTES))) {
            attempts.poll();
        }

        if (attempts.size() < FOLLOW_LIMIT) {
            attempts.add(now);
            return true;
        }
        return false;
    }

    @Override
    public UsersViewDTO followUser(Integer followerId, Integer followedId) {
        if (!canFollow(followerId)) {
            throw new IllegalStateException("Reached follow limit. Please try again later.");
        }
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new EntityNotFoundException("User to follow not found"));

        if (follower.getFollowings().contains(followed)) {
            throw new IllegalStateException("Already following this user");
        }

        follower.getFollowings().add(followed);
        //followed.getFollowers().add(follower);
        followed.setFollowersNum(followed.getFollowersNum() + 1);
        userRepository.save(follower);
        userRepository.save(followed);
        return new UsersViewDTO(followed);
    }

    @Override
    public UsersViewDTO unfollowUser(Integer followerId, Integer followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("Follower not found"));
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new EntityNotFoundException("User to unfollow not found"));

        follower.getFollowings().remove(followed);
        //followed.getFollowers().remove(follower);

        followed.setFollowersNum(followed.getFollowersNum() - 1);
        userRepository.save(follower);
        userRepository.save(followed);
        return new UsersViewDTO(followed);
    }

    @Override
    public boolean isFollowing(int followerId, int followedUserId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new EntityNotFoundException("Logged in user not found"));
        User followed = userRepository.findById(followedUserId)
                .orElseThrow(() -> new EntityNotFoundException("User to follow/unfollow not found"));

        return follower.getFollowings().contains(followed);
    }

    @Override
    public List<UsersViewDTO> getFollowingUsers(Integer userId) {
        List<User> followings = userRepository.findFollowingUsers(userId);
        return followings.stream()
                .filter(user -> !user.isDeleted())
                .map(user -> {
                    UsersViewDTO userDTO = new UsersViewDTO();
                    userDTO.setId(user.getId());
                    userDTO.setEmail(user.getEmail());
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setFollowersCount(getFollowersCount(user.getId()));
                    userDTO.setPostCount(user.getPostCount());
                    return userDTO;
                }).toList();
    }
}

