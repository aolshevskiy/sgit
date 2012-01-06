package sgit.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.matcher.Matcher;

class MarkerUpdater implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invokation) throws Throwable {
		Object result = invokation.proceed();		
		File f = new File("app.init");
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		f.setLastModified(System.currentTimeMillis());
		return result;
	}
}

class InitMatcher implements Matcher<Method> {

	@Override
	public Matcher<Method> and(Matcher<? super Method> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean matches(Method meth) {
		return meth.getName().equals("init");			 
	}

	@Override
	public Matcher<Method> or(Matcher<? super Method> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
