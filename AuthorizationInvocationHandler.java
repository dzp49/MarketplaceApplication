import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AuthorizationInvocationHandler implements InvocationHandler, Serializable {
	private static final long serialVersionUID = 1L;
	private Object objectImpl;
 
	public AuthorizationInvocationHandler(Object impl) {
	   this.objectImpl = impl;
	}
	 
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.isAnnotationPresent(RequiresRole.class)) {
			RequiresRole test = method.getAnnotation(RequiresRole.class);
			Account session = (Account) args[0];
			if (session.getUserType().equals(test.value())) //ensures access rights
				{
					try {
				 		return method.invoke(objectImpl, args);
					}catch (InvocationTargetException e){
						throw e.getCause();
					}
           		 }else {
            	throw new AuthorizationException(method.getName());
            }
		} else {
			try {
				return method.invoke(objectImpl, args);
			}catch (InvocationTargetException e){
				throw e.getCause();
			}
		}   
	}
}