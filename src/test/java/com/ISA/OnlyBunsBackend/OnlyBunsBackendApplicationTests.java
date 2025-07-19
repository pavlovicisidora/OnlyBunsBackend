package com.ISA.OnlyBunsBackend;

import com.ISA.OnlyBunsBackend.dto.LocationDTO;
import com.ISA.OnlyBunsBackend.dto.UserRegistration;
import com.ISA.OnlyBunsBackend.service.PostService;
import com.ISA.OnlyBunsBackend.service.UserService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlyBunsBackendApplicationTests {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Test(expected = PessimisticLockingFailureException.class)
	public void testPessimisticLockingScenario() throws Throwable {

		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(() -> {
			System.out.println("Started Thread 1");
			userService.followUser(1, 2);
		});
		Future<?> future2 = executor.submit(() -> {
			System.out.println("Started Thread 2");
			try {
				Thread.sleep(200);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			userService.followUser(4, 2);
		});
		try {
			future1.get();
			future2.get();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getCause().getClass());
			throw e.getCause();
		}
		executor.shutdown();
	}

	@Test
	public void testConcurrentLikes() throws InterruptedException {
		// Pokretanje dva niti za testiranje konkurentnog lajkovanja
		Thread thread1 = new Thread(() -> postService.likePost(1, 1));
		Thread thread2 = new Thread(() -> postService.likePost(1, 2));

		thread1.start();
		thread2.start();

		// Čekanje da se obe niti završe
		thread1.join();
		thread2.join();


		int likeCount =postService.getPostById(1).getLikeCount();

		assertEquals(3, likeCount);
	}

	@Test(expected = RuntimeException.class)
	public void RateLimiterBreaks() throws InterruptedException {
		// Postavljanje komentara do 5 komentara
		postService.addComment(1, 1, "Komentar 1");
		postService.addComment(1, 1, "Komentar 2");
		postService.addComment(1, 1, "Komentar 3");
		postService.addComment(1, 1, "Komentar 4");
		postService.addComment(1, 1, "Komentar 5");

		// Očekujemo da 6. komentar baci RuntimeException
		postService.addComment(1, 1, "Komentar 6");
	}

	@Test
	public void testConcurrentRegistration() throws InterruptedException {
		final boolean[] conflictOccurred = {false};

		Runnable registrationTask = () -> {
			try {
				UserRegistration req = new UserRegistration();
				req.setUsername("conflict_user");
				req.setPassword("pass123");
				req.setFirstName("Test");
				req.setLastName("User");
				req.setEmail("test@example.com");

				LocationDTO loc = new LocationDTO();
				loc.setCity("Belgrade");
				loc.setCountry("Serbia");
				req.setLocation(loc);

				userService.save(req);
				System.out.println("Registracija uspešna.");
			} catch (RuntimeException ex) {
				System.out.println("Registracija nije uspela: " + ex.getMessage());
				if (ex.getMessage().contains("exists")) {
					conflictOccurred[0] = true;
				}
			}
		};

		Thread thread1 = new Thread(registrationTask);
		Thread thread2 = new Thread(registrationTask);

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		assertTrue("Bar jedna registracija je trebalo da ne uspe zbog konflikta korisničkog imena", conflictOccurred[0]);
	}


}
