# Prototype
## Summary
A team project of five people, we produced a project called “Theater”, that can help 
improve the theater business, and adjust it to the variables instructions during covid-19.
The project is a server-client (tcp/ip protocol), and uses MySql database.

## How to run:
1. Run Maven install in the parent project.
2. Run the entities using the install exec:java.
3. Run the server using the exec:java goal in the server module.
4. Run the client using the javafx:run goal in the client module.

## Notes:
- Before running go to server->SendMail, and put your gmail account mail and password.
- Go to resources->hibernate.proporties in each of the client/entities/server, and add your database name, and MySql password.
