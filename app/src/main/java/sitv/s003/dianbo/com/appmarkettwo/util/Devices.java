package sitv.s003.dianbo.com.appmarkettwo.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by Administrator on 2016/9/2.
 */
public class Devices {
    public static int freeSpacePer() {
        int speed = 0;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            speed = (int) (availCount * 100.0f / blockCount);
        }
        return speed;
    }
}
