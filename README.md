# Build
    mvn clean install 
    
# Run
	Works well in eclipse under default config from importing
	
    java -jar BootTomcat/target/ROOT.jar

# Known Defects

1. Redo doesn't check full stack
1. Line/point thickness not set
1. CSRF Token failures are not handled nicely
1. App must be executed twices.  Once for db creation and again to use the db.