# 370_Project
# Team # 1

## Group Name: A La Carte
### Team Members
````
Dami Adetula (ola746)
Evan James (ecj874)
Justin Nahirney (jmn566)
Kristian Manaloto (kcm662)
Randall Sungcang (rts063)
Tomi Bamimore (ise652)
Vidhi Tusharbhai Dhorajia (ojk162)
````
## Product Description (Currently Deliverable 0 Description)
This product works as a management system for restaurants as well as focusing on optimizing and simplifying the flow of productivity. 
The managers will be able to update the restaurant's storage, set the restaurant's menu,
track the work done by the restaurant's workers and receive reports for how the day went.
Managers are able to easily access the restaurant's information and create accurate updates with relative ease.
While actions in the program for workers could be done by a single button click, this benefits the
general workflow of the cooks and help reduce confusion in the kitchen.
### Product Features
```
An Easy To Understand GUI that is clear and provides the user with many options
    - Each worker is able to have their own specific stations
    - Able to fit all types of screen sizes
Allows for multiple users and contains the necessary databases for a restaurant system
Able to open a Manager side or a Worker side on the Products main menu
    - Manager Side (Requires a manager log in) grants
        - Access to Inventory
        - Access to Recipes
        - Access to Menu Items
        - Access to Staff Information
        - Access to Restaurant Summary
    - Worker Side grants
        - Access to Server Screen (Requires server log in)
        - Access to Kitchen Screen
Inventory Features
    - Managers can add ingredient to a restaurant inventory
		- set cost of ingredients
		- set quantity
		- set ingredient types
		- set allergen warning
		- quantity warning alert for low ingredients 
		- set reorder thresholds for all ingredients
	- Ingredient quantity auto updates to account for orders punched in
Recipe Features
    - Recipe Creator, create custom recipes for the restaurant
		- set recipe names
		- price of ingredients auto updates depending on the ingredients added and the quantity
		- add ingredients and ingredient quantity to recipe
		- set description
		- set preparation instructions
		- set prep time, used for kitchen timing
		
	- Recipe List
		- easily view and edit existing recipes
		- delete out dated recipes
		- view recipe display, that the staff will see
Menu Item Features
    - A list of Menu Items that is to be displayed on the Server Screen
        - The user will be able to select a Menu Item on the list to display it's information
        - The user will also be able to edit or delete the selected Menu Item
    - An option that allows the User to create new Menu Items
        - The user will then be able to manually set the Menu Item's information and select recipes
    - Editing and creating a new Menu Item will be saved in the database
Staff Information Features
     - Keep track of all your staff
		- add them by name, SIN, and a custom ID
		- set their positions
		- managers are added in the sign-up
		- managers can change log in info
	- Keep track of tips earned by staff
Server Side Features
    - Will display all of the Menu Items in the database to the Server Screen
        - The user will be able to select these items to either add the item to the order 
        or customize the item before adding to the order
    - The user is able to open and recieve a Stock Report
    - The user is able to open and see the status of Tables
        - Possible to add/remove Tables on the system depending on the Restaurant layout
    - The user is able to refund orders 
    - There will be a display of the Menu Items that is included in the current order
        - The user will be able to either void the current order or send the order to the Kitchen
    - Communication with the Kitchen side possible from the Server side
Kitchen Side Features
    - Will be able to display the orders sent from the Server Side
        - The user is able to view the item's recipe or to complete the item, 
        the user will also be given the option to cancel the full order
    - Each order will contain a timer next to it, this will show the order's current time and the expected preparation time
        - The timer display will update depending on the current time of the order
    - Communication with the Server side possible from the Kitchen
Restaurant Summary Features:
	- Track restaurant earnings and trends
		- track total orders
		- see the most popular menu items
		- see ingredient usage and total cost
		- track total income and gross earnings for the day

	- View reports from previous days
			
```
## Product Installation 
Simply download this program's zip file and extract it.

Once extracted, open the source module 370_project on IntelliJ and then build the artifact
ALaCarteRestaurantSystem.jar.  Once the jarfile is built, run it.


### Prerequisites 
- IntelliJ
### Dependencies 
- Maven
- JavaFX
- ControlsFX
- BootstrapFX
- Gson

