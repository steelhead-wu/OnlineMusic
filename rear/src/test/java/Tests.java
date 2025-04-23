import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.ToolCallback;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @time 2025/2/25 19:23 周二
 */
/*
<T> T可以传入任何类型的list
        关于参数T的说明：
        第一个T表示<T>是一个泛型
        第二个T表示方法返回的是T类型的数据
*/
public class Tests {




    public static void main(String[] args) throws InterruptedException {
        Flux.range(4, 1)
                .subscribe()
        ;


        FunctionTest<Integer, String> ft = new Tests.FunctionTest<Integer, String>();
        ft.func(i -> i * 2 + "", 61);
    }

    @Test
    void security() {
//        PasswordEncoder passwordEncoder =
//                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        ThreadLocal<Integer> integerThreadLocal = ThreadLocal.withInitial(() -> 30);
        ThreadLocal<Integer> integerThreadLocal1 = new ThreadLocal<>();
//        integerThreadLocal.set(20);
        integerThreadLocal.remove();
        System.out.println(integerThreadLocal.get());
    }


    static class FunctionTest<T, R> { // 1
        // 将行为进行抽象
        public void func(Function<T, R> function, T t) {
            R r = function.apply(t);
            System.out.println(r);
        }
    }


    @Test
    public void singerOnTest() {
        Flux<Integer> flux = Flux.range(0, 2)
                .publishOn(Schedulers.single())
                .map(i -> {
                    System.out.println("Mapping for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                });

        flux.subscribe();
    }

    @org.junit.jupiter.api.Test
    public void subscriptOnTest() {
        Flux.just("tom")
                .map(s -> {
                    System.out.println("[map] Thread name: " + Thread.currentThread().getName());
                    return s.concat("@mail.com");
                })
                .publishOn(Schedulers.newSingle("thread-publishOn"))
                .filter(s -> {
                    System.out.println("[filter] Thread name: " + Thread.currentThread().getName());
                    return s.startsWith("t");
                })
                .subscribeOn(Schedulers.newSingle("thread-subscribeOn"))
                .subscribe(s -> {
                    System.out.println("[subscribe] Thread name: " + Thread.currentThread().getName());
                    System.out.println(s);
                });
    }

    @org.junit.jupiter.api.Test
    public void publishOnTest() throws InterruptedException {
        Flux<Integer> flux = Flux.range(0, 2)
                .map(i -> {
                    System.out.println("Mapping for " + i + " is done by thread " + Thread.currentThread().getName());
                    return i;
                });


        //create a runnable with flux subscription
        Runnable r = () -> flux.subscribe(s -> {
            System.out.println("Received " + s + " via " + Thread.currentThread().getName());
        });

        Thread thread = new Thread(r);
        thread.join();
        thread.start();

    }


}

interface MyEventListener<T> {
    void onDataChunk(List<T> chunk);

    void processComplete();
}

class SampleSubscriber<T> extends BaseSubscriber<T> {
    /*
    开始订阅的时候调用
     */
    @Override
    public void hookOnSubscribe(Subscription subscription) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Subscribed");
        request(1);

    }


    // 下一个元素的时候调用
    @Override
    public void hookOnNext(T value) {
        System.out.println(value);
        request(1);
    }

    // 没有出现异常且完成的时候调用
    @Override
    protected void hookOnComplete() {
        System.out.println("done");
    }

    // 出现异常的时候调用
    @Override
    protected void hookOnError(Throwable throwable) {
        System.out.println(throwable);
    }


}
