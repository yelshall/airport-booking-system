# airport-booking-system

This is an airport booking system I made in Java that allows you to book flights. This is project essentially gathers all the topics I learnt into one big project.

Those topics include:
* Threads and locks
* Servers and sockets
* OOP
* File I/O
* GUI design

# How it works

You run the server and then run an instance of the client, then you are prompted to enter where you are connecting to (default is localhost, since I am assuming you are running the server locally on your computer.) Then enter the port number which is printed to the console by the server. You can then book three different airlines by entering personal information and booking. There is also I feature I implemented where you can see the passengers on the flight, also, if you reload this list of passengers it will update it if any other customers book the same flight.

# How to run

Just clone the files and run the server, as well as, however many instances of the client you want.

## Notes

The passengers are stored in res.txt and if this file is modified, then there will be errors while running. So, I suggest you keep it as is, or just carefully remove passengers as needed while keeping the original format.

## Expanding the project

I could've went mroe in depth with this project and added an admin login for the server where you can remove people off of flights and such. Moreover, I could've added more than three airlines and a scheduling system for actual flights. Other than that, I think the project is a good display of OOP programming and other programming topics and I am overall happy with it.
