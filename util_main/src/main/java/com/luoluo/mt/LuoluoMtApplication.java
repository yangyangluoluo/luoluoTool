package com.luoluo.mt;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
@EnableSwagger2
@EnableKnife4j
@SpringBootApplication
public class LuoluoMtApplication {

	public static void main(String[] args) throws UnknownHostException {

		ConfigurableApplicationContext application = SpringApplication.run(LuoluoMtApplication.class, args);
		Environment env = application.getEnvironment();
		log.info("\n----------------------------------------------------------\n\t" +
						"应用 '{}' 运行成功! 访问连接:\n\t" +
						"Swagger文档: \t\thttp://{}:{}/doc.html\n" +
						"----------------------------------------------------------",
				env.getProperty("spring.application.name"),
				InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
		log.info(LuoluoMtApplication.class.getSimpleName() + " is success!");
	}

}
