package me.duzhi.demo.hm;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main1(String[] args) {

        long t1 = System.currentTimeMillis();
        HMMap map = new HMHashMap();
        for (int i = 0; i < 1000; i++) {
            map.put("key+" + i, "value" + i);
        }
        System.out.println(map.size());
        for (int i = 0; i < 1000; i++) {
            System.out.println("key:"+("key+" + i)+":"+map.get("key+" + i));
        }
        long t2 = System.currentTimeMillis();
        System.out.println("===================================================ssssssssssss:"+(t2-t1));
        System.out.println("hashmap+=");
        long t3 = System.currentTimeMillis();
        Map map1 = new HashMap();
        for (int i = 0; i < 1000; i++) {
            map1.put("key+" + i, "value" + i);
        }
        System.out.println(map1.size());
        for (int i = 0; i < 1000; i++) {
            System.out.println("key:"+("key+" + i)+":"+map1.get("key+" + i));
        }
        long t4 = System.currentTimeMillis();
        System.out.println("===================================================ssssssssssss::"+(t4-t3));
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        HMMap map = new HMHashMap();
        for (int i = 0; i < 1000; i++) {
            map.put("key+" + i, "value" + i);
        }

        System.out.println(map.size());
        map.remove("key+"+321);
        System.out.println(map.size());
        for (int i = 0; i < 1000; i++) {
            System.out.println("key:"+("key+" + i)+":"+map.get("key+" + i));
        }
        long t2 = System.currentTimeMillis();
        System.out.println("===================================================ssssssssssss:"+(t2-t1));
    }
}
