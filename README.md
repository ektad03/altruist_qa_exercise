# Altruist QA Automation Exercise

## Overview

This repository contains my solution for the Altruist QA Automation Assessment.

The primary objective is to validate the list of stock symbols displayed in the "You may be interested in" section on [Google Finance](https://www.google.com/finance), compare them with a given set of expected symbols, and clearly report any extra or missing symbols. The solution demonstrates robust, production-quality UI automation with best practices.

---

## Tech Stack

- **Language:** Java 17
- **Build Tool:** Maven
- **Testing Framework:** TestNG
- **Automation Library:** Selenium WebDriver
- **Dependency Management:** WebDriverManager

---

## Solution Highlights

- **End-to-End UI Automation:** Automates real browser interactions and validates live data on Google Finance.
- **Dynamic Data Handling:** Handles changing UI data by explicitly reporting extra and missing symbols.
- **Stability & Robustness:** Uses explicit waits and exception handling for reliable operation on dynamic pages.
- **Clean Project Structure:** Professional Maven layout and well-documented code.
- **Easy Reproducibility:** Can be set up and run in minutes with just a few commands.

---

## Prerequisites

- Java 17+
- Maven 3.6+
- Google Chrome browser

---

## How to Run

1. **Clone the repository**
    ```bash
    git clone https://github.com/ektad03/altruist-qa-exercise.git
    cd altruist-qa-exercise
    ```

2. **Install dependencies**
    ```bash
    mvn clean install
    ```

3. **Execute the tests**
    ```bash
    mvn test -DsuiteXmlFile=testng.xml
    ```

> **Test results and console output will display any extra or missing symbols detected compared to the expected list.**

---

## Notes

- The set of stock symbols shown in the UI is dynamic and may differ by user or time. This is handled gracefully in the test reporting.
- All logic, comments, and structure follow best practices for clarity and maintainability.
- If you have any trouble running the tests or would like to discuss the approach, please reach out—happy to walk through the solution!
- Email id: ekta.deshmukh01@gmail.com
---