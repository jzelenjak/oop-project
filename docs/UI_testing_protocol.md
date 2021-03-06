# Testing terminology
## Asserts:
* “Assert page change - x” - Asserts that the current page changes to x
* “Assert pop-up - x” - Asserts that x pop-up(s) appears
* “Assert appear - x” - Asserts that ui element(s) x appeared
* “Assert disappear - x” - Asserts that ui element(s) x disappeared
* “Assert set - x in y to/as z” - Asserts that the x parameter has been set in table y to value z
* “Assert set - x in y” - Assert that the row x has appeared in table y
* “Assert delete - x in y” - Asserts that the x element has been deleted in table y
* “Assert display - x as y” - Asserts that x is displayed as y
* “Assert change x to y” - Asserts that x has changed to y
* “Assert display - x has y” - Asserts that x has y inside of it (for UI)
* “Assert contains - x has y” - Asserts that x has y inside of it (for system)
* “Assert no change” - Asserts that nothing has changed

# Setup for all the tests:
1. Start the server
2. Open 4 instances of the app on different machines
3. Open a database query tool


# Scenes

## Main scene: 

### Setup for the tests:
* Open application

### Things to test:
#### “Join Lecture” button 
1. Press the button
2. Assert page change - Join lecture page

#### “Create lecture” button
1. Press the button
2. Assert page change - Create lecture page

#### “Help” button
1. Press the button
2. Assert page change - Help page
3. “Restore lecture” button
4. Press the button
5. Assert page change - Restore lecture page

## Create lecture scene:
### Setup for the tests: 
* Click “Create lecture” button

### Things to test:

#### Enter your name

##### No name 
1. Enter lecture name
2. Do not enter your name
3. Press the “Create lecture” button
4. Assert pop-up - “Please enter your name”

##### Long name
1. Enter lecture name
2. Enter a name composed of 51 characters
3. Press the “Create lecture” button
4. Assert pop-up - “Your name is too long”

#### Enter lecture name

##### No name 
1. Enter the name
2. Do not enter the lecture name
3. Press the “Create lecture” button
4. Assert pop-up - “Please enter the lecture name”

##### Long name
1. Enter lecture name composed of ≥ 256 characters
2. Enter a name 
3. Press the “Create lecture” button
4. Assert pop-up - “Your lecture name is too long

#### “Schedule lecture” checkBox
##### Setup:
1. Enter your name
2. Enter the lecture name

##### Appears

1. Check the box
2. Assert appears - datePicker, hour input field, minute input field

##### Disappears
1. Check the box
2. Uncheck the box
3. Assert disappears - datePicker, hour input field, minute input field

##### Error if empty input
1. Check the box and do not choose the date and/or time
2. Press join lecture
3. Assert pop-up - “Provided date or time is invalid”

##### Error if invalid input (String to the date picker)
1. Check the box
2. Write a string to the date picker
3. Press join lecture
4. Assert pop-up - “Provided date or time is invalid”

##### Error if invalid input (String to the hours and/or minutes)
1. Check the box
2. Write a string to the hours and/or minutes field
3. Press join lecture
4. Assert pop-up - “Provided date or time is invalid”

##### Error if invalid input (Invalid date)
1. Check the box
2. Type invalid date format into the datePicker
3. Choose hours and minutes
4. Press join lecture
5. Assert pop-up - “Provided date or time is invalid”

##### Error if invalid input (Enter a negative number)
1. Check the box
2. Write a negative number to date picker and/or hours field and/or minutes field
3. Press join lecture
4. Assert pop-up - “Provided date or time is invalid”

#### “Question delay” field
##### Setup:
1) Enter your name
2) Enter the lecture name

##### Check if empty (default should be 1 question/minute)
1) Press the “Create lecture” button
2) Assert set - Lecture frequency in Lecture to 60
3) Check if more or equal to 0
4) Input 42 to the field
5) Press the “Create lecture” button
6) Assert set - Lecture frequency in Lecture to 42
7) Check if negative

##### Input -1 to the field
1) Press the “Create lecture” button
2) Assert pop-up - “Please provide a positive number”

