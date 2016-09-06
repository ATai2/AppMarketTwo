package sitv.s003.dianbo.com.appmarkettwo.util;

import java.io.File;

import sitv.s003.dianbo.com.appmarkettwo.download.DownloadTask;

/**
 * Created by Administrator on 2016/9/6.
 */
public class FileUtils {
    public static  boolean isfileExsit(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }
    public  static void deletFile(DownloadTask task){
        File file = new File(task.getFilePath() + task.getPackageName());
        file.delete();
    }
}
