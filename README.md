# Re-peta
The project concerns the implementation of a platform for booking repetitions provided by the university.
Users can book, view and cancel repetitions using their own account due to the storage of all data in a database.

The service is available through:
- Web site built via Servlet, Bootstrap and Vue.js
- Android application built via Flutter and Dart language

Both solutions communicate with the database by performing the following set of actions:
1. The user through his choices generates a JSON message.
2. The message is received by Servlets and they are transformed into queries (after appropriate checks).
3. The query is submitted to the database and the response is handled by transforming it into JSON format, understandable for the Front End side.
4. The result is displayed to the user

The entire application was designed with Figma and studied paying close attention to UX and UI.
