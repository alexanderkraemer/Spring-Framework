package swt6.spring.basics.hello;

public class GreetingServiceImpl implements GreetingService {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void sayHello() {
		System.out.println(this.message);
	}
}
