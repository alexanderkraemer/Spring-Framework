package swt6.spring.basics.hello;

public class HtmlGreetingServiceImpl implements GreetingService {

	private String message;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public void sayHello() {
		System.out.format("<html>%n <body> <p>%s</p> </body> </html>", this.message);

	}

}
