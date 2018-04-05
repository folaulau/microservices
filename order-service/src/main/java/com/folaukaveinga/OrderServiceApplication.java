package com.folaukaveinga;

import java.net.InetAddress;
import java.util.Arrays;

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

import com.folaukaveinga.model.Order;
import com.folaukaveinga.service.OrderService;
import com.folaukaveinga.utility.RandomGeneratorUtil;

@SpringBootApplication
public class OrderServiceApplication {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
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

				System.out.println("************************ Restaurant Order Service ***************************");
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
	private OrderService orderService;
	
	private void loadTestData() {
		loadOrders();
	}
	
	int numberOfUsers = 5;
	
	private void loadOrders() {
		log.info("loading Order test data...");
		
		for (int i = 1; i <= numberOfUsers; i++) {
			Order order = new Order();
			order.setItem(RandomGeneratorUtil.getString(9));
			order.setUserId(RandomGeneratorUtil.getIntegerWithin(1, 50));
			try {
				order = orderService.save(order);
				log.info("index: {}, {}", i, order);
			} catch (Exception e) {
				log.warn("Loading Error, msg: {}",e.getLocalizedMessage());
			}
			
		}
		log.info("Expense test data loaded");
	}
	
	
}
