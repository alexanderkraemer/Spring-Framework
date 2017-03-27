package swt6.spring.basics.aop.advice;

import org.aspectj.lang.annotation.Pointcut;

public class TraceOptionsDefaultImpl implements TraceOptions {

	private boolean isTracingEnabled = false;
	
	@Override
	public boolean isTracingEnabled() {
		//return isTracingEnabled;
		return true;
	}

	@Override
	public void disableTracing() {
		isTracingEnabled = false;
	}

	@Override
	public void enableTracing() {
		isTracingEnabled = true;
	}

}
