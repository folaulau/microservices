package com.folaukaveinga.webclient;

import java.net.InetAddress;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class WebClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebClientApplication.class, args);
	}

	@Profile({"local","dev"})
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			try {
				System.out.println("\n");
				Runtime runtime = Runtime.getRuntime();
				double mb = 1048576;// megabtye to byte
				double gb = 1073741824;// gigabyte to byte
				Environment env = ctx.getEnvironment();

				System.out.println("************************ Restaurant Client ***************************");
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
}
