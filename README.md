# Requirements

## Working 

* Your code will be responsible for setting up the database schema.
  * uses H2DB

* The application will require a login via a web form with a username and password.
* An initial user of Username:admin Password:admin will be pre-seeded.
* The admin user will have functionality to CRUD new users with username & initial password.
* Admin UI elements should be available via some icon. When admin elements are not visible,
  * Click the "person" icon on the far right side of the toolbar when logged in as admin

* the admin should function as a regular account, with a normal drawing canvas (except for the admin access icon)

* You can have a fixed canvas size ie..800x600 – but the overall UI should scale with browser within reason.
  * Fullscreen up to 1920x1920;

* You can set a minimum browser size, but there should be a graceful fail below that minimum.
  * No practical minimum (don't use this on a smartwatch)

* Each 'draw' will be from a mouse press to mouse release
  * Uses sketch.js library defaults.  
  * moving outside the window also triggers the line segment end.
  * moving over the toolbar/title triggers line segment end

* There should be able to be at least 64 draw actions. How you handle drawing over this limit (if present) is up to you.
  * Manually verified, no noticeable performance deprecation.

* A 16 colour pallet should be available in the UI to choose from.
* Line/Point thickness is a 2x2 pixel square. You do not have to provide a way to change this.
* Undo and Redo available for full history to the limit of draw actions above (your choice how to handle over limit)
  * Redo does invalidate after an action is performed.  It is therefore possible to redo on top of new work
  
* Undo only has to remove draw, it does not _have_ to repair damage done by the removal
  * I'm unsure what this is referring to.  The undo functionality is tied to the underlying action stack and therefore there should be no "damage" from undo actions
  
* Drawings should be able to be saved to the backend database, per user.
* Drawings should be able to be loaded from the backend database, per user.
* There should be a button to 'Erase and restart' the canvas
* Erasing a canvas with any draw moves should effect a confirmation prompt
* Saved Drawings will require a name. Unique within an indivudual users set of drawings.
* Code will be checked in at various stages into Mindfuel's GIT repository, and you will be setup with an account for that.

### Backend Requirements

* Java application based on Spring Boot
* Maven for build manager
* Appropriate Unit testing
  * unfortunately cobertura is breaking on a lambda for me so I can't indicate coverage
* Backend to Frontend data interchange format will be json

### Frontend Requirements
* Angular JS 1.5.2 will used to provide quick user interaction, and mostly a single page / api
driven single page application.
* HTML 5 Canvas Object
* Will work on both Chrome and Firefox of latest public release
  * Firefox appears to run poorly with sketch.js
  
* CSS Bootstrapping of your choice, but we will expect a moderately scalable UI
  * Angular Material 
  
* Function over form
  * I'm hoping I achieved both

## Deviation

* Single page application where possible
  * while it would be possible and quite easy to layer dialogs for authentication/authorization it reduces usability on mobile and testability.  I would therefore have pushed for this requirement to be reconsidered in a "real world scendario". 
  * the app is using angular routing (html5mode) and therefore with the exception of spring redirects the user does not technically leave the page.  The transition between /admin and / should therefore feel extremely fast/smooth 

## Incomplete

The following are requirements that am either still working on or didn't notice when going through the first time

* Loading drawings should present a paginated list of available drawings (for a given user)
* Loaded drawings should replay on to a blank canvas with the 'draw' operations in the original order.
    * I'm not sure if this referring to animations or not.  
    * Based on the impl it should be possible to animate but from my email with Richard storing historical data is out of scope
    * based on time left I'm going to omit this requirement  

### Frontend Requirements

* Appropriate Unit testing
  * limited or non-existent (spent too much time elsewhere)


# Continuous Integration

In order to simulate a push-to-deploy with jenkins I've configured a private repository on bitbucket which I push locally to.

Jenkins will then

1. Checkout the code
1. Build the code
1. Test the code
1. Publish the code to the "production" git repository

This model works nicely with cloud hosting providers such as redhat cloud (openshift) and is a model I was am very familiar with.  
Obviously I wouldn't do this with MindFuel intelectual property.

This model also has the added benefit of ensuring that your team is able to always have a working copy of the code despite my changes.

## Jenkins

[![Build Status](http://pi-jenkins.sparksis.com/buildStatus/icon?job=MindFuel Test)](http://pi-jenkins.sparksis.com/job/MindFuel Test)
 
## GitLab pipeline

[![build status](https://git.mindfuel.ca/tests/colton/badges/master/build.svg)](https://git.mindfuel.ca/tests/colton/commits/master)

# Build
    mvn clean install 
    
# Run

Works well in eclipse under default config from importing
	
    java -jar BootTomcat/target/ROOT.jar

# Known Defects

1. Redo doesn't check full stack
1. CSRF Token failures are not handled nicely
1. App not built for to support minification
  * didn't want to deal with ['this',function('this'){}] when refactoring a throw-away app
