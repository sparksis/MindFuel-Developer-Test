# Continious Integration

## Jenkins
[![Build Status](http://pi-jenkins.sparksis.com/buildStatus/icon?job=MindFuel Test)](http://pi-jenkins.sparksis.com/job/MindFuel Test)
 
User: guest
 
Password: 9VwRDs8ozi

## GitLab pipeline

[![build status](https://git.mindfuel.ca/tests/colton/badges/master/build.svg)](https://git.mindfuel.ca/tests/colton/commits/master)


# Build
    mvn clean install 
    
# Run
	Works well in eclipse under default config from importing
	
    java -jar BootTomcat/target/ROOT.jar

# Known Defects

1. Redo doesn't check full stack
1. Line/point thickness not set
1. CSRF Token failures are not handled nicely
1. App must be executed twice.  Once for db creation and again to use the db.
1. App not built for minification
