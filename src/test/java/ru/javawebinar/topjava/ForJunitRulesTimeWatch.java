package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ForJunitRulesTimeWatch extends Stopwatch {
    private final static Logger LOG = getLogger(ForJunitRulesTimeWatch.class);
    public static List<String> testResult = new ArrayList<>();
    public static short finished = 0;
    public static long allTime = 0L;

    @Override
    protected void finished(long nanos, Description description) {
        finished++;
        allTime += nanos;
        int stringLengthCompensation = 35 - description.getMethodName().length();
        String message = "test " + description.getMethodName()
                + String.join("", Collections.nCopies(stringLengthCompensation, " "))
                + Math.round(nanos / 1000_000) + "\tms";
        LOG.info("\n=================================================================================\n" +
                message
                + "\n=================================================================================");
        testResult.add(message);
    }
}