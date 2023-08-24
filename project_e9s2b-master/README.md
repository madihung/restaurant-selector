# Dinner Decider

## To end the struggles of deciding what to eat

One of the hardest parts about making plans to go out is deciding what to eat. This application is the 
perfect solution to end those planning problems, and can be used by anyone trying to decide where to go 
for a meal in the Vancouver area. 

The user can view a list of restaurants the application provides and has the option of adding restaurants 
of their own or completely creating their own list of restaurants for the application to choose from. They
then have the option of selecting a type of food they wish to eat. The application will randomly
select a restaurant based on the user's selections.

As a user,
- I want to be able to view the list of given restaurants
- I want to be able to add restaurants to the list of given restaurants
- I want to be able to select a type of food 
- I want a random restaurant to be picked from the list of restaurants with the selected type of food
- I want to be able to create a new list of restaurants
- I want to be able to save the new list of restaurants I created
- I want to be able to optionally load the restaurant options I made from file when the program starts

## Instructions for Grader
**To add a restaurant to your list:**
1. Select the food type of the restaurant you want to add in the drop down menu
2. Enter the name of the restaurant into the text box labelled "Enter the restaurant name"
3. Hit enter (the restaurant name will appear in the space below).
The restaurant will automatically be added to the list

 **To get a randomly selected restaurant from your list:**
 1. Press the choose button in the GUI
 2. The name of the randomly chosen restaurant will appear in window 
 
*You can save the state of my application and trigger my audio component by clicking the save button.*

*You can reload the state of my application by clicking the load button.*
The loaded restaurants will appear in the text area.

##Phase 4 Task 2
A Hashmap is used in the RestaurantOptions class. The methods that play a role in this map interface include:
- addRestaurant() which adds a restaurant to the map
- handlesAddRestoDefault() which adds a user-made restaurant to the default map loaded in main by 
calling addRestaurant() in its code body
- addUserRes() which adds a user-made restaurant to an empty hashmap by calling addRestaurant()

##Phase 4 Task 3
1. In the Restaurant Options class, the chooseUserRestaurant() and chooseDefaultRestaurant() have similar behaviors, 
so there is similar code. To avoid semantic coupling, I abstracted the duplicate code into another function called 
randomRestaurant().
2. I split UserConsole into two new classes, UserConsoleDefault and UserConsoleNew for better cohesion. 
UserConsoleDefault holds methods related to adding and operating on the loaded list, while UserConsoleNew holds 
methods and fields related to creating and adding restaurants to a new list through the GUI. 