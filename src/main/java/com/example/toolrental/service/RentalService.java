package com.example.toolrental.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import com.example.toolrental.model.Constants;
import com.example.toolrental.model.RentalAgreement;
import com.example.toolrental.model.Tool;
import com.example.toolrental.model.ToolInventory;

public class RentalService {

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate, ToolInventory inventory) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException(Constants.ERROR_RENTAL_DAYS);
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException(Constants.ERROR_DISCOUNT_PERCENT);
        }

        Tool tool = inventory.getTool(toolCode);
        if(tool == null){
            throw new IllegalArgumentException(Constants.ERROR_TOOL_NOT_FOUND);
        }

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeableDays = calculateChargeableDays(checkoutDate.plusDays(1), dueDate, tool);
        double preDiscountCharge = roundToCents(tool.getDailyCharge() * chargeableDays);
        double discountAmount = roundToCents(preDiscountCharge * (discountPercent / 100.0));
        double finalCharge = roundToCents(preDiscountCharge - discountAmount);

        return new RentalAgreement(tool, rentalDays, chargeableDays, checkoutDate, dueDate, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    public int calculateChargeableDays(LocalDate startDate, LocalDate dueDate, Tool tool) {
        int chargeableDays = 0;

        for (LocalDate date = startDate; !date.isAfter(dueDate); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();
            boolean isHoliday = isHoliday(date);
            boolean isWeekday = !(day.equals(DayOfWeek.SATURDAY) || day.equals(DayOfWeek.SUNDAY));
            boolean isWeekend = !isWeekday;

            if (isHoliday && tool.getHolidayCharge() == false) {
                continue;
            }
            if (isWeekday && tool.getWeekdayCharge() == false) {
                continue;
            }
            if (isWeekend && tool.getWeekendCharge() == false) {
                continue;
            }

            chargeableDays++;
        }

        return chargeableDays;
    }

    public boolean isHoliday(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException(Constants.ERROR_DATE_NOT_NULL);
        }
        LocalDate independenceDay = LocalDate.of(date.getYear(), Month.JULY, 4);
        if (independenceDay.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            independenceDay = independenceDay.plusDays(1);
        }
        LocalDate laborDay = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        return date.equals(laborDay) || date.equals(independenceDay);
    }

    // Helper method to round to two decimal places (round half up)
    public double roundToCents(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