## Product Usage
    Start Up:
		Sign up managers using the sign-up page
		Log in with set credentials
		Once you log in, you will be sent to the manager main screen
		Here you have access to:
			Inventory
			Recipes
			Menu Items
			Staff
			Restaurant Summary

	Inventory:
		Add your ingredients to the inventory by:
			Setting the name
			Set your current quantity (can be set to 0 if your planning ahead)
			Set how you want to measure the ingredients (Pounds or by Count)
			Select which type the ingredient falls under
			Select the price per unit of the ingredient, this helps you keep better track of costs
		
			Optionally, set your reorder point for the ingredient now, this defaults to 0

			Hit Submit
		Now your done adding your ingredients.

		If you want to make changes to the ingredients, say editing quantity, select the ingredient from the table, make your edits, and hit "Update" of submit
		
		"Clear" clears all the fields
		
		To delete an ingredient, select the ingredient from the table and hit "Delete"

    Recipes:
		To create your recipes head to the Recipe Tab and hit create new recipes. You'll want to create recipes for single food items, not whole combos. For example "Burger" and "Fries" as seperate recipes not "Burger and fries", save that for Menu Items! This will make it easier to make new menu items in the future.

			Enter your recipes name EX "Burger"
			Leave the price of ingredints box blank for most accurate cost reports, it will automatically update with to total price of the recipe
			Enter a description of the recipe (the servers and cooks will beable to read this)
			Enter the preparation instructions (so your cooks can read)
			Enter a preparation time (this is used for the kitchens timing system)
			
			Then you can start adding the ingredients and the quantity, this will help keep your portion sizes consistent so you don't end up losing money
	
			Once you have everything filled out and your ingredients set, hit "Save Recipe" to save

	Menu Items:
		Now you have your recipes you can start creating menu items! Go back to Manager main screen and hit Menu Items. 
		This screen is where you can view you whole menu. From here you can create menu items like "Burger and Fries combo". Menu Items can have multiple recipes, or just one.
		
		To create new Menu Items:
			Hit the "Create New Menu Items
			Enter the name for your Menu Item EX "Burger and Fries"
			Enter a Description

			Start adding your recipes by selecting them from the right box and hitting add, they will get moved over to the left box.
			Your total cost of the recipes will get displayed and you can set the price of the menu item to profit, you don't wanna lose money off these.
		
		Once you are done. Hit "Save Menu Item"

	Staff:
		You will need staff to run your restaurant. To add them, hit the "Staff" button on manager main screen.
		Here you can add your staff:
			Enter their name, set a unique staff ID, and their SIN.
			Set their position as "Cook" or "Server"
		
			Then hit Submit.
			
		To add new managers:
			Go back to the log in page, and sign them up in the sign-up screen, like how you did.

	Restaurant Summary:
		This feature allows you to view your busiest days, most popular menu items, your restaurants ingredient usage and costs and your total income and profit for the day.

		This screen will automatically store this information every new day.
		To view older days, select a date on the Calendar and hit view report, if it exist it will pop-up.

	That is everything you need to know about the Manager Side of A La Carte Restaurant Systems!

	Moving on to the server side:
	    From the main program screen, hit "Worker View", this will take you to the Worker Main Screen.
	    From here servers will need to log in using their unique Staff ID, managers can also log in using theirs, but cooks cannot
    
    Server Side Summary:
        To add an Item to the Order:
            Select an Item and press Add Order Button
        To delete an Item in the Order:
            Hit the X button for Items added in order
            Or hit void order to remove all items in order
        Adding a customization to an Item in the Order
            Hit Customize button and select the Item display in the view
            Select an option button on the left side and an ingredient in the Item
            Hit the set button
        Remove a customization on a customized Items
            Go to Customize View, select an Item and hit the discard button
        Sending an Order to Kitchen
            If there is an Item in the current order, hit the send to kitchen button
        Refunding an Order
            Hit the refund button to open a Refund view
            Hit refund on an Order thats been sent to the Kitchen to refund
        Send an alert to Kitchen
            Hit add note button which opens up a Note View where the server can send an Alert to Kitchen
        View the alerts sent by Kitchen side
            The server will be notified if a note is active by having an orange ! button
            Click the ! button to view Kitchen alerts
        Viewing Tables
            Click the tables button and this will open up a Table View
            The server can add/remove a table, set an order to the table and reserve a table
        Viewing the Stock Reports
            Hit the Stocks button to open up the list of stocks
    Kitchen Side Summary:
        Complete an Order
            This can be done by completing all of the Recipes in an Order
            Can also be done by pressing the complete button
        Increase the order time of a recipe
            Hit the add 1 min button on a specified recipe to increase its current time
        View ingredients in a Recipe
            Hit the view recipe button on a specified recipe to view its ingredients
        Send alert to server
            Hit the send alert button to open up a alert sender view
            Type the alert and send it to Server
        View alerts sent by Server
            The Kitchen side will be notified if a note is active by having an orange ! button
            Click the ! button to view Server alerts
## Authors and acknowledgment
Great work to all the members of A La Carta coding group.

Special thanks to our TAs Kartik Kapoor (kak110) and Rushil Ramdharee (rtr697)


## Project status 
Currently, the program has every user story features implemented.
We even managed to start adding extra features however not all of these extra features are implemented in the current repository.
So if we were to continue working on the program, we would have these extra features implemented alongside with improving the whole
program's GUI and it's containers.

Overall, we managed to finish what we planned on creating.