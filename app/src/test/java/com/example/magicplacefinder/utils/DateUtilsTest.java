package com.example.magicplacefinder.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import java.util.Date;


public class DateUtilsTest {

    @Test
    void dateToString_correctFormat_returnTrue() {
        String correctString = "07:15 15-02-88";
        //Months are zero-based in Date. So 12 is interpreted as december + 1 month.
        Date referenceDate = new Date(1988, 1, 15, 07, 15);

        String formedString = DateUtils.dateToString(referenceDate);

        Assertions.assertEquals(correctString, formedString);
    }

    @Test
    void dateToString_incorrectFormat_returnFalse() {

        String correctString = "07:15 15-02-88";
        //Months are zero-based in Date. So 12 is interpreted as december + 1 month.
        Date referenceDate = new Date(1988, 1, 21, 07, 15);

        String formedString = DateUtils.dateToString(referenceDate);

        Assertions.assertNotEquals(correctString, formedString);
    }

}
