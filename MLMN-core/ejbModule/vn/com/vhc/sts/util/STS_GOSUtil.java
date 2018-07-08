package vn.com.vhc.sts.util;

import java.io.IOException;

import java.text.DecimalFormat;

import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class STS_GOSUtil {
    public static final double CELL_GOS_MAX = 0.99;
    public static final double CELL_OFFER_STEP = 0.01;
    public static final double ROUTE_GOS_MAX = 0.99;
    public static final double ROUTE_OFFER_STEP = 1;
    private static final DecimalFormat ONE_SCALE_FORMAT =
        new DecimalFormat("#.#");
    private static final DecimalFormat EIGHT_SCALE_FORMAT =
        new DecimalFormat("#.########");
    private static final DecimalFormat TWO_SCALE_FORMAT =
        new DecimalFormat("#.##");
    private static final DecimalFormat THREE_SCALE_FORMAT =
        new DecimalFormat("#.###");

    private static Logger logger = Logger.getLogger(STS_GOSUtil.class.getName());
    private static FileHandler handler;

    public STS_GOSUtil() {
        super();
    }

    public static void main(String[] args) throws IOException {
        //generateCellGOS("CELL_GOS.txt", 2, 50, 0.1, 100);
        //generateCellCapacity("CELL_CAPACITY.txt", 0.03, 0.05, 0.1, 50);
        //generateCellTchRequest("CELL_TCHREQUEST.txt", 0.03, 0.05, 0.1, 100);
        //generateRouteGOS("D:/Temp/ROUTE_GOS_Dev1k.txt", 1, 2000, 1, 1000);
        //generateRouteGOS("D:/Temp/ROUTE_GOS_Dev2k.txt", 1001, 2000, 1, 10000);
        //generateRouteGOS("D:/Temp/ROUTE_GOS_Dev3k.txt", 2001, 3000, 1, 10000);
        //generateRouteGOS("D:/Temp/ROUTE_GOS_Dev4k.txt", 3001, 4000, 1, 10000);
        //generateRouteGOS("D:/Temp/ROUTE_GOS_Dev5k.txt", 4001, 5000, 1, 10000);
        //generateRouteGOS("D:/Temp/ROUTE_GOS_Dev6k.txt", 5001, 6000, 1, 10000);
        //generateRouteGOS("D:/Temp/ROUTE_GOS_Dev7k.txt", 6001, 7000, 1, 10000);
        generateRouteGOS("D:/Temp/ROUTE_GOS_Dev8k.txt", 7001, 8000, 1, 10000);
        generateRouteGOS("D:/Temp/ROUTE_GOS_Dev9k.txt", 8001, 9000, 1, 10000);
        generateRouteGOS("D:/Temp/ROUTE_GOS_Dev10k.txt", 9001, 10000, 1,
                         10000);
        //generateRouteCapacity("ROUTE_CAPACITY.txt", 0.005, 0.005, 1, 10000);
        //generateRouteDevRequest("ROUTE_DEVREQUEST.txt", 0.005, 0.005, 1, 10000);
    }


    private static void initFileLogger(String fileName) throws IOException {
        if (handler != null) {
            handler.close();
            logger.removeHandler(handler);
        }

        handler = new FileHandler(fileName);
        handler.setLevel(Level.FINEST);
        logger.setLevel(Level.FINEST);
        handler.setFilter(new PlainTextFilter());
        handler.setFormatter(new PlainTextFormatter());
        logger.addHandler(handler);
    }

    public static void generateCellGOS(String fileName, int fromTch, int toTch,
                                       double fromTraf,
                                       double toTraf) throws IOException {
        initFileLogger(fileName);
        logger.log(Level.FINEST, "TRAFFIC,TCH,GOS\n", "File");
        double cong, offer, traf, gos;

        for (int tch = fromTch; tch < toTch; tch++) {
            logger.info("Calculating TCH = " + tch);
            traf = fromTraf;
            while (traf < tch - 0.1 && traf <= toTraf) {
                offer = traf;
                do {
                    cong = 1;
                    offer = offer + CELL_OFFER_STEP;
                    for (int i = 1; i <= tch; i++) {
                        cong = cong * offer / (i + cong * offer);
                    }
                } while (offer < traf / (1 - cong));
                gos = cong;
                //Write to file
                logger.log(Level.FINEST,
                           ONE_SCALE_FORMAT.format(traf) + "," + tch + "," +
                           EIGHT_SCALE_FORMAT.format(gos) + "\n", "File");
                traf = traf + 0.1;
            }
        }
    }

    public static void generateCellCapacity(String fileName,
                                            double fromGOSDesign,
                                            double toGOSDesign, double fromTch,
                                            double toTch) throws IOException {
        initFileLogger(fileName);
        logger.log(Level.FINEST, "CHANNEL,GOS,CAPACITY\n", "File");
        double cong, offer, gos, capacity, tch;

        gos = fromGOSDesign;
        while (gos <= toGOSDesign) {
            logger.info("Calculating GOS = " + gos);
            tch = fromTch;
            while (tch <= toTch) {
                offer = 0;
                do {
                    cong = 1;
                    for (int i = 1; i <= tch; i++) {
                        cong = cong * offer / (i + cong * offer);
                    }
                    offer = offer + CELL_OFFER_STEP;
                } while (cong < gos);
                capacity = offer;
                //Write to file
                logger.log(Level.FINEST,
                           ONE_SCALE_FORMAT.format(tch) + "," + gos + "," +
                           TWO_SCALE_FORMAT.format(capacity) + "\n", "File");
                tch = tch + 0.1;
            }
            gos = gos + 0.01;
        }
    }

    public static void generateCellTchRequest(String fileName,
                                              double fromGOSDesign,
                                              double toGOSDesign,
                                              double fromTraf,
                                              double toTraf) throws IOException {
        initFileLogger(fileName);
        logger.log(Level.FINEST, "TRAFFIC,TCHREQUEST,GOS\n", "File");
        double cong, offer, gos, traf;
        int tch, tchRequest;

        gos = fromGOSDesign;
        while (gos <= toGOSDesign) {
            logger.info("Calculating GOS = " + gos);
            traf = fromTraf;
            while (traf <= toTraf) {
                offer = 0;
                tch = 1;
                while (offer <= traf) {
                    do {
                        cong = 1;
                        for (int i = 1; i <= tch; i++) {
                            cong = cong * offer / (i + cong * offer);
                        }
                        offer = offer + CELL_OFFER_STEP;
                    } while (cong < gos);
                    tch = tch + 1;
                }
                tchRequest = tch - 1;
                //Write to file
                logger.log(Level.FINEST,
                           ONE_SCALE_FORMAT.format(traf) + "," + tchRequest +
                           "," + TWO_SCALE_FORMAT.format(gos) + "\n", "File");
                traf = traf + 0.1;
            }
            gos = gos + 0.01;
        }
    }

    public static void generateRouteGOS(String fileName, int fromDev,
                                        int toDev, int fromTraf,
                                        int toTraf) throws IOException {
        initFileLogger(fileName);
        logger.log(Level.FINEST, "TRAFFIC,DEVICE,GOS\n", "File");
        double cong, offer, gos;
        int traf;

        for (int dev = fromDev; dev < toDev; dev++) {
            logger.info("Calculating Device = " + dev);
            traf = fromTraf;
            while (traf < dev - 1 && traf <= toTraf) {
                offer = traf;
                do {
                    cong = 1;
                    offer = offer + ROUTE_OFFER_STEP;
                    for (int i = 1; i <= dev; i++) {
                        cong = cong * offer / (i + cong * offer);
                    }
                } while (offer < traf / (1 - cong));
                gos = cong;
                //Write to file
                logger.log(Level.FINEST,
                           traf + "," + dev + "," + EIGHT_SCALE_FORMAT.format(gos) +
                           "\n", "File");
                traf = traf + 1;
            }
        }
    }

    public static void generateRouteCapacity(String fileName,
                                             double fromGOSDesign,
                                             double toGOSDesign, int fromDev,
                                             int toDev) throws IOException {
        initFileLogger(fileName);
        logger.log(Level.FINEST, "DEVICE,GOS,CAPACITY\n", "File");
        double cong, offer, gos, capacity;
        int dev;
        gos = fromGOSDesign;
        while (gos <= toGOSDesign) {
            logger.info("Calculating GOS = " + gos);
            dev = fromDev;
            while (dev <= toDev) {
                offer = 0;
                do {
                    cong = 1;
                    for (int i = 1; i <= dev; i++) {
                        cong = cong * offer / (i + cong * offer);
                    }
                    offer = offer + ROUTE_OFFER_STEP;
                } while (cong < gos);
                capacity = offer;
                //Write to file
                logger.log(Level.FINEST,
                           dev + "," + THREE_SCALE_FORMAT.format(gos) + "," +
                           TWO_SCALE_FORMAT.format(capacity) + "\n", "File");
                dev = dev + 1;
            }
            gos = gos + 0.001;
        }
    }

    public static void generateRouteDevRequest(String fileName,
                                               double fromGOSDesign,
                                               double toGOSDesign,
                                               int fromTraf,
                                               int toTraf) throws IOException {
        initFileLogger(fileName);
        logger.log(Level.FINEST, "TRAFFIC,DEVREQUEST,GOS\n", "File");
        double cong, offer, gos, traf;
        int dev, devRequest;

        gos = fromGOSDesign;
        while (gos <= toGOSDesign) {
            logger.info("Calculating GOS = " + gos);
            traf = fromTraf;
            while (traf <= toTraf) {
                offer = 0;
                dev = 1;
                while (offer <= traf) {
                    do {
                        cong = 1;
                        for (int i = 1; i <= dev; i++) {
                            cong = cong * offer / (i + cong * offer);
                        }
                        offer = offer + ROUTE_OFFER_STEP;
                    } while (cong < gos);
                    dev = dev + 1;
                }
                devRequest = dev - 1;
                //Write to file
                logger.log(Level.FINEST,
                           traf + "," + devRequest + "," + THREE_SCALE_FORMAT.format(gos) +
                           "\n", "File");
                traf = traf + 1;
            }
            gos = gos + 0.001;
        }
    }

    private static class PlainTextFormatter extends Formatter {
        public String format(LogRecord record) {
            return record.getMessage();
        }
    }

    private static class PlainTextFilter implements Filter {
        public boolean isLoggable(LogRecord record) {
            return (record.getParameters() != null);
        }
    }
}
