# ChoreAssign

## An application for managing household chores

This application will help members of a household divide, assign and remember recurring chores. Chores 
may need to be completed on different intervals, for example:

- feeding a pet (daily)
- taking out the garbage (weekly)
- cleaning the fridge (monthly)

This application will allow users to define chores with intervals on which they should be completed and the time to
complete the chore on the specified interval. Users can then add household members and manage chores assigned to each 
household member. Users will also be able to view their household members and the chores assigned to each. 
This will help users remember and track what chores they and others in their household are responsible for. 

This application could be useful to anyone who shares a living space with roommates, their significant
other, their family, or anyone else. My motivation for this project comes from my own household, where my partner and I
often discuss how to divide chores and how often they will be done.

## User Stories

- As a user, I want to be able to create a new chore with a name, description, frequency (daily, weekly, or monthly), 
  and time commitment and add it to a list of chores
- As a user, I want to view the existing list of chores
- As a user, I want to be able to edit an existing chore in the list of chores to be assigned
- As a user, I want to be able to delete a chore from the list of chores to be assigned
- As a user, I want to be able to create a household member with a name and empty list of assigned chores
- As a user, I want to view the existing household members
- As a user, I want to be able to remove a household member
- As a user, I want to be able to assign multiple chores to individual household members
- As a user, I want to be able to remove chores assigned to household members
- As a user, I want to be able to view the chores assigned to each household member
- As a user, I want to be able to view all household members and the chores assigned to them
- As a user, I want to be able to view the total time required for chores assigned to a household member
- As a user, I want to be able to save created chores and household members lists to a file*
- As a user, I want to be able to load chores and assignments from a file*

\* code for persistence implementation based on CPSC210 JsonSerializationDemo

## Instructions for grader

### Visual component:
- Run ChoreAssignUI to see the splashscreen.

### Add multiple X to Y:
- To add a chore:
  - Click the "Add Chore Button". This will open a pop-up window.
  - Fill in the chore fields according to the pop-up window.
  - Click "OK" to add the chore to the list of chores.
- To add a person:
  - Click the "Add Person" button at the bottom of the window.
  - Enter a name in the pop-up window. The name must be unique from any existing names.
  - Click "OK" to add the new person.
- To edit a created chore:
  - Click on a chore (row) in the top table containing the created chores to highlight it.
  - Click on the "Edit Chore" button below. This will open a pop-up window.
  - Change the chore fields as necessary, unchanged fields will remain unchanged.
  - Click "OK" to apply the changes.
- To assign a chore to a person:
  - Click on a chore (row) in the top table containing the created chores to highlight it.
  - Click on the "Assign Chore" button below. This will open a pop-up window.
  - Select a person to assign the chore to in the drop-down.
  - Click "OK" to apply changes.

### Persistence:
- To load the application state:
  - Click the "Load" button in the top left corner of the window.
- To save the application state:
  - Click the "save" button in the top left corner of the window.

## Phase 4: Task 2
### Representative EventLog output
Sun Apr 09 23:04:35 PDT 2023
Created chore named Dishes
Sun Apr 09 23:04:53 PDT 2023
Created chore named Laundry
Sun Apr 09 23:04:57 PDT 2023
Created person named Joey
Sun Apr 09 23:05:04 PDT 2023
Assigned Laundry to Joey

## Phase 4: Task 3
### Reflection
If I were to refactor my app I would try to combine the console and GUI versions of the app, so that there is one main 
UI class that runs the app. Currently, the user has to choose whether to run the console or GUI version. Doing this
would probably involve refactoring the ChoreAssignApp class so that it can deal with user inputs from the console or 
GUI. The advantage of this refactoring would be to reduce the points of control. That way if the model classes or the 
behaviour of the app change, then the console and GUI UI classes don't need to be modified individually.