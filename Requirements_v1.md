## User Requirements

**Title:** User Login Functionality

**Requirement ID:** U1

**Description:** As a user, I should be able to log in by username and password for the 3 roles.

---

**Title:** User Registration Functionality

**Requirement ID:** U2

**Description:** As a user, I should be able to register with ROLE_USER by providing a username and password, as well as an optional PIN, age, and sex.

---

**Title:** Password Change Functionality

**Requirement ID:** U3

**Description:** As a user, I should be able to change my password by providing the old one and the new one.

---

**Title:** PIN Setting Functionality

**Requirement ID:** U4

**Description:** As a user, I should be able to set my PIN.

---

**Title:** PIN Change Functionality

**Requirement ID:** U5

**Description:** As a user, I should be able to change my PIN by providing the old and the new one.

---

**Title:** PIN-Based Login Functionality

**Requirement ID:** U6

**Description:** As a user, I should be able to log in with username and pin (enhancing spring security with pin functionality).

---

**Title:** User Logout Functionality

**Requirement ID:** U7

**Description:** As a user, I should be able to log out.

---

**Title:** Admin User Management Functionality

**Requirement ID:** U8

**Description:** As a user with ROLE_ADMIN, I should be able to get a list of all users.

---

**Title:** Admin User Enable/Disable Functionality

**Requirement ID:** U9

**Description:** As a user with ROLE_ADMIN, I should be able to enable/disable a user by his username.

---

**Title:** Admin Content Management Functionality

**Requirement ID:** U10

**Description:** As a user with ROLE_ADMIN, I should be able to manage habit tracker templates.

---

**Title:** Admin Color Palettes Functionality

**Requirement ID:** U11

**Description:** As a user with ROLE_ADMIN, I should be able to manage color palettes.

## Settings Requirements

**Title:** Access to Terms and Conditions Functionality

**Requirement ID:** S1

**Description:** As a user, I should be able to get Terms and Conditions.

---

**Title:** Grateful Notification Toggle Functionality

**Requirement ID:** S2

**Description:** As a user, I should be able to enable/disable the Grateful notification.

---

**Title:** Bedtime Property Setting Functionality

**Requirement ID:** S3

**Description:** As a user, I should be able to set the property for Bedtime (hour and minutes).

---

**Title:** Bedtime Property Access Functionality

**Requirement ID:** S4

**Description:** As a user, I should be able to get the property for Bedtime.

## Grateful Requirements

**Title:** Grateful Text Writing Functionality

**Requirement ID:** G1

**Description:** As a user, I should be able to write text for what I am grateful for.

---

**Title:** Grateful List Viewing Functionality

**Requirement ID:** G2

**Description:** As a user, I should be able to see the list of all of my gratefulnesses.

---

**Title:** Grateful Text Editing Functionality

**Requirement ID:** G3

**Description:** As a user, I should be able to edit grateful text.

---

**Title:** Grateful Entry Deletion Functionality

**Requirement ID:** G4

**Description:** As a user, I should be able to delete grateful for the day.

---

**Title:** Inspirational Quotes Viewing Functionality

**Requirement ID:** G5

**Description:** As a user, I should be able to see inspirational quotes, if I wrote grateful text at the end of the day.

---

**Title:** Gratefulness Statistics Visualization Functionality

**Requirement ID:** G6

**Description:** As a user, I should be able to see statistics per month and year. On the graph, I can see how many gratefulnesses are written daily.

---

**Title:** MDE Jettons Receipt Functionality

**Requirement ID:** G7

**Description:** As a user, I should be able to receive MDE jettons - a number of extra (more than one per day) grateful at the end of the month.

---

**Title:** MDE Jettons Notification Functionality

**Requirement ID:** G8

**Description:** As a user, I should be able to see a notification in the app when I receive MDE jettons.

---

**Title:** Video Animation Purchase with MDE Jettons Functionality

**Requirement ID:** G9

**Description:** As a user, I should be able to buy video animation with the latest or random 10 grateful with MDE jettons.

---

**Title:** Video Animation Access and Download Functionality

**Requirement ID:** G10

**Description:** As a user, I should be able to watch and download on my device the created video animation of 10 gratefulnesses.

## Habit Requirements

**Title:** Habit Tracker List Viewing Functionality

**Requirement ID:** H1

**Description:** As a user, I should see a list of all the habit trackers, grouped by month.

---

**Title:** Habit Tracker Detail Viewing Functionality

**Requirement ID:** H2

**Description:** As a user, I should be able to see details for every habit tracker in the list.

