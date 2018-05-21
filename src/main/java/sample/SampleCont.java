package sample;

import org.apache.commons.javaflow.api.Continuation;
import org.apache.commons.javaflow.api.continuable;

import java.io.*;
import java.util.concurrent.ExecutionException;

class Process implements Runnable,Serializable {

    public @continuable void run() {

        System.out.println("\t継続処理開始、suspendで一時停止");

        // suspendで実行停止。
        Object fromCaller = Continuation.suspend("1");

        // 呼び出し元から resumeしたら再開
        System.out.println("\tsuspendした場所から、 " + fromCaller + " を受け取って再開しました。");
    }
}

public class SampleCont {

    public static void main(String[] args) throws Exception{

        Thread.sleep(5000);
        SampleCont cont = new SampleCont();

        System.out.println("継続処理の呼び出し");
        Continuation cc = Continuation.startWith(new Process());

        System.out.println("\n継続からSuspendしたときに、 " + cc.value() + " を取得。resumeで再開します。");
        cc.resume("A");

        System.out.println("\nsuspendした 継続をもう一度再開します。");
        cc.resume("B");

        System.out.println("\nsuspendした継続をシリアライズ/デシリアライズして再開します。");
        serialize(cc);
        Continuation ccLoaded = (Continuation) load();

        ccLoaded.resume("C");

    }
    public static void serialize(Object obj) throws Exception{
        // シリアライズ
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cont.bin"))) {
            oos.writeObject(obj);
        }
    }
    public static Object load() throws Exception{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("cont.bin"))) {
            return ois.readObject();

        }

    }


}

