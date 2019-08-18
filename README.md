# web-server
Simple thread-pool web file server


1. Compiling
    ```
    mvn clean install
    ```
2. Starting the web-server
    ```
    mvn java:exec
    ```
    OBS: On startup, the file repository is emptyed.
3. Configuration
    ```
    The config.properties file contains the project configurations for the application port, 
    the root directory of the file system.
    ```
    
4. At the moment the following api is supported:
    ```
    GET     /welcome            => the welcome page
    GET     /file?id=filename   => retrieves the file
    PUT     /file?id=filename   => updates the file
    POST    /file?id=filename   => saves the file
    DELETE  /file?id=filename   => deletes the file
    ```
    All other requests return a 404.