---

**Title:** Habit Tracker Image Download Functionality

**Requirement ID:** H3

**Description:** As a user, I should be able to download the habit tracker as an image.

---

**Title:** Monthly Habit Tracker Image Download Functionality

**Requirement ID:** H4

**Description:** As a user, I should be able to download the month with all related habit trackers as an image.

---

**Title:** Habit Tracker Board Customization Functionality

**Requirement ID:** H5

**Description:** As a user, I should be able to set a color and position on the habit tracker board, if the habit tracker is assigned to the current month.

---

**Title:** New Habit Tracker Creation Functionality

**Requirement ID:** H6

**Description:** As a user, I should be able to create a new habit tracker.

---

**Title:** Habit Tracker Template Listing Functionality

**Requirement ID:** H7

**Description:** As a user, I should be able to list habit tracker templates as images.

---

**Title:** Habit Tracker Color Palette Listing Functionality

**Requirement ID:** H8

**Description:** As a user, I should be able to list color palettes for the habit tracker.

---

**Title:** Habit Tracker Customization Functionality

**Requirement ID:** H9

**Description:** As a user, I should be able to select an image, color palette, month, and how many times per month the habit must be completed.

## User Content Management Requirements

**Title:** Habit Tracker Management Functionality

**Requirement ID:** CM1

**Description:** As a user, I should see a list of all the habit trackers.

---

**Title:** Habit Tracker Deletion Functionality

**Requirement ID:** CM2

**Description:** As a user, I should be able to delete selected habit trackers.

---

**Title:** Habit Tracker Detail Editing Functionality

**Requirement ID:** CM3

**Description:** As a user, I should be able to edit details for the habit tracker.
**Title:** Performance Requirement

**Requirement ID:** NF001

**Description:** The application shall respond to user interactions within 1 second for 95% of requests. The backend API should handle at least 500 requests per second during peak usage.

---

**Title:** Reliability Requirement

**Requirement ID:** NF002

**Description:** The application shall have a 99.9% uptime monthly, excluding scheduled maintenance.

---

**Title:** Availability Requirement

**Requirement ID:** NF003

**Description:** The application should be accessible to users 24/7, with scheduled maintenance windows communicated in advance.

---

**Title:** Data Security Requirement

**Requirement ID:** NF004

**Description:** User data, including habits and personal information, must be encrypted during transmission and storage.

---

**Title:** Authentication Requirement

**Requirement ID:** NF005

**Description:** Enforce the use of strong passwords with a combination of uppercase and lowercase letters, numbers, and special characters. Ensure that default usernames and passwords are changed, especially for administrative accounts.

---

**Title:** Authorization Requirement

**Requirement ID:** NF006

**Description:** Implement Role-Based Access Control (RBAC) to assign specific roles to users, and determine access rights based on those roles. Using JWT for authorization, validate and verify tokens to ensure their integrity and prevent token-based attacks. Ensure that user sessions are properly invalidated after logout or inactivity to prevent unauthorized access.

---

**Title:** Data Backup and Recovery Requirement

**Requirement ID:** NF007

**Description:** Regular automated backups of the MySQL database should be performed, and a recovery plan must be in place in case of data loss.

---

**Title:** Cross-browser Compatibility Requirement

**Requirement ID:** NF008

**Description:** The application must be compatible with the latest versions of major web browsers (Chrome, Firefox, Safari).

---

**Title:** Usability Requirement

**Requirement ID:** NF009

**Description:** The user interface should follow responsive design principles, providing a seamless experience across various devices and screen sizes.

---

**Title:** Load Time Requirement

**Requirement ID:** NF010

**Description:** The application's initial load time should not exceed 3 seconds on 3G mobile networks.

---

**Title:** Error Handling Requirement

**Requirement ID:** NF011

**Description:** The application must provide clear and user-friendly error messages for common scenarios, guiding users on issue resolution.

---

**Title:** Logging and Monitoring Requirement

**Requirement ID:** NF012

**Description:** Comprehensive logging should be implemented to capture errors, warnings, and usage patterns.

---

**Title:** Data Encryption Requirement

**Requirement ID:** NF013

**Description:** All communication between the Ionic Angular app and the Java Spring backend must be encrypted using HTTPS. User data stored in the MySQL database should be encrypted, especially sensitive information like passwords.

---

**Title:** Performance Requirement for Mobile Networks

**Requirement ID:** NF014

**Description:** The application should load within 3 seconds on 3G mobile networks to ensure a smooth user experience.
