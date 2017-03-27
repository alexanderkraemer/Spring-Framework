package swt6.spring.basics.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component @Aspect
public class TraceAdvice {
	@DeclareParents(value="swt6.spring.basics.aop.logic..*", defaultImpl=TraceOptionsDefaultImpl.class)
	private static TraceOptions mixin;
	
	private boolean isTracingEnabledInProxy(JoinPoint jp) {
		return ((TraceOptions) jp.getThis()).isTracingEnabled();
	}
	
	@Pointcut("execution(public * swt6.spring.basics.aop.logic..*find*(..))")
	private void findMethods() {}

	@Before("swt6.spring.basics.aop.advice.TraceAdvice.findMethods()")
	public void traceBefore(JoinPoint jp) {
		if(isTracingEnabledInProxy(jp)) {
			String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
			System.out.println("--> " + methodName);
		}
	}
	
	@AfterReturning("findMethods()")
	public void traceAfter(JoinPoint jp) {
		if(isTracingEnabledInProxy(jp)) {
			String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
			System.out.println("<-- " + methodName);
		}
	}
	
	@Around("execution(public * swt6.spring.basics.aop.logic..*find*ById*(..))")
	public Object traceAround(ProceedingJoinPoint jp) throws Throwable {
		String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
		
		if(isTracingEnabledInProxy(jp)) {
			System.out.println("==> " + methodName);
		}
		
		Object retVal = jp.proceed();
		
		if(isTracingEnabledInProxy(jp)) {
			System.out.println("<== " + methodName);
		}
		
		return retVal;
	}
	
	@AfterThrowing(pointcut="execution(public * swt6.spring.basics.aop.logic..*find*(..))", throwing="exception")
	public void traceException(JoinPoint jp, Throwable exception) {
		String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
		
		if(isTracingEnabledInProxy(jp)) {
			System.out.printf("##> %s%n threw exception <%s>%n", methodName, exception);
		}
	}
}
