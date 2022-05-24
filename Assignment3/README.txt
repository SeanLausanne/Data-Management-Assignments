There are 3 packages in ./src/ for 3 problems
Each package contains a main function

In ./src/problem1 there are 6 classes: 
Driver acts as the main class that initiates the program and calls each step.
TwitterConnector contains Twitter credentials and connects to Twitter through APIs
TwitterDownloader contains the main business logic of downloading contents from twitter
MongoDBConnection contains the methods related to MongoDB, including connecting to DB, saving and loading contents from DB
TextProcessor contains the method of text cleaning
FileManager contains the method to write files
