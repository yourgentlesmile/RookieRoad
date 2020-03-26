package group.xc.proxy;

/**
 * 代理类和被代理类在编译期间就确定下来了
 */
public class StaticProxy {
    public static void main(String[] args) {
        NikeClothFactory nikeClothFactory = new NikeClothFactory();
        ProxyClothFactory proxyClothFactory = new ProxyClothFactory(nikeClothFactory);
        proxyClothFactory.produceCloth();
    }
}
interface ClothFactory {
    void produceCloth();
}
class ProxyClothFactory implements ClothFactory {

    private ClothFactory factory;//用被代理对象进行实例化

    public ProxyClothFactory(ClothFactory factory) {
        this.factory = factory;
    }

    @Override
    public void produceCloth() {
        System.out.println("代理工厂做一些准备工作");
        factory.produceCloth();
        System.out.println("代理工厂做一些后续的收尾工作");
    }
}
class NikeClothFactory implements ClothFactory {

    @Override
    public void produceCloth() {
        System.out.println("生产一批衣服");
    }
}
