package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ForJunitRulesTimeWatch extends Stopwatch {
    private final static Logger LOG = getLogger(ForJunitRulesTimeWatch.class);
    public static short passed = 0;
    public static short failed = 0;
    public static long allTime = 0L;

    @Override
    public void succeeded(long nanos, Description description) {
        passed++;
        allTime += nanos;
        float testTime = (float) Math.floor(nanos / Math.pow(10, 6)) / 1000;
        LOG.info("\n=================================================================================\n" +
                "The test '" + description.getMethodName() + "' was passed in " + testTime + " sec.\n"
                + "=================================================================================");
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        failed++;
        allTime += nanos;
        LOG.info("\n=================================================================================\n" +
                "The test '" + description.getMethodName() + "' was failed."
                + "\n=================================================================================");
    }
}
