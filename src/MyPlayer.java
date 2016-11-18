import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by namercx on 2016/11/18.
 */
public class MyPlayer {
    private String songName =null;
    private String singer =null;
    private String songMaker = null;
    private String lyricMaker = null;
    private TreeMap lyricMap =new TreeMap();
    private TreeMap timeMap = new TreeMap();
    private Player myMusic =null;
    public void lyric() {
        File f = new File("H:\\A half\\a half\\洛天依 - 一半一半.lrc");
        Scanner scan = null;
        try {
            scan = new Scanner(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scan.hasNext()) {
            String str = scan.next();
            if (str.contains("[ti"))
                songName = str.substring(4, str.length() - 1);
            else if (str.contains("[ar"))
                singer = str.substring(4, str.length() - 1);
            else if (str.contains("[al:"))
                songMaker = str.substring(4, str.length() - 1);
            else if (str.contains("[by:"))
                lyricMaker = str.substring(4, str.length() - 1);
            else {
                String[] piece = str.split("\\[|\\]");
                if(!piece[piece.length - 1].contains(":")){
                    String lineLyric = piece[piece.length - 1];
                    for(int i = piece.length - 2; i>=0; i--){
                        if(piece[i].contains(":")){
                            String[] pie = piece[i].split(":");
                            int key = Integer.parseInt(pie[0])*60*1000+ (int)(Double.parseDouble(pie[1])*1000);
                            lyricMap.put(key,lineLyric);
                        }
                    }
                }
            }
        }
        ArrayList<Integer> keys = new ArrayList<>();
        Set set=lyricMap.keySet();
        for (Object aSet : set) {
            keys.add((int) aSet);
        }
        Iterator it=set.iterator();
        for(int i=0; i<keys.size(); i++) {
            if(i==0)  timeMap.put(it.next(),keys.get(0));
            else {
                timeMap.put(it.next(),keys.get(i)-keys.get(i-1));
            }
        }
    }
    public MyPlayer() {
        lyric();
        File f = new File("H:\\A half\\a half\\洛天依 - 一半一半.mp3");
        BufferedInputStream bufferedInputStream=null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            myMusic =new Player(bufferedInputStream);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public void play(){
        System.out.println("songName:" + songName + "\n"+"singer:" + singer+"\n" + "songMaker:" + songMaker
                + "\n" + "lyricMaker:" + lyricMaker);
        MyThread myThread = new MyThread(lyricMap ,timeMap);
        Thread thread1 = new Thread(myThread);
        thread1.start();
        try {
            myMusic.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
