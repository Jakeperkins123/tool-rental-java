# Tool Rental POS System

## Features
- **Tool Inventory Management**: Rent tools like ladders, chainsaws, and jackhammers with attributes such as tool code, type, brand, and daily rental charges.
- **Rental Agreement Generation**: Automatically generates a detailed agreement including tool details, rental period, chargeable days, pre-discount charges, discounts, and final charges.
- **Holiday Handling**: Supports Independence Day (observed if it falls on a weekend) and Labor Day (first Monday in September).
- **Flexible Charging Rules**: Tools may have different charging rules for weekdays, weekends, and holidays.
- **Validation**: Throws user-friendly exceptions for invalid inputs such as rental days less than 1 or discounts outside the range 0–100.

## Tools
| Tool Code | Tool Type    | Brand    | Daily Charge | Weekday Charge | Weekend Charge | Holiday Charge |
|-----------|-------------|----------|--------------|----------------|----------------|----------------|
| CHNS      | Chainsaw    | Stihl    | $1.49        | Yes            | No             | Yes            |
| LADW      | Ladder      | Werner   | $1.99        | Yes            | Yes            | No             |
| JAKD      | Jackhammer  | DeWalt   | $2.99        | Yes            | No             | No             |
| JAKR      | Jackhammer  | Ridgid   | $2.99        | Yes            | No             | No             |

## Tests
Includes JUnit tests for:
1. Validating required test cases (6 scenarios provided in the specification).
2. Handling invalid inputs (e.g., invalid tool codes, rental days < 1, or discounts > 100%).
3. Ensuring proper holiday handling and chargeable day calculations.
4. Verifying formatting of dates (`mm/dd/yy`), currency (`$9,999.99`), and percentages (`99%`).

## How to Run
1. Build the project using Maven:
``` 
mvn clean install 
```
2. Run tests 
```
mvn test
```
    