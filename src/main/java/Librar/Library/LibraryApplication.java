package Librar.Library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
@EntityScan(basePackages = "Librar.Library.Modes") // Adjust if necessary

@SpringBootApplication(scanBasePackages = "Librar.Library")
@EnableAspectJAutoProxy
public class LibraryApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}
}