#### “Create lecture” button 
##### Check database if lecture info was logged correctly
1. Enter your name as Asterios
2. Enter the lecture name as “IDM”
3. Press the “Create lecture” button
4. Assert set - lecture name in Lecture to “IDM”
5. Assert set - lecturer name in Lecture to “Asterios”

##### Check scene change
1. Enter your name
2. Enter the lecture name
3. Press the “Create lecture” button
4. Assert pop-up - “Secture has been created successfully”
5. Assert page change - Lecturer chat scene

##### Check for prompt about copying lecture ID/moderator key
1. Enter your name
2. Enter the lecture name
3. Schedule a lecture
4. Press the “Create lecture” button
5. Assert pop-up - “Please do not forget to copy the UUID/moderator key”

#### “Help” button
1. Press the button
2. Assert page change - Help page

#### ”Back” button
1. Press the button
2. Assert page change - Previous page

## Join lecture scene:
### Setup for the tests: 
1. Create a lecture (Done in create lecture testing)
2. Save its UUID and moderator key

### Things to test:
#### “Enter name” field 
##### No name 
1. Do not enter your name into the name field
2. Enter enter the lecture ID
3. Press the “Join lecture” button
4. Assert pop-up - “Please enter your name!”

##### Long name
1. Enter lecture name
2. Enter a name composed of 51 characters
3. Press the “Join lecture” button
4. Assert pop-up - “Your name is too long”

##### Bad name (offensive)
1. Enter “Hitler” or “Mussolini” into the name field
2. Enter enter the lecture ID
3. Press the “Join lecture” button
4. Assert pop-up - “Please enter your real name!”

##### Bad name (contains profanity)
1. Enter “F*ck”into the name field
2. Enter enter the lecture ID
3. Press the “Join lecture” button
4. Assert pop-up - “Your name contains curse language!”

#### “Enter lecture ID” field
##### No input
1. Enter your name
2. Do not enter the lecture ID field
3. Press “Join lecture” button
4. Assert pop-up - “Please enter the lecture ID!”

##### Invalid UUID
1. Enter your name
2. Enter a random string which is not a UUID into the lecture ID field
3. Press “Join lecture” button
4. Assert pop-up - “Invalid lecture ID!”

#### “Moderator” checkBox
##### Setup:
1. Enter your name
2. Enter the lecture ID

##### Appears
1. Check the box
2. Assert appears - ”Enter the moderator key” field

##### Disappears
1. Check the box
2. Uncheck the box
3. Assert disappears - ”Enter the moderator key” field

##### No input
1. Check the box
2. Do not enter the moderator key
3. Press “Join lecture” button
4. Assert pop-up - “Please enter the moderator key”

##### Invalid UUID
1. Check the box
2. Enter a random string which is not a UUID into the moderator key field
3. Press “Join lecture” button
4. Assert pop-up - “Invalid UUID”

#### “Join lecture” button
##### Setup:
1. Enter your name
2. Enter the lecture ID

##### Incorrect lecture ID
1. Press “Join lecture” button
2. Assert pop-up - “The lecture ID is invalid”

##### Incorrect mod key
1. Check the “Moderator” checkBox
2. Enter incorrect moderator key
3. Press “Join lecture” button
4. Assert pop-up - “The lecture moderator key is invalid”

##### Enter as a moderator
1. Check the “Moderator” checkBox
2. Enter the correct moderator key
3. Press “Join lecture” button
4. Assert page change - “Lecturer chat scene”

##### Enter as user
1. Press “Join lecture” button
2. Assert page change - “User chat page”

