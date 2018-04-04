package com.folaukaveinga;

import java.net.InetAddress;
import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.folaukaveinga.model.User;
import com.folaukaveinga.service.UserService;
import com.folaukaveinga.utility.PasswordUtil;
import com.folaukaveinga.utility.RandomGeneratorUtil;


@SpringBootApplication
public class AuthenticationServerApplication  {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServerApplication.class, args);
	}
	
	@Profile({"local","dev"})
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.info("Loading test data...");
			// load test data
			try {
				loadTestData();
				log.info("Test data loaded!");
			} catch (Exception e) {
				log.warn("Error occurred while loading test data, {}", e.getLocalizedMessage());
			}
			// Display Environmental Useful Variables
			try {
				System.out.println("\n");
				Runtime runtime = Runtime.getRuntime();
				double mb = 1048576;// megabtye to byte
				double gb = 1073741824;// gigabyte to byte
				Environment env = ctx.getEnvironment();

				System.out.println("************************ Sidecar Health - Expenses ***************************");
				System.out.println("** Active Profile: " + Arrays.toString(env.getActiveProfiles()));
				System.out.println("** Port: " + env.getProperty("server.port"));
				System.out.println("** External Url: " + InetAddress.getLocalHost().getHostAddress() + ":"
						+ env.getProperty("server.port")+env.getProperty("server.servlet.context-path"));
				System.out.println("** Internal Url: http://localhost:" + env.getProperty("server.port")+env.getProperty("server.servlet.context-path"));
				
				System.out.println("************************* Java - JVM ***********************");
				System.out.println("** Number of processors: " + runtime.availableProcessors());
				System.out.println("** Total memory: " + (double) (runtime.totalMemory() / mb) + " MB = "
						+ (double) (runtime.totalMemory() / gb) + " GB");
				System.out.println("** Max memory: " + (double) (runtime.maxMemory() / mb) + " MB = "
						+ (double) (runtime.maxMemory() / gb) + " GB");
				System.out.println("** Free memory: " + (double) (runtime.freeMemory() / mb) + " MB = "
						+ (double) (runtime.freeMemory() / gb) + " GB");
				System.out.println("************************************************************");
			} catch (Exception e) {
				System.err.println("Exception, commandlineRunner -> " + e.getMessage());
			}
			System.out.println("\n");
		};
	}
	
	
	//====================================Load Test Data===============================
	@Autowired
	private UserService userService;
	
	private void loadTestData() {
		loadUser();
	}
	
	int numberOfUsers = 5;
	
	private void loadUsers() {
		log.info("loading User test data...");
		
		String plainTextPassword = "test12";//PasswordUtil.getRandomTempPassword();
		log.info("Plain Text Password: {}", plainTextPassword);
		String hashedPassword = PasswordUtil.hashPassword(plainTextPassword);
		log.info("Hashed Password: {}", hashedPassword);
		
		for (int i = 1; i <= numberOfUsers; i++) {
			User user = new User();
			user.setFirstName(RandomGeneratorUtil.getString(8));
			user.setLastName(RandomGeneratorUtil.getString(8));
			user.setUsername(RandomGeneratorUtil.getString(5)+"@gmail.com");
			user.setPassword(hashedPassword);
			user.addRole(User.USER);
			
			log.info(user.toJson());
			try {
				userService.save(user);
			} catch (Exception e) {
				log.warn("Loading Error, msg: {}",e.getLocalizedMessage());
			}
			
		}
		log.info("Expense test data loaded");
	}
	
	private void loadUser() {
		log.info("loading User test data...");
		
		String plainTextPassword = "test12";//PasswordUtil.getRandomTempPassword();
		log.info("Plain Text Password: {}", plainTextPassword);
		String hashedPassword = PasswordUtil.hashPassword(plainTextPassword);
		log.info("Hashed Password: {}", hashedPassword);
		User user = new User();
		user.setFirstName(RandomGeneratorUtil.getString(8));
		user.setLastName(RandomGeneratorUtil.getString(8));
		user.setUsername("folau@gmail.com");
		user.setPassword(hashedPassword);
		user.addRole(User.USER);
		
		log.info(user.toJson());
		try {
			userService.save(user);
		} catch (Exception e) {
			log.warn("Loading Error, msg: {}",e.getLocalizedMessage());
		}
		log.info("Expense test data loaded");
	}
	
}
