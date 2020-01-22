package group.xc.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 */
public class MetaspaceOOM {
    static class OOMTest {

    }

    /**
     * -XX:MetaspaceSize=5m -XX:MaxMetaspaceSize=5m
     * @param args
     */
    public static void main(final String[] args) {
        int i = 0;
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, objects);
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("=-=-=-=-=-=" + i);
            e.printStackTrace();
        }
    }
}
