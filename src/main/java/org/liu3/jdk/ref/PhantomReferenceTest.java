package org.liu3.jdk.ref;

import org.apache.commons.io.FileCleaner;
import org.apache.commons.io.FileCleaningTracker;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liutianshuo
 * @Date: 2021/5/20
 */
public class PhantomReferenceTest {


    public static void main(String[] args) {
//        例子jdk之中就有不少，比如sunjdk里面的DirectByteBuffer的Cleaner，
//        rmi的客户端dgc里面的PhantomLiveRef等等，
//        另外还有commons io里面的FileCleaningTracker。

        FileCleaner fc = new FileCleaner();
        fc.getInstance().track("c:\\java\\1.txt", new Object());
        fc.getInstance().track("c:\\java\\2.txt",new Object());
        System.gc();
        //System.exit(0);


        FileCleaningTracker fct = new FileCleaningTracker();

        Object value = new Object();
        ReferenceQueue queue = new ReferenceQueue();

        Thread thread = new Thread(() -> {
            try {
                int cnt = 0;
                WeakReference<byte[]> k;
                while((k = (WeakReference) queue.remove()) != null) {
                    System.out.println((cnt++) + "回收了:" + k);
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        Map<WeakReference, Object> map = new HashMap<>();
        for(int i = 0;i < 100;i++) {
            byte[] bs = new byte[1024*1024];
            WeakReference<byte[]> ref = new WeakReference<byte[]>(bs, queue);
            map.put(ref, i);
        }
        System.out.println("map.size->" + map.size());

        for(Map.Entry<WeakReference, Object> en : map.entrySet()){
            System.out.println(en.getKey().get()+" "+en.getValue());
        }

    }
}
