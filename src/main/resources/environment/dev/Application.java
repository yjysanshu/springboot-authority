import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan(value = {
		"com.temp.permission.mapper",
		"com.temp.generator.mapper",
		"com.temp.common.mapper"
})
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(com.temp.Application.class, args);
	}
}