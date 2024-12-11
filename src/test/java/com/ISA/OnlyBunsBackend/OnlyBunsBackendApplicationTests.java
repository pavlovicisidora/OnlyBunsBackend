package com.ISA.OnlyBunsBackend;

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


@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlyBunsBackendApplicationTests {
	@Autowired
	private UserService userService;

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
			userService.followUser(1, 2);
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

}
