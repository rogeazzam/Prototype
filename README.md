# Prototype
## Summary
A team project of five people, we produced a project called “Theater”, that can help 
improve the theater business, and adjust it to the variables instructions during covid-19.
The project is a server-client (tcp/ip protocol), and uses MySql database.

## Project Overview
### Purchasing Theatre ticket / Home ticket

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/1.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/2.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/3.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/4.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/5.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/6.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/7.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/8.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/9.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/10.png)


### Customer's personal page

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/11.png)


Supposing customer with ID = 2 purchased the seats chosen in the previous image:

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/12.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/13.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/14.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/15.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/16.png)


### Managers and Employees management page

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/17.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/18.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/19.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/20.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/21.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/22.png)

![alt text](https://github.com/rogeazzam/Prototype/blob/master/images/23.png)


## How to run:
1. Run Maven install in the parent project.
2. Run the entities using the install exec:java.
3. Run the server using the exec:java goal in the server module.
4. Run the client using the javafx:run goal in the client module.

## Notes:
- Before running go to server->SendMail, and put your gmail account mail and password.
- Go to resources->hibernate.proporties in each of the client/entities/server, and add your database name, and MySql password.
