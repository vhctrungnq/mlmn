package inventory.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


public class IN_CollectFileByPattern {
    public static final int MAX_SEARCH_LINE = 10;
    private static Logger logger =
        Logger.getLogger(IN_CollectFileByPattern.class.getName());

    private String regex;

    public IN_CollectFileByPattern() {
    }

    public IN_CollectFileByPattern(String regex) {
        this.regex = regex;
    }

    public static void main(String[] args) {
        IN_CollectFileByPattern fileRemover = new IN_CollectFileByPattern();
        fileRemover.doTask();
    }

    public void doTask() {
        File fileConfig =
            new File(System.getProperty("user.dir"), "config.properties");
        if (!fileConfig.exists()) {
            System.out.println("File config is not exist");
            return;
        }
        Properties proper = load(fileConfig);
        String pattern = proper.getProperty("file.pattern");
        if (pattern == null || pattern.length() == 0) {
            writeLog("file.pattern trong file config la khong chinh xac");
            return;
        }
        this.regex = pattern;

        String original = proper.getProperty("search.dir");
        File originalFile = new File(original);
        if (!originalFile.exists()) {
            writeLog("Gia tri search.dir trong file config la khong chinh xac");
            return;
        }

        String direc = proper.getProperty("destination.dir");
        File direcFile = new File(direc);
        if (!direcFile.exists()) {
            direcFile.mkdirs();
        }

        String type = proper.getProperty("type");
        if (type == null || type.length() == 0) {
            return;
        }

        if (type.equalsIgnoreCase("move")) {
            moveFileMatchPattern(originalFile, direcFile);
        } else if (type.equalsIgnoreCase("remove")) {
            removeFileNotMatchPattern(originalFile);
        } else if (type.equalsIgnoreCase("content")) {
            removeFileIfContentNotMatch(originalFile, regex);
        } else {
            writeLog("type config la khong chinh xac. No chi bao gom 3 loai: remove, move hoac content");
        }
    }

    private void writeLog(String message) {
        RandomAccessFile randomFile = null;
        try {
            randomFile =
                    new RandomAccessFile(new File(System.getProperty("user.dir"),
                                                  "log.txt"), "rw");
            String space = "\n";
            // Seek to end of file
            if (randomFile.length() > 0) {
                randomFile.seek(randomFile.length());
            }
            if (message.length() == 0) {
                message = "";
            }
            randomFile.writeBytes("----------------------------");
            randomFile.write(space.getBytes());

            String time = getCurrentTime();
            randomFile.writeBytes("TIME: " + time + " - MESSAGE: ");

            randomFile.write(message.getBytes());
            randomFile.write(space.getBytes());
        } catch (Exception e) {
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public void removeFileNotMatchPattern(File searchDir) {
        FilePatternFilter filter = new FilePatternFilter(regex, false);
        removeFileRecusive(searchDir, filter);
    }

    public void moveFileMatchPattern(File searchDir, File newDir) {
        FilePatternFilter filter = new FilePatternFilter(regex, true);
        moveFileRecusive(searchDir, newDir, filter);
    }

    public void removeFileIfContentNotMatch(File searchDir,
                                            String searchString) {
        FilePatternFilter filter = new FilePatternFilter(".*", true);
        removeFileIfContentNotMatch(searchDir, filter, searchString);
    }

    protected void removeFileIfContentNotMatch(File searchDir,
                                               FileFilter filter,
                                               String searchString) {
        int cnt = 0;
        File[] dirList = searchDir.listFiles(filter);
        if (dirList != null) {
            for (File d : dirList) {
                if (d.isDirectory()) {
                    removeFileIfContentNotMatch(d, filter, searchString);
                } else {
                    if (shouldRemoveFile(d, searchString)) {
                        cnt++;
                        d.delete();
                        logger.info(d.getPath() + " deleted");
                    }
                }
            }
        }
        logger.info(cnt + " files deleted in " + searchDir.getPath());
    }

    @SuppressWarnings("resource")
	protected boolean shouldRemoveFile(File d, String searchString) {
        BufferedReader reader = null;
        int cnt = 0;
        try {
            reader = new BufferedReader(new FileReader(d));
            String line = reader.readLine();
            while (line != null) {
                //Only check some line
                if (cnt++ >= MAX_SEARCH_LINE) {
                    return true;
                }

                if (line.indexOf(searchString) != -1) {
                    return false;
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    logger.warn(e.getMessage());
                }
            }
        }

        return true;
    }

    protected void moveFileRecusive(File searchDir, File newDir,
                                    FileFilter filter) {
        int cnt = 0;
        File[] dirList = searchDir.listFiles(filter);
        if (dirList != null) {
            for (File d : dirList) {
                if (d.isDirectory()) {
                    moveFileRecusive(d, newDir, filter);
                } else {
                    d.renameTo(new File(newDir.getPath() +
                                        System.getProperty("file.separator") +
                                        d.getName()));
                    cnt++;
                }
            }
        }
        logger.info(cnt + " files move in " + searchDir.getPath() + " to " +
                    newDir.getPath());
    }

    protected void removeFileRecusive(File searchDir, FileFilter filter) {
        int cnt = 0;
        File[] dirList = searchDir.listFiles(filter);
        if (dirList != null) {
            for (File d : dirList) {
                if (d.isDirectory()) {
                    removeFileRecusive(d, filter);
                } else {
                    d.delete();
                    cnt++;
                }
            }
        }
        logger.info(cnt + " files deleted in " + searchDir.getPath());
    }

    private Properties load(File propsFile) {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream(propsFile);
            props.load(fis);
            fis.close();
        } catch (Exception e) {
        }
        return props;
    }

    private class FilePatternFilter implements FileFilter {
        private Pattern pattern;
        private boolean match;

        public FilePatternFilter(String regex, boolean match) {
            pattern = Pattern.compile(regex);
            this.match = match;
        }

        public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return true;
            }

            Matcher m = pattern.matcher(pathname.getName());
            //If match it not appear in file listing
            if (m.matches()) {
                return match;
            }

            return !match;
        }
    }
}
