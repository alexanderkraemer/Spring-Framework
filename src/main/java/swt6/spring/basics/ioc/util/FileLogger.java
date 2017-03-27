package swt6.spring.basics.ioc.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.inject.Named;

import org.springframework.stereotype.Component;

//@Component("fileLogger")
@Named //("fileLogger")
@Log(LoggerType.FILE)
public class FileLogger implements Logger {

	private PrintWriter writer;
	
	public FileLogger() {
		init("log.txt");
	}
	
	public FileLogger(String fileName) {
		init(fileName);
	}
	
	public void setFileName(String fileName) {
		init(fileName);
	}
	
	private void init(String fileName) {
		try {
			writer = new PrintWriter(new FileOutputStream(fileName));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		
	}
	
	@Override
	public void log(String msg) {
		writer.println("Log: " + msg);
		writer.flush();
	}
	
	public void close() {
		writer.close();
	}

}
