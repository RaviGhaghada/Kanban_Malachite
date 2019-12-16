# Team Name
Kanban Malachite

# Project
Mello: A single user desktop application to manage Kanban boards.

# Tools Used

Java 11.0, JavaSwing, Gradle 4.10.2, JavaFX, TestFx (allowed by Dr. Keppens in some forum post), GSon (allows us to convert an object into a json string, SimpleJSON (allows us to navigate a json structure)

# Data storage

We store all of our data in ./src/main/resources/data/databoard.json
Currently, it holds 1-2 weeks worth of board activity.

# Developers:

Mariam Marek Rojina Manvi Ravi

# Setting up gradle wrapper to obtain Gradle 4.10.2 (only for Mac/Linux):

CUT (not copy) the contents of build.gradle and PASTE it another temporary file.
Save the build.gradle file so that it's empty.

Start gradle

> gradle

And then run:

> gradle wrapper --gradle-version 4.10.2

Now mark it as an executable

> chmod +x gradlew

Download the wrapper by executing it:

> ./gradlew

From now on, run all gradle commands using the wrapper, so instead of 'gradle build'
use:

> ./gradlew build

Now go back to the temporary file where you put your gradle folder and paste it back
to build.gradle

Then run:

> ./gradlew build

It should download all the libraries needed for the project.
