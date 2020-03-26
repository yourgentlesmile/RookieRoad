package group.xc.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class Reflection {
    /**
     * 一些反射的操作
     */
    public static void handbook() {
        Class<Person> clazz = Person.class;
        //获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = clazz.getFields();
        //获取当前运行时类自己声明的所有属性。(不包含父类中声明的属性)
        Field[] declaredFields = clazz.getDeclaredFields();
        Field field = declaredFields[0];
        //获取当前运行时类及其父类中声明为public访问权限的方法
        Method[] methodField = clazz.getMethods();
        //获取当前运行时类自己声明的所有方法。(不包含父类中声明的方法)
        Method[] methodDeclaredField = clazz.getDeclaredMethods();
        //获取构造器 声明为public的
        Constructor<?>[] constructors = clazz.getConstructors();
        //获取当前运行时类自己声明的所有构造器。
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        //获取父类
        Class<? super Person> superclass = clazz.getSuperclass();
        //获取带泛型的父类的泛型
        Type genericSuperclass = clazz.getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        //获取泛型参数，返回的数组是因为，比如map<k,v>,这时候泛型就有多个了
        Type[] actualTypeArguments = paramType.getActualTypeArguments();
        //获取实现的接口,以及父类的接口
        Class<?>[] interfaces = clazz.getInterfaces();
        Class<?>[] classes = clazz.getSuperclass().getInterfaces();
        //获取所在包
        Package aPackage = clazz.getPackage();

        Method method = methodField[0];
        //获取权限修饰符,具体含义可以看Modifier类
        int modifiers = field.getModifiers();
        System.out.println(Modifier.toString(modifiers));
        //获取数据类型
        System.out.println(field.getType());
        //获取变量名
        System.out.println(field.getName());

        /**
         * 注解，权限修饰符，返回值类型，方法名，参数类型，抛出的一样
         */
        //注解
        Annotation[] annotations = method.getAnnotations();

        //权限修饰符
        int modifiers1 = method.getModifiers();
        //返回值类型
        System.out.println(method.getReturnType().getName());
        //方法名
        System.out.println(method.getName());
        //形参列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        //获取异常
        Class<?>[] exceptionTypes = method.getExceptionTypes();
    }
    /**
     * 反射之前，对于Person的操作
     */
    public static void normalUsing() {
        //创建对象
        Person tom = new Person("Tom", 12);

        //通过对象，调用其内部的属性与方法
        tom.age = 10;
        System.out.println(tom.toString());
        //在Person类外部，不可以通过Person类的对象调用其内部私有结构，
        //比如getNation方法以及私有构造器
    }

    /**
     * 使用反射
     */
    public static void usingReflect() throws Exception {
        Class<Person> clazz = Person.class;
        //通过反射，创建Person类的对象
        Constructor<Person> constructor = clazz.getConstructor(String.class, int.class);
        Person tom = constructor.newInstance("Tom", 12);
        System.out.println(tom.toString());

        //通过反射调用对象指定的属性、方法
        Field age = clazz.getDeclaredField("age");
        age.set(tom, 10);
        System.out.println(tom.toString());
        //调用方法
        Method show = clazz.getDeclaredMethod("show");
        show.invoke(tom);

        //通过反射，可以调用Person类的私有结构。比如私有的属性、构造器、方法
        //调用私有的构造器
        Constructor<Person> priCon = clazz.getDeclaredConstructor(String.class);
        priCon.setAccessible(true);
        //newInstance：创建类的实例(运行时的对象)
        /**
         * 要想newInstance()方法正常的创建空参的构造器，要求：
         * 1、运行时类必须提供空参的构造器
         * 2、空参的构造器的访问权限得够，通常设置为public。
         *
         * 在javabean中要求提供一个public的空参构造器，原因：
         * 1、便于通过反射，创建运行时类的对象
         * 2、便于子类继承此运行类时，默认调用super()时，保证父类有此构造器
         */
        clazz.newInstance();
        Person jery = priCon.newInstance("Jery");
        System.out.println(jery.toString());
        //调用私有的属性
        Field priName = clazz.getDeclaredField("name");
        priName.setAccessible(true);
        priName.set(jery, "fake");
        System.out.println(jery.toString());

        //调用私有的方法
        Method showNation = clazz.getDeclaredMethod("getNation");
        showNation.setAccessible(true);
        System.out.println(showNation.invoke(jery));
    }

    public static void main(String[] args) throws Exception {
        usingReflect();
    }
}
