package vn.com.vhc.sts.core;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.log4j.Logger;


public class STS_FileSweeper {
    private static Logger logger =
        Logger.getLogger(STS_FileSweeper.class.getName());
    private long backupDay;
    private File rootDir;

    public STS_FileSweeper(String rootDir, long backupDay) throws IOException {
        this.backupDay = backupDay * 24 * 60 * 60 * 1000;
        this.rootDir = new File(rootDir);
        if (!this.rootDir.isDirectory()) {
            throw new IOException(rootDir + " is not a directory");
        }
    }

    public void doSweep() {
        deleteFileRecursive(rootDir, System.currentTimeMillis());
    }

    protected void deleteFileRecursive(File dir, long currentTimeMil) {
        int cnt = 0;
        File[] dirList = dir.listFiles(new DirFilter(currentTimeMil));
        if (dirList != null) {
            for (File d : dirList) {
                if (d.isDirectory()) {
                    deleteFileRecursive(d, currentTimeMil);

                } else {
                    d.delete();
                    cnt++;
                }
            }
            logger.info(cnt + " files deleted in " + dir.getPath());
        }
    }


    public static void main(String[] args) throws IOException {
        new STS_FileSweeper("H:\\Temp\\test.txt", 30).doSweep();
    }

    private class DirFilter implements FileFilter {
        private long current;

        public DirFilter(long currentTime) {
            this.current = currentTime;
        }

        public boolean accept(File pathname) {
            //Check if this is file and file modified time is over backup day
            if (pathname.isDirectory() ||
                (pathname.isFile() && (current - pathname.lastModified()) >
                 backupDay)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
