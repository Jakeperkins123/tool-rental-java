package com.example.toolrental.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RentalAgreement {

    private final Tool tool;
    private final int rentalDays;
    private final int chargeableDays;
    private final LocalDate checkoutDate;
    private final LocalDate dueDate;
    private final double preDiscountCharge;
    private final int discountPercent;
    private final double discountAmount;
    private final double finalCharge;

    public RentalAgreement(Tool tool, int rentalDays, int chargeableDays,
            LocalDate checkoutDate, LocalDate dueDate,
            double preDiscountCharge, int discountPercent,
            double discountAmount, double finalCharge) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.chargeableDays = chargeableDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.preDiscountCharge = preDiscountCharge;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.finalCharge = finalCharge;
    }

    public Tool getTool() {
        return this.tool;
    }

    public int getRentalDays() {
        return this.rentalDays;
    }

    public int getChargeableDays() {
        return this.chargeableDays;
    }

    public LocalDate getCheckoutDate() {
        return this.checkoutDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public double getPreDiscountCharge() {
        return this.preDiscountCharge;
    }

    public int getDiscountPercent() {
        return this.discountPercent;
    }

    public double getDiscountAmount() {
        return this.discountAmount;
    }

    public double getFinalCharge() {
        return this.finalCharge;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RentalAgreement)) {
            return false;
        }
        RentalAgreement rentalAgreement = (RentalAgreement) o;
        return Objects.equals(tool, rentalAgreement.tool) && rentalDays == rentalAgreement.rentalDays && chargeableDays == rentalAgreement.chargeableDays && Objects.equals(checkoutDate, rentalAgreement.checkoutDate) && Objects.equals(dueDate, rentalAgreement.dueDate) && preDiscountCharge == rentalAgreement.preDiscountCharge && discountPercent == rentalAgreement.discountPercent && discountAmount == rentalAgreement.discountAmount && finalCharge == rentalAgreement.finalCharge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tool, rentalDays, chargeableDays, checkoutDate, dueDate, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        // %s - String
        // %d - Integer
        // %.2f - 2 decimal fp num
        // %d%% - integer + % symbol
        // %n - /n new line
        String agreementString = String.format("""
                Tool code: %s
                Tool type: %s
                Tool brand: %s
                Rental days: %d
                Checkout date: %s
                Due date: %s
                Daily rental charge: $%.2f
                Chargeable days: %d
                Pre-discount charge: $%.2f
                Discount percent: %d%%
                Discount amount: $%.2f
                Final charge: $%.2f%n""",
                tool.getToolCode(), tool.getToolType(), tool.getBrand(),
                rentalDays,
                checkoutDate.format(formatter),
                dueDate.format(formatter),
                tool.getDailyCharge(),
                chargeableDays,
                preDiscountCharge,
                discountPercent,
                discountAmount,
                finalCharge);
        return agreementString;
    }

}
