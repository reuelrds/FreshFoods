# Freshfoods

A Simple Food Delivery website where users can order Fruits and Vegetables.  
The FrontEnd is built using with [Angular](https://angular.io), while the Backend uses Jave Servlets (v4.0) which are to connected to MySQL Database (5.6.20).

All the payments are processed by **[Rave by Flutterwave](https://ravepay.co/).**


Table of Contents:
- [Freshfoods](#freshfoods)
  - [Hosting](#hosting)
  - [Website Pages](#website-pages)
  - [Clone this Repo](#clone-this-repo)
  - [Project Setup](#project-setup)
  - [Run the Project](#run-the-project)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

**_The website is Live and can be previewed [here](http://3.139.60.126/FreshFoods)._**

Test User:
```
Name: Arimus Black  
Email: arimus@testemail.com  
Password: 1234  
```

~~Test Payment Cart:~~
```
Card number: 5531 8866 5214 2950
cvv: 564
Expiry: 09/32
Pin: 3310
OTP: 12345
```
**Note: The app currently successfully places an order and does not interface with Rave. So, it no longer needs this.**



## Hosting
The Frontend, the Backend, and the Database are hosted in separate AWS EC2 instances. The FrontEnd is Hosted on a Apache WebServer (2.0.65) running in an Amazon AWS EC2 instance, and the Backend is served by Apache Tomcat (v9.0.43).

## Website Pages

***TODO: Add a description of what each webpage does.***

## Clone this Repo

```bash
> git clone https://github.com/reuelrds/FreshFoods.git
> cd FreshFoods
```
## Project Setup

* ### Create the Database  
  
    The __*[./backend/FreshFoods/WebContent/WEB-INF](https://github.com/reuelrds/FreshFoods/tree/main/backend/FreshFoods/WebContent/WEB-INF)*__ contains a ***database.sql*** file which can be used to setup the database.

    * Update the *STATIC_FILES_URL* inside the ***database.sql*** to the url where the FrontEnd will be Served.  
    
    *If you are using **ng serve** command to serve the frontend, you can leave it unchanged as the STATIC_FILES_URL is already set to http://localhost:4200 which is the default port used by **ng serve.***
  
    * Open a terminal in your project root and and run the following to set up the database.
  
  ```bash
  $ mysql -u YOUR_USER_NAME -p YOUR_PASSWORD
  ```

    * Once you are logged in to MySQL shell, run:
  
  ```sql
  mysql> source ./backend/FreshFoods/WebContent/WEB-INF/database.sql
  mysql> exit;
  ```



 * ### Update the Credentials to Connect to the Database
    Update the ***context.xml*** file located in *PROJECT_ROOT/backend/FreshFoods/WebContent/META-INF*

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
	<Resource name="jdbc/YOUR_DATABASE_NAME" auth="Container" type="javax.sql.DataSource"
        maxTotal="10" maxIdle="5" maxWait="10000"
        username="YOUR_DATABASE_USERNAME" password="YOUR_DATABASE_PASSWORD" driverClassName="com.mysql.cj.jdbc.Driver"
        url="jdbc:mysql://MYSQL_HOST:PORT/FreshFoods"/>
</Context>
```
    
* ### ~~Setup Rave Account~~

  Go to **[Rave by Flutterwave](https://ravepay.co/)** website and set up your free account. Once logged in,

  * On the Left side panel under Billing Tools, make sure that the mode is switched to Test Mode.
  * Then go to Settings > API to get your Test API Public key.

**Note: The app no longer interfaces with Rave and this step can be skipped.**


* ### Setup Environment variables
  Update the `.env` file with your choosen values.

  
## Run the Project

The project is configured to run in docker containers. So, just run:

```bash
docker compose up -d
```

It will build the and start three separate docker containers, one for frontend, one for the Java backend, and one for the MySQL database.
