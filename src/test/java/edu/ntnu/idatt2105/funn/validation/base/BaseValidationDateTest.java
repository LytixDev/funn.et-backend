package edu.ntnu.idatt2105.funn.validation.base;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import edu.ntnu.idatt2105.funn.validation.BaseValidation;

public class BaseValidationDateTest {
    
    private final LocalDate today = LocalDate.now();

    private final LocalDate yesterday = LocalDate.now().minusDays(1);

    private final LocalDate tomorrow = LocalDate.now().plusDays(1);

    @Test
    public void testTodayIsAfterYesterday() {
        assertTrue(BaseValidation.isAfter(today, yesterday));
    }

    @Test
    public void testTodayIsAfterTomorrow() {
        assertFalse(BaseValidation.isAfter(today, tomorrow));
    }

    @Test
    public void testTodayIsAfterToday() {
        assertFalse(BaseValidation.isAfter(today, today));
    }

    @Test
    public void testTodayIsBeforeTomorrow() {
        assertTrue(BaseValidation.isBefore(today, tomorrow));
    }

    @Test
    public void testTodayIsBeforeYesterday() {
        assertFalse(BaseValidation.isBefore(today, yesterday));
    }

    @Test
    public void testTodayIsBeforeToday() {
        assertFalse(BaseValidation.isBefore(today, today));
    }
}
