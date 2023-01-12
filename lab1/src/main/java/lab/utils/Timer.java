package lab.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Timer {

    private long start = -1, end = -1;

    private static final DateFormat FORMAT = new SimpleDateFormat("HH:mm:ss.SSSS");

    static {
        FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    }

    public void start() {
        start = System.currentTimeMillis();
        end = -1;
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public String getTime() {
        if (start < 0) {
            throw new IllegalArgumentException("Timer has not been started");
        } else if (end < 0) {
            throw new IllegalArgumentException("Timer has not been ended");
        }

        return FORMAT.format(new Date(end - start));
    }

    @Override
    public String toString() {
        return "Timer{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
