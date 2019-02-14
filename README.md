# SupermarketSimulator
A platform that simulates a supermarket applying various OOP practices and design patterns like Factory, Visitor, Observer etc. Also implemented GUI. Technology used: Java, Swing.

## Installation and Execution
Download the ZIP archive or clone this repository on your locaal device and then you have 2 options:

# Eclipse Project
Simply open the folder Project as an Eclipse project as the project was devoloped fully using Eclipse. In that directory should be the .project file. Then simply run the Test class (if you are not interested in the GUI) or the SupermarketGUI class (if you would like to run it using GUI).

# Run through CLI
```
cd Sources/
make 
make run (if you are not interested in the GUI)
make runGUI (if you would like to run it using GUI)
```
## How it works!

The program takes as input 3 files named: store.txt, customers.txt, events.txt. With the data inside these files, it creates a supermarket simulation. 

The file events.txt contains different events that happen in the supermarket (like adding an item to somebody's WishList or ShoppingCart, removing an item, etc.). This file is only used when you run the application without CLI, writing the file events.txt is the way you manipulate what happens in the supermarket. If you choose to run the program with the GUI, you will have the opportunity to trigger these events by interacting with the GUI.

If you run the GUI app, you will first select between customer and admin and you will not be able to return and choose the other option unless you close the app and restart it. Then you hit upload so that the files customers.txt and store.txt are successfully uploaded.

If you choose Admin, your options are straight forward. If you choose Customer, you will need to insert the name of a valid customer (in the customer.txt file) and then be able to administrate your WishList and your ShoppingCart. From that point on, your options are also straight forward.

To be noted that everything that happens on the GUI also happens in the backend! Although the implementation seems simplistic, collaboration with backend is assured using the Handler class, same class used to handle events in the case without CLI.

## Fun fact

Various design patterns were implemented in this project. I encourage you to discover them by yourself ;) .
