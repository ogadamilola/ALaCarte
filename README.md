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
The managers will be able to track 
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
For now installation is simply pull the main repository and run the StartApplication.java class

Will Update
### Prerequisites (Undefined For Now)
### Dependencies (Undefined For Now)

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
## Authors and acknowledgment
Great work to all the members of A La Carta coding group.

Special thanks to our TAs Kartik Kapoor (kak110) and Rushil Ramdharee (rtr697)


## Project status (Unchanged For Now)
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.
