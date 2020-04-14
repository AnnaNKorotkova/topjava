package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class ForJunitRulesTimeWatch extends Stopwatch {
    private final static Logger LOG = getLogger(ForJunitRulesTimeWatch.class);
    public static List<String> testResult = new ArrayList<>();
    public static short finished = 0;
    public static long allTime = 0L;
    public static String delimiter = "\n====================================================";

    @Override
    protected void finished(long nanos, Description description) {
        finished++;
        allTime += nanos;
        String message = String.format("\ntest %-35s %8d ms", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
        Marker marker = MarkerFactory.getMarker("currentTestResult");
        LOG.info(marker, colorTo(delimiter + message + delimiter, 32));
        testResult.add(message);
    }

    public static String colorTo(String str, int color) {
        return (char) 27 + "[" + color + "m" + str + (char) 27 + "[0m";
    }
}