##### Check if lecture has not yet started
1. Schedule a new lecture a couple of days from now (Done in create lecture testing schedule lecture
2. Press “Join lecture” button
3. Assert pop-up - “The lecture has not started yet”
4. Check if lecture is closed
5. Close the lecture created in setup (Done in lecturer chat scene testing)
5. Press “Join lecture” button
6. Assert pop-up - “The lecture has already closed”

#### “Help” button
1. Press the button
2 Assert page change - Help page

#### ”Back” button
1. Press the button
2. Assert page change - Previous page

## Chat scene (lecturer):
### Setup for the tests:
1. Create a lecture (Done in create lecture testing)
2. Enter the lecture as students from several machines (Done in join lecture testing)


### Things to test:
#### Check if lecture and lecturer name are displayed correctly
1. Assert displayed - lecture name as provided lecture name
2. Assert displayed - lecturer name as provided lecturer name

#### Sorting checkBox (by Votes)

##### Ask multiple questions from different machines (Done in user chat scene testing)
1. Upvote one question thrice (Done in user chat scene testing)
2. Upvote one question once (Done in user chat scene testing)
3. Check the sort by Votes checkBox
4. Assert display - questions in the listView as question which was upvoted thrice, then the one with 1 upvote and then the rest

#### Sorting checkBox (by Time)
1. Ask multiple questions from different machines (Done in user chat scene testing)
2. Check the sort by Time checkBox
3. Assert display - questions in the listView in order newest to oldest

#### Sorting checkBoxes (by Time and by Votes)
1. Ask a question and upvote it once (Done in user chat scene testing)
2. Wait for some time (20-30 seconds)
3. Ask another question 
4. Check the sort by Time and by Votes checkBoxes
5. Assert display - second question appears before

#### Filtering checkBox (show answered)
1. Ask multiple questions from different machines (Done in user chat scene testing)
2. Mark one question as answered (Done in question cell lecturer testing)
3. Check the “Answered” checkBox
4. Assert display - listView only has the answered question in it

#### Filtering checkBox (show unanswered) 
1. Ask multiple questions from different machines (Done in user chat scene testing)
2. Mark one question as answered (Done in question cell lecturer testing)
3. Check the “Unanswered” checkBox
4. Assert display - listView has all the questions except one which was marked as answered

#### “Lecture speed” functionality

##### Reset votes
1. Vote for the lecture speed from several machines (done in user scene testing)
2. Press the “Reset lecture speed” button
3. Assert set - Lecture speed in Lecture to 0
4. Assert delete - All the lectureSpeed votes with the lecture ID in the LectureSpeedTable

##### Display correctly (number & votes bar)
1. Vote for the lecture speed from several machines (done in user scene testing)
2. Assert display - number of faster votes as the number of times the user voted for faster
3. Assert display - number of slower votes as the number of times the user voted for slower
4. Assert display - the red bar has length proportional to the slower votes
5. Assert display - the blue bar has length proportional to the faster votes

#### “Copy modkey” button
1. Press the button
2. Assert pop-up - “The moderator key has been copied successfully to clipboard”
3. Assert contains - clipboard has the moderator key

#### “Copy lecture ID” button
1. Press the button
2. Assert pop-up - “The lecture ID has been copied successfully to clipboard”
3. Assert contains - clipboard has the lecture ID

#### “Lecturer mode” button
1. Press the button
2. Assert disappears - everything except for listView, listView’s contents, “Lecturer mode” button, “Exit lecture” button
3. Press the button
4. Assert appears - everything that disappeared in the step 2

#### “Leave lecture” button

##### Prompt
1. Press the button
2. Assert pop-up - “Are you sure you want to leave the lecture”

##### Navigation
1. Press the button
2. Assert change scene - main scene

#### “End lecture” button

##### Navigation
1. Press the button
2. Assert change scene - main scene

##### Prompt
1. Press the button
2. Assert pop-up - “Are you sure you want to end the lecture”

##### Lecture state change in database
1. Press the button
2. Assert set - isOpen in Lecture to false

##### Users are kicked out when the lecture closes
1. Press the button
2. Assert change scene - main scene (all users)

#### “Manage polling” button
1. Press the button
2. Assert pop-up - Manage polling page

#### “Show poll results” button
1. Press the button
2. Assert pop-up - Show poll results page

#### “Export questions & answers”
1. Press the button
2. Assert contains - machine’s disk drive has a text file with textual representation of the questions and answers

#### “Help” button
1. Press the button
2. Assert page change - Help page

##Chat scene (user):

### Setup for the tests:  
1. Create lecture (Done in create lecture page testing)
2. Join the lecture as a student (done in join lecture page testing)

### Things to test:

#### Check if lecture and lecturer name display correctly
1. Assert display - lecture name as provided lecture name
2. Assert display - lecturer name as provided lecturer name

#### Sorting checkBox (by Time)
1. Ask multiple questions from different machines (Done in user chat scene testing)
2. Check the sort by Time checkBox
3. Assert display - questions in the listView in order newest to oldest
4.(Do the same for other boxes as in lecturer chat scene testing)

#### Filtering checkBox (show answered)
1. Ask multiple questions from different machines (Done in user chat scene testing)
2. Mark one question as answered (Done in question cell lecturer testing)
3. Check the “Answered” checkBox
4. Assert display - listView only has the answered question in it

#### Filtering checkBox (show unanswered)
1. Ask multiple questions from different machines (Done in user chat scene testing)
2. Mark one question as answered (Done in question cell lecturer testing)
3. Check the “Unanswered” checkBox
4. Assert display - listView has all the questions except the one which was marked as answered

#### Lecture speed (faster checkBox)

##### Vote 
1. Press the faster checkBox
2. Assert appears - tick on faster checkBox
3. Assert set - speed in UserSpeedVote to faster
4. Change vote
5. Press the faster checkBox
6. Press the slower checkBox
7. Assert disappears - tick from faster checkBox
8. Assert appears - tick on slower checkBox
9. Assert set - speed in UserSpeedVote to slower

##### Cancel vote
1. Press the faster checkBox
2. Press the faster checkBox
3. Assert disappear - ticks from faster checkBox
4. Assert delete - UserSpeedVote in UserSpeedVote table

#### Lecture speed (slower checkBox)

##### Vote 

##### Press the slower checkbox
1. Assert appears - tick on slower checkBox
2. Assert set - speed in UserSpeedVote to slower
3. Change vote
4. Press the slower checkBox
5. Press the faster checkBox
6. Assert disappears - tick from slower checkBox
7. Assert appears - tick on faster checkBox
8. Assert set - speed in UserSpeedVote to faster

##### Cancel vote
1. Press the slower checkBox
2. Press the slower checkBox
3. Assert disappear - ticks from slower checkBox
4. Assert delete - UserSpeedVote in UserSpeedVote table

#### Submit question / enter your question

##### Successful
1. Type a question “Is this a question?”
2. Press “Submit” button
3. Assert appears - asked question (in the listView)

##### Too long (more than 280 characters)
1. Type a question more than 280 characters long
2. Press “Submit” button
3. Assert pop-up - “Your question text is too long”

##### User banned
1. Type a question “Is this a question?”
2. Press “Submit” button
3. Ban the user (Done in lecturer cell testing)
4. Wait slightly more that the set frequency
5. Type a question “Is this a question?”
6. Press “Submit” button
7. Assert pop-up - “You have been banned”

##### User time out
1. Type a question “Is this a question?”
2. Press “Submit” button
3. Type a question “Is this a question?” (wait less than the set frequency)
4. Press “Submit” button
5. Assert pop-up - “Too frequently”

#### “Leave lecture” button
1. Press the button
2. Assert pop-up - “Are you sure you want to exit the lecture?”
3. Assert page changed - main page

#### “Help” button
1. Press the button
2. Assert page changed - help page


## User manual scene:

### Setup for the tests:  
1. Enter the manual scene through main menu (Done in main scene testing)

### Things to test:

#### Category buttons (go through all of them and check)
1. Go through all the category buttons
2. For each assert that the text matches the button’s description

#### ”Back” button
1. Press the button
2. Assert page changed - previous page

# Cells
## Question cell (lecturer):
### Setup for the tests:
1. Ask a couple of valid questions as a user (done in user scene testing)
2. Upvote the a question
3. Enter the lecture as a moderator (done in join lecture testing)

### Things to test:

#### Question info changes
#####  Number of upvotes
1. Assert display - number of upvotes as 1

##### Asked by
1. Assert display - asked by as Asked by: + username of user who asked the question

##### Question text
1. Assert display - question text as the question text typed by the user

##### Answer text
1. Answer the question (done in answer question pop-up testing)
2. Assert display - the answer typed in the previous step

##### Answered checkmark
1. Answer the question (done in answer question pop-up testing)
2. Assert appears - checkmark

##### Status (Typing / answered)
1. Press the edit button
2. Assert appears - text “editing” below the question text

##### Time
1. Assert appears - timestamp is the same time as when the question was asked

#### “Delete” button 
1. Press the delete button
2. Assert disappears - question from the listView of all users
3. Assert delete - question from question table

#### “Ban user” button
##### Ban by ip 
1. Press ban button
2. Select by ip and set the time
3. Press ban 
4. Assert disappeared - the question for which the user has been banned
5. Assert changed - all remaining questions of the banned user contain “...(banned)” in the owner name 
6. Ask question as student from the same ip (done in user chat scene testing)
7. Assert pop-up - “You are banned”
8. (do the same for ID)

##### Select time
1. Press ban button
2. Select by ip
3. Select 3 minutes
4. Press ban
5. Assert disappeared - the question for which the user has been banned
6. Assert changed - all remaining questions of the banned user contain “...(banned)” in the owner name
7. Ask question as student from the same ip (done in user chat scene testing)
8. Assert pop-up - “You are banned”
9. Wait for 3 minutes
10. Assert disappears - all remaining questions of the banned user do not contain “...(banned)” in the owner name anymore
11. Ask question as student from the same ip (done in user chat scene testing)
12. Assert appears - question in the listView
13. (do the same for id)

#### “Mark as answered” button
1. Press the button
2. Assert appears - checkmark (inside the cell)
3. Assert disappears - “MarkAsAnswered” button from the cell

#### “Edit” button
1. Press the button
2. Assert pop-up - edit question scene
3. Edit the text
4. Assert changed - the question text to the edited text
5. Assert changed - question owner name to the moderator/lecturer name + “...(edited)”

#### “Answer manually” button
1. Press the button
2. Assert pop-up - answer question scene
3. Write the answer and press submit
4. Assert appears - the answer text below the question text 
5. Assert appears - checkmark (inside the cell)
6. Assert disappears - “MarkAsAnswered” button from the cell

## Question cell (user):
### Setup for the tests:
1. Ask a couple of valid questions as a user (done in user scene testing)
2. Upvote a question
3. Enter the lecture as a moderator (done in join lecture testing)

### Things to test:

#### Number of upvotes
1. Assert display - number of upvotes as 1 (for the upvoted question)

#### Asked by
1. Assert display - asked by as Asked by: + username of user who asked the question

#### Question text
1. Assert display - question text as the question text typed by the user

#### Answer text
1. Answer the question (done in answer question pop-up testing)
2. Assert display - the answer typed in the previous step

#### Answered checkmark
1. Answer the question (done in answer question pop-up testing)
2. Assert appears - checkmark

#### Time
1. Assert appears - timestamp is the same time as when the question was asked

#### “Upvote” button

##### Upvote
1. Press the upvote button
2. Assert change - Arrow colour to blue

##### Cancel
1. Press the upvote button
2. Press the upvote button again
3. Assert change - Arrow colour back to white

#### “Delete” button

##### Own question
1. Press the button
2. Assert disappears - question from the listView
3. Assert delete - question from the question table

##### Not own question
1. Assert not appears - delete button in the question cell

## Poll/quiz functionality (lecturer):
### Setup for the tests:
1. Press the manage polls button (done in lecturer scene testing)
2. Input a valid poll name (done in poll pop-up testing)
3. Press the add option button thrice
### Things to test:

#### DeLete
1. Press the delete button
2. Assert disappears - the deleted option from the listView

#### No text check
1. Input no text into the input option text field
2. Press the open poll button (done in poll management pop-up testing)
3. Assert pop-up - “Please provide text for the option”

#### Too long text check
1. Input text longer than 280 characters into every option field
2. Press the open poll button (done in poll management pop-up testing)
3. Assert pop-up - “The text for the poll option is too long”

#### “Mark as right answer” checkBox
1. Input valid text into the input option text field
2. Press the checkBox on one of them
3. Press the open poll button (done in poll management pop-up testing)
4. Assert set - isCorrect in polloption table as true (only for the checked option)


## Poll/quiz functionality (user):
### Setup for the tests:
1. Enter a lecture as a student (done in join lecture testing)
2. Open a poll (done in poll management controller testing)

### Things to test:

#### Option text displays correctly
1. Assert display - poll option text as provided poll option text
2. “Vote” button closes the scene
3. Press the “Vote” button
4. Assert disappears - vote on poll pop-up scene
4. Assert set - userVotePoll in userVotePoll table

#### Display results
##### Setup:
1. Close the poll as the lecturer (done in lecturer poll management pop-up scene)

##### Correct option text
1. Assert display - Option text as provided option text
2. Checkmark next to correct ones
3. Assert display - Checkmark as checkmark (only if the option wasmarked as correct)

##### Number of votes
1. Assert display - Number of votes as 1

##### Graph proportional
1. Assert display - Graph as full sized graph (in the option which was voted for)
2. Assert display - Graph as empty space in other options

# Pop-ups
## “Answer question” pop-up:
### Setup for the tests:
1. Ask a question as user (done in user chat scene testing)
2. Mark the question as answered manually (done in the lecturer question cell testing)

###Things to test:

#### Correct question text
1. Type the question text
2. Press the submit button
3. Assert set - answer text in question to submitted answer text

##### No text
1. Press the submit button
2. Assert pop-up - “Please provide the answer text”

#####. “Submit” button  - changes the status to “Answered”
1. Type the question text
2 Press the submit button
3. Assert set - question status in question to answered

##### “Cancel” button
1. Press the cancel button
2. Assert disappear - the pop-up scene disappears

## “Edit question” pop-up:
### Setup for the tests: 
1. Ask a question as user (done in user chat scene testing)
2. Press the edit button (done in the lecturer question cell testing)

### Things to test:
#### Correct question text (Text / question edit field)
1. Assert display - question text as the question text of the question

#### “Submit” button
1.Change the text of the question
2. Press submit button
3. Assert set - the questionText in question as the inputed question text


#### “Cancel” button
1. Press the cancel button
2. Assert disappear - the pop-up scene disappears

## “Manage poll/quiz” pop-up:
### Setup for the tests:
1. Press the manage polling button (done in user chat scene testing)

### Things to test:
#### No input text
1. Type no text
2. Press the open poll button
3. Assert pop-up - “please input question text” 

#### Is poll checkBox

##### Hides/unhides the checkBox for correct in poll option cells
1. Input question text into the field
2. Press add poll button
3. Check the checkBox
4, Assert disappeared - the checkBox disappeared from the poll option cell

##### Sets all poll options to be correct
1. Input question text into the field
2. Press add poll button
3. Input valid text for poll option
4. Check the checkBox
5. Press the open poll button
6. Assert set - isCorrect in poll option as true

#### New Poll

##### Resets all input on the page
1. Input question text into the field
2. Press add poll button
3. Input valid text for poll option
4. Press the open poll button
5. Press the close poll option
6. Assert popup - poll results popup

#### Reset

##### Check if the number of votes in the latest poll was reset
1. Input question text into the field
2. Press add poll button
3. Input valid text for poll option
4. Press the open poll button
5. Vote on poll (done in the user poll vote popup testing)
6. Press the “Reset” button
7. Assert set - number of votes in pollOption as 0


#### Open

##### Check if poll became accessible
1. Input question text into the field
2. Press add poll button
3. Input valid text for poll option
4. Press the open poll button
5. Assert set - open in poll as true

##### Students get pop-up for voting
1. Input question text into the field
2. Press add poll button
3. Input valid text for poll option
4. Press the open poll button
5. Assert popup - vote on poll popup 

#### Close poll

##### Check if poll closed
1. Input question text into the field
2. Press add poll button
3. Input valid text for poll option
4. Press the open poll button
5. Press the close poll button
6. Assert set - isOpen in poll as false

##### Students get pop-up for results
1. Input question text into the field
2. Press add poll button
3. Input valid text for poll option
4. Press the open poll button
5. Press the close poll button
6. Assert popup - poll vote results popup

## Display poll/quiz result:
###Setup for the tests: 
1. Open a poll (Done in pollManager popup testing)
2. Close the poll (Done in pollManager popup testing)

### Things to test:
#### Question text is correct
1. Assert display - question text as the inputted polling question text

## Vote on poll/quiz:
### Setup for the tests:
1. Open the lecture (done in create lecture testing)
2. Enter as a student (done in join lecture testing)
3. Open a poll (Done in pollManager popup testing)

### Things to test:
#### Question text is correct
1. Assert display - question text as the inputted polling question text
