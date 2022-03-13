- App consists of a single screen created with Jetpack Compose. This screen shows a list of all the rooms, with their image, name and available spots. Each room has a button that the user can press to book a spot for that room.
- When the user presses the book button, a request is made to the BE and if the request is successful, the user can see the number of spots decrease by 1 and a snackbar showing that the room (name) was booked.
- The app uses Dagger Hilt for dependency injection.
- The app uses a ViewModel that connects to a Repository. The repository then connects to a Retrofit service to fetch data and to the local database.
- The app stores the list of rooms in a Room database.
- This database is the single source of truth. The UI will update whenever the content in the DB changes.
- The app uses Flows to be a reactive app.
- Unit tests are added for some classes.

TODO:
- Add progress bar to show that the screen is loading.
- Add error snackbar when there is an error fetching the data.
- Add empty state to show that there are no rooms available (if nothing comes from the BE and there is no error).
- Add error snackbar when there is an error booking a room.
- Add fonts used in the design.
- Add correct colors for dark theme.
- Add tests for repository class.
- Move dimens and strings to a dimens or strings file.