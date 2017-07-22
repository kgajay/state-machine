This is sample maven test project for ui automation using selenium

Java lib =>
    
    DropWizard, Selenium, Mysql
    
Steps to configure

    1: Install maven of version 3
    
    2: Intall Java 1.8
    
    3: clone project to your local, git clone https://gitlab.com/kgajay/sample-ui-scraping.git
    
    4: go to sample-ui-scraping
    
    5: download dependency => mvn clean package ( this will install all external lib's)
    
    6: start server => java -jar target/sample-ui-scraping-0.0.1-SNAPSHOT.jar  ./src/main/resources/config.yml (this will start server on http://127.0.0.1:9020)
    
    7: To check run below command
    
        A)  curl -X GET http://127.0.0.1:9020/v1/app/name
        
        B)  curl -X GET http://127.0.0.1:9020/admin/ping
        
        C)  curl -X GET http://127.0.0.1:9020/admin/healthcheck    
        
        