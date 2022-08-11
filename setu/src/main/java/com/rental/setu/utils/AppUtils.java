package com.rental.setu.utils;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Utility methods for carrying out business logic
 *
 * @author jimil
 */
@Service
public class AppUtils {

    /**
     * Get the number of days between now and target date
     *
     * @param targetDate the date till which number of days are to be counted from now
     * @return
     */
    public long getIntervalInDaysFromNow(java.sql.Date targetDate) {
        Date now = Date.from(Instant.now());
        Date target = new Date(targetDate.getTime());

        long diffInMillis = Math.abs(target.getTime() - now.getTime());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Get the number of days between first date and second date
     *
     * @param firstDate the starting date of the range
     * @param secondDate the ending date of the range
     * @return
     */
    public long getIntervalInDays(java.sql.Date firstDate, java.sql.Date secondDate) {
        Date first = new Date(firstDate.getTime());
        Date second = new Date(secondDate.getTime());

        long diffInMillis = Math.abs(second.getTime() - first.getTime());
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * used to compute the difference between two sets
     *
     * @param setOne
     * @param setTwo
     * @return
     */
    public Set<Integer> difference(final Set<Integer> setOne, final Set<Integer> setTwo) {
        Set<Integer> result = new HashSet<>(setOne);
        result.removeIf(setTwo::contains);
        return result;
    }

}
