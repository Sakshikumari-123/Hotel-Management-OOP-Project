The Hotel Management System is a Java-based application designed to efficiently manage hotel operations
by allowing users to store customer details, book rooms of four different types, order food for specific
rooms, unbook rooms, and generate bills, while also providing the ability to view room features and check
room availability. It is a menu-driven program that runs continuously until the user chooses to exit.
File handling is implemented to save the current status of the hotel, including customer details, 
booked rooms, and food orders, when the program exits to ensure that no data is lost, and the stored 
file is read when the application restarts to restore the previous hotel status. Additionally, file 
writing is performed in a separate thread to enable parallel execution, and a user-defined exception 
is thrown if a user attempts to book a room that has already been allotted, with proper exception
handling mechanisms implemented to effectively manage unexpected errors.
##### Topics Covered:-
Classes and Objects, Inheritance, File Handling with Objects, ArrayList, Implementing 
Interfaces, User-Defined Exceptions, Multithreading, and Exception Handling.
