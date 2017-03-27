package swt6.spring.basics.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.WorkLogImplJavaConfigBased;
import swt6.spring.basics.ioc.logic.WorkLogImplXmlConfigBased;
import swt6.spring.basics.ioc.util.ConsoleLogger;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;

@Configuration
@ComponentScan(basePackageClasses=WorkLogConfig.class)
public class WorkLogConfig {

//	@Bean @Log
//	public Logger consoleLogger() {
//		return new ConsoleLogger();
//	}
	
	@Bean
	public WorkLogFacade workLog() {
	//	return new WorkLogImplJavaConfigBased(consoleLogger());
		return new WorkLogImplJavaConfigBased();

	}
}
