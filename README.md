# Time-Tracking-System
## Project description:
Administrator binds Task to Employee.
Employee could have one or several Tasks.
Employee sets spent time amount for each Task.
Employee can send request to add or remove particular Task.

## Developer:
Dudchenko Andrei
## Mentor:
Lyashenko Maxim

# Prerequisites to run project:
1. To have installed Java JDK
2. To have installed MySQL

# How to run project:
## Deployment:
1. Download and unzip the archive with the project or clone using Git.
2. Create "time_tracking" scheme in MySQL using "db_creation.sql" file in root folder or using DBInitializer utility program (see Additional information section below). Database connection properties could be found in ua.training.tts.constant.DBParameters.java file. By default login: admin and password: admin are used there.
3. Istall maven http://www.apache-maven.ru/install.html
4. In project directory open command line
5. Enter command "mvn tomcat7:run"
6. Open browser and follow the link http://localhost:8888/company/tts

# Additional information:
There is a DBInitializer utility for this project. With it you can easily create/drop required scheme with some mock data.
Initialize class will create "time-tracking" scheme in MySQL (and drop it if it exist).
DeInitialize will drop this scheme from MySQL.

Link to utility:
https://github.com/Lanfirus/Utilities

# How to start using application
Initially there are no users in database and there is no way to create administrator other than to create it fully manually in database or to create ordinary employee through application itself (using Registration) and then manually change account type to admin in database.
Existing administrators could change account type of other users in the system.