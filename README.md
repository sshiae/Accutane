## General information

**Application Name:** Accutane

**Purpose:** The application is designed to conveniently track the course of retinoid intake. It allows users to mark the days of admission, receive reminders about the need for admission, see the end date of the course, as well as track the total cumulative dose and the current dose scored.

## Basic functionality

### 1. The start window

- **Description:** When the application is first launched, it displays a window with a list of courses.
- **Contents:**
- A list of courses with the name and start date.
  - The ability to delete, view and change courses.
  - Floating button in the lower right corner to add a new course.

###2. Adding a course

- **Access:** Through the floating button on the start screen.
- **Input fields:**
- Daily dosage.
  - The total desired dosage.
  - The start date of the course.
  - The time of the drug reminder.
  - Name of the course.
- **Action:** The "Save" button to add a course.

### 3. Viewing and editing the course

- **Access:** When clicking on a course in the list.
- **Information:**
  - Percentage progress of the course in the form of a pie chart.
  - The total number of days of treatment.
  - The start date of the course.
  - The daily dose of the drug.
  - Already accumulated course dose.
  - The number of days until the end of the course (calculated as the total dose / daily dose, rounded up).
  - The total desired dose.
- **Actions:**
- "Change course" button: opens the course editing window (see point 2).
- "Stop course" button: removes the course from the list.

###4. Reminders

- **Description:** Daily reminders about taking the capsule.
- **Functions:**
  - Notification with the option to select "Remind in 5 minutes" and "Close".
  - If you select "Remind in 5 minutes", the notification is repeated after 5 minutes.
  - When you select "Close", the dose is counted as drunk, and the application opens.

## Technical requirements

1. **Platforms:** Android and iOS.
2. **Development language:** Kotlin and KMP.
3. **Notifications:** Use system notifications for reminders.
4. **Interface:** It must be intuitive and comply with modern standards of mobile applications.
5. **Security:** Ensure the protection of user data.

## Additional requirements

- Implement the ability to localize the application to support multiple languages.
- Provide responsive design for different screen sizes.

## Quality control

- Conduct testing on all supported devices.
- Make sure that all functions work correctly and there are no critical errors.
- To test the user interface for usability.

## Deadlines

- Development: 3 months.
- Testing: 1 month.
- Implementation and publication: 1 month.