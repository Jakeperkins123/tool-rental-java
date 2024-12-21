package com.example.toolrental.model;
import java.util.Objects;

public class Tool {

    private String toolCode;
    private String toolType;
    private String brand;
    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;    


    public Tool() {
    }

    public Tool(String toolCode, String toolType, String brand, double dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getToolCode() {
        return this.toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return this.toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getDailyCharge() {
        return this.dailyCharge;
    }

    public void setDailyCharge(double dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return this.weekdayCharge;
    }

    public boolean getWeekdayCharge() {
        return this.weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return this.weekendCharge;
    }

    public boolean getWeekendCharge() {
        return this.weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return this.holidayCharge;
    }

    public boolean getHolidayCharge() {
        return this.holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }

    public Tool toolCode(String toolCode) {
        setToolCode(toolCode);
        return this;
    }

    public Tool toolType(String toolType) {
        setToolType(toolType);
        return this;
    }

    public Tool brand(String brand) {
        setBrand(brand);
        return this;
    }

    public Tool dailyCharge(double dailyCharge) {
        setDailyCharge(dailyCharge);
        return this;
    }

    public Tool weekdayCharge(boolean weekdayCharge) {
        setWeekdayCharge(weekdayCharge);
        return this;
    }

    public Tool weekendCharge(boolean weekendCharge) {
        setWeekendCharge(weekendCharge);
        return this;
    }

    public Tool holidayCharge(boolean holidayCharge) {
        setHolidayCharge(holidayCharge);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tool)) {
            return false;
        }
        Tool tool = (Tool) o;
        return Objects.equals(toolCode, tool.toolCode) && Objects.equals(toolType, tool.toolType) && Objects.equals(brand, tool.brand) && dailyCharge == tool.dailyCharge && weekdayCharge == tool.weekdayCharge && weekendCharge == tool.weekendCharge && holidayCharge == tool.holidayCharge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toolCode, toolType, brand, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
    }

    @Override
    public String toString() {
        return "{" +
            " toolCode='" + getToolCode() + "'" +
            ", toolType='" + getToolType() + "'" +
            ", brand='" + getBrand() + "'" +
            ", dailyCharge='" + getDailyCharge() + "'" +
            ", weekdayCharge='" + isWeekdayCharge() + "'" +
            ", weekendCharge='" + isWeekendCharge() + "'" +
            ", holidayCharge='" + isHolidayCharge() + "'" +
            "}";
    }


}
