# Freshfoods

A Simple Food Delivery website where users can order Fruits and Vegetables.  
The FrontEnd is built using with [Angular](https://angular.io), while the Backend uses Jave Servlets (v4.0) which are to connected to MySQL Database (5.6.20).

All the payments are processed by **[Rave by Flutterwave](https://ravepay.co/).**


**_The website is Live and can be previewed [here](http://3.139.60.126/FreshFoods)._**


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

  ***Note: You can also edit the database name in the database.sql file***

 * ### Update the Credentials to Connect to the Database
    Update the ***web.xml*** file located in *PROJECT_ROOT/backend/FreshFoods/WebContent/META-INF*

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
	<Resource name="jdbc/YOUR_DATABASE_NAME" auth="Container" type="javax.sql.DataSource"
        maxTotal="10" maxIdle="5" maxWait="10000"
        username="YOUR_DATABASE_USERNAME" password="YOUR_DATABASE_PASSWORD" driverClassName="com.mysql.cj.jdbc.Driver"
        url="jdbc:mysql://MYSQL_HOST:PORT/YOUR_DATABASE_NAME"/>
</Context>
```
    
* ### Setup Rave Account

  Go to **[Rave by Flutterwave](https://ravepay.co/)** website and set up your free account. Once logged in,

  * On the Left side panel under Billing Tools, make sure that the mode is switched to Test Mode.
  * Then go to Settings > API to get your Test API Public key.

* ### Setup Environment variables for the frontend
  Create an ***env.ts*** file in *PROJECT_ROOT/src/environments* folder with the following format:

  ```typescript
  export const env = {
    BACKEND_URL: 'YOUR_BACKEND_URL',
    RAVE_API_KEY: 'YOUR_RAVE_API_KEY',
  }

  ```
  
## Run the Project

* Database
    If you haven't set up the MySQL database to run as a service, run:
```bash
$ mysqld
```

* Deploy Jave Servlets on Tomcat  
    
    Package the backend in a war file and deploy it deploy on Tomcat server.  
   
   The easiest way to achieve this is to: Open the ***PROJECT_ROOT/backend*** folder in Ecplise and right click on the project name and select Export > WAR File.
   Copy the war file in the ***%CATALINA_HOME/webapps***

* Serve the Website  

    * Install the required Packages and serve the website:

```bash
> npm install
> npm run start
```
