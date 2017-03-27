package swt6.spring.basics.ioc.util;

import javax.inject.Named;

import org.springframework.stereotype.Component;

//@Component("consoleLogger") 	//spring
@Named //("consoleLogger")			//CDI
@Log
public class ConsoleLogger implements Logger {

	private String prefix = "Log";

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	 
	public void log(String msg) {
		System.out.format("%s: %s%n", prefix, msg);
	}
}
