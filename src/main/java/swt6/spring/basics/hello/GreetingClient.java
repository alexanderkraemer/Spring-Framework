package swt6.spring.basics.hello;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GreetingClient {

	public static void main(String[] args) {
//		GreetingServiceImpl service = new GreetingServiceImpl();
//		service.setMassage("Hello");
//		service.sayHello();
		
		try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext("swt6/spring/basics/hello/greetingService.xml")) {
			GreetingService bean = factory.getBean("greetingService", GreetingService.class);
			
			bean.sayHello();
		}
	}

}
