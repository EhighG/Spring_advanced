package hello.advanced.v1;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThreadLocalService {

    // with ThreadLocal
    private ThreadLocal<List<String>> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("저장 name={} -> nameStore={}", name, nameStore.get());
//        nameStore.set(name);
        List<String> strings = nameStore.get();
        if (strings == null) strings = new ArrayList<>();
        strings.add(name);
        nameStore.set(strings);
        sleep(1000);
        log.info("조회 nameStore={}",nameStore.get());
        return "done";
    }

    // without ThreadLocal

//    private List<String> nameStore = new ArrayList<>();
//
//    public String logic(String name) {
//        log.info("저장 name={} -> nameStore={}", name, nameStore);
//        nameStore.add(name);
//        sleep(1000);
//        log.info("조회 nameStore={}",nameStore);
//        return "done";
//    }


    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}