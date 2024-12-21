package com.example.toolrental;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

import com.example.toolrental.model.ErrorMessages;
import com.example.toolrental.model.RentalAgreement;
import com.example.toolrental.model.Tool;
import com.example.toolrental.model.ToolInventory;
import com.example.toolrental.service.RentalService;

import junit.framework.TestCase;

public class RentalServiceTest extends TestCase {

    private RentalService rentalService;
    private ToolInventory inventory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        inventory = new ToolInventory();
        inventory.addTool(new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true));
        inventory.addTool(new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false));
        inventory.addTool(new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false));
        inventory.addTool(new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false));
        rentalService = new RentalService();
    }

    // Test cases from requirements doc 
    public void testRequiredTestCases() {
        try {
            rentalService.checkout(
                    "LADW",
                    5,
                    101,
                    LocalDate.of(2015, Month.SEPTEMBER, 3),
                    inventory
            );
            fail("Expected Illegal Argument Exception for discount %");
        } catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.ERROR_DISCOUNT_PERCENT, e.getMessage());
        }

        RentalAgreement agreement2 = rentalService.checkout("LADW", 3, 10, LocalDate.of(20, Month.JULY, 2), inventory);
        assertEquals(2, agreement2.getChargeableDays());
        assertEquals(3.98, agreement2.getPreDiscountCharge());
        assertEquals(.40, agreement2.getDiscountAmount());
        assertEquals(3.58, agreement2.getFinalCharge());

        RentalAgreement agreement3 = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, Month.JULY, 2), inventory);
        assertEquals(3, agreement3.getChargeableDays());
        assertEquals(4.47, agreement3.getPreDiscountCharge());
        assertEquals(1.12, agreement3.getDiscountAmount());
        assertEquals(3.35, agreement3.getFinalCharge());

        RentalAgreement agreement4 = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, Month.SEPTEMBER, 3), inventory);
        assertEquals(3, agreement4.getChargeableDays());
        assertEquals(8.97, agreement4.getPreDiscountCharge());
        assertEquals(0.0, agreement4.getDiscountAmount());
        assertEquals(8.97, agreement4.getFinalCharge());

        RentalAgreement agreement5 = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, Month.JULY, 2), inventory);
        assertEquals(5, agreement5.getChargeableDays());
        assertEquals(14.95, agreement5.getPreDiscountCharge());
        assertEquals(0.0, agreement5.getDiscountAmount());
        assertEquals(14.95, agreement5.getFinalCharge());

        RentalAgreement agreement6 = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, Month.JULY, 2), inventory);
        assertEquals(1, agreement6.getChargeableDays());
        assertEquals(2.99, agreement6.getPreDiscountCharge());
        assertEquals(1.50, agreement6.getDiscountAmount());
        assertEquals(1.49, agreement6.getFinalCharge());
    }


    // My test cases
    public void testValidCheckout() {
        RentalAgreement agreement = rentalService.checkout(
                "LADW",
                3,
                10,
                LocalDate.of(2024, Month.JULY, 2),
                inventory
        );
        assertEquals("LADW", agreement.getTool().getToolCode());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(LocalDate.of(2024, Month.JULY, 5), agreement.getDueDate());
        assertEquals(2, agreement.getChargeableDays());
        assertEquals(3.98, agreement.getPreDiscountCharge());
        assertEquals(0.40, agreement.getDiscountAmount());
        assertEquals(3.58, agreement.getFinalCharge());
    }

    public void testToolNotFound() {
        try {
            rentalService.checkout(
                    "ASDFG",
                    5,
                    1,
                    LocalDate.of(2015, Month.SEPTEMBER, 3),
                    inventory
            );
            fail("Expected Illegal Argument Exception for invalid tool code");
        } catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.ERROR_TOOL_NOT_FOUND, e.getMessage());
        }
    }

    public void testEmptyToolCode() {
        try {
            rentalService.checkout(
                    "", // Empty string as tool code
                    5,
                    1,
                    LocalDate.of(2015, Month.SEPTEMBER, 3),
                    inventory
            );
            fail("Expected IllegalArgumentException for empty tool code");
        } catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.ERROR_TOOL_NOT_FOUND, e.getMessage());
        }
    }

    public void testNullToolCode() {
        try {
            rentalService.checkout(
                    null, // Null tool code
                    5,
                    1,
                    LocalDate.of(2015, Month.SEPTEMBER, 3),
                    inventory
            );
            fail("Expected IllegalArgumentException for null tool code");
        } catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.ERROR_TOOL_NOT_FOUND, e.getMessage());
        }
    }

    public void testInvalidDiscount() {
        try {
            rentalService.checkout(
                    "LADW",
                    3,
                    101,
                    LocalDate.of(2024, Month.JULY, 2),
                    inventory);
            fail("Expected invalid discount");
        } catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.ERROR_DISCOUNT_PERCENT, e.getMessage());
        }
        try {
            rentalService.checkout(
                    "LADW",
                    3,
                    -1,
                    LocalDate.of(2024, Month.JULY, 2),
                    inventory);
            fail("Expected IllegalArgumentException for invalid discount");
        } catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.ERROR_DISCOUNT_PERCENT, e.getMessage());
        }
    }

    public void testIsHoliday() {
        // 4th of july
        assertTrue(rentalService.isHoliday(LocalDate.of(2024, Month.JULY, 4)));
        // first labor day first monday of sept
        LocalDate laborDay = LocalDate.of(2024, Month.SEPTEMBER, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        assertTrue(rentalService.isHoliday(laborDay));
        // labor day falls on sat
        assertTrue(rentalService.isHoliday(LocalDate.of(2020, Month.JULY, 3)));
        // labor day falls on sun
        assertTrue(rentalService.isHoliday(LocalDate.of(2021, Month.JULY, 5)));
        // not labor day
        assertFalse(rentalService.isHoliday(LocalDate.of(2021, Month.JULY, 20)));
        // invalid input
        try {
            rentalService.isHoliday(null);
            fail("Expected IllegalArgumentException for null");
        } catch (IllegalArgumentException e) {
            assertEquals(ErrorMessages.ERROR_DATE_NOT_NULL, e.getMessage());
        }
        // Day before Independence Day (not a holiday)
        assertFalse(rentalService.isHoliday(LocalDate.of(2024, Month.JULY, 3)));

        // Day after Independence Day (not a holiday)
        assertFalse(rentalService.isHoliday(LocalDate.of(2024, Month.JULY, 5)));

        // Day before Labor Day (not a holiday)
        assertFalse(rentalService.isHoliday(LocalDate.of(2024, Month.SEPTEMBER, 1)));
    }

    public void testCalculateChargeableDays() {
        LocalDate startDate = LocalDate.of(2024, Month.JULY, 1);
        LocalDate dueDate = LocalDate.of(2024, Month.JULY, 12);
        Tool ladder = inventory.getTool("LADW");
        int chargeableDays = rentalService.calculateChargeableDays(startDate.plusDays(1), dueDate, ladder);

        assertEquals(10, chargeableDays);

        Tool chainsaw = inventory.getTool("CHNS");
        chargeableDays = rentalService.calculateChargeableDays(startDate.plusDays(1), dueDate, chainsaw);
        assertEquals(9, chargeableDays);

        Tool jackhammer = inventory.getTool("JAKD");
        chargeableDays = rentalService.calculateChargeableDays(startDate.plusDays(1), dueDate, jackhammer);
        assertEquals(8, chargeableDays);
    }

    public void testRoundToCents() {
        // Test cases for exact rounding
        assertEquals(1.12, rentalService.roundToCents(1.123), 0.0); // down
        assertEquals(1.13, rentalService.roundToCents(1.125), 0.0); // up
        assertEquals(2.00, rentalService.roundToCents(2.0), 0.0);   // No rounding 

        // Test cases for negative numbers
        assertEquals(-1.12, rentalService.roundToCents(-1.123), 0.0); // down (negative)
        assertEquals(-1.13, rentalService.roundToCents(-1.125), 0.0); // up (negative)

        // Edge cases
        assertEquals(0.00, rentalService.roundToCents(0.00001), 0.0); // just above zero
        assertEquals(-0.00, rentalService.roundToCents(-0.00001), 0.0); // Negative zero
    }

    public void testRentalAgreementFormatting() {
        RentalAgreement agreement = rentalService.checkout(
                "LADW",
                3,
                10,
                LocalDate.of(2024, Month.JULY, 2),
                inventory
        );

        String expectedOutput = """
            Tool code: LADW
            Tool type: Ladder
            Tool brand: Werner
            Rental days: 3
            Checkout date: 07/02/24
            Due date: 07/05/24
            Daily rental charge: $1.99
            Chargeable days: 2
            Pre-discount charge: $3.98
            Discount percent: 10%
            Discount amount: $0.40
            Final charge: $3.58
            """;
        assertEquals(expectedOutput, agreement.toString());

    }
}
