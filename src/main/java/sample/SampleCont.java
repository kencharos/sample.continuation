package sample;

import org.apache.commons.javaflow.api.Continuation;
import org.apache.commons.javaflow.api.continuable;

class Process implements Runnable {

    public @continuable void run() {

        System.out.println("\tStart, and suspend");

        // suspendで実行停止。
        Object fromCaller = Continuation.suspend("1");

        // 呼び出し元から resumeしたら再開

        System.out.println("\tResume process at " + fromCaller);
    }
}

public class SampleCont {

    public static void main(String[] args) {

        SampleCont cont = new SampleCont();

        System.out.println("start process");
        Continuation cc = Continuation.startWith(new Process());

        System.out.println("yield value [" + cc.value() + "] when process suspended");

        cc.resume(" go resume!!");

        System.out.println("again cc resume!");

        cc.resume(" go resume 2");

    }
}

