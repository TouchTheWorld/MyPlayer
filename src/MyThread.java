import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by namercx on 2016/11/18.
 */
public class MyThread implements Runnable {
    TreeMap lyricMap =null;
    TreeMap timeMap =null;

    public MyThread( TreeMap lyricMap, TreeMap timeMap){
        this.lyricMap = lyricMap;
        this.timeMap = timeMap;
    }
    @Override
    public void run() {
            Set set=lyricMap.keySet();
            Iterator<Integer> it=set.iterator();
            while(it.hasNext()){
                int key = it.next();
                long time = Long.parseLong(timeMap.get(key).toString());
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(lyricMap.get(key));
            }
    }
}
