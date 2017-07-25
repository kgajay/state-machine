This is sample maven test project for ui automation using selenium

Java lib =>
    
    DropWizard, Selenium, Spring, Mysql
    
Steps to configure

    1: Install maven2
    
    2: Intall Java 1.8
    
    3: clone project to your local, git clone https://github.com/kgajay/sample-ui-scraping.git
    
    4: go to sample-ui-scraping
    
    5: download dependency => mvn clean package ( this will install all external lib's)
    
    6: start server => java -jar target/sample-ui-scraping-0.0.1-SNAPSHOT.jar  ./src/main/resources/config.yml (this will start server on http://127.0.0.1:9020)
    
    7: To check run below command
        
        A)  curl -X GET http://127.0.0.1:9020/v1/app/name
        
        B)  curl -X GET http://127.0.0.1:9020/admin/ping
        
        C)  curl -X GET http://127.0.0.1:9020/admin/healthcheck    
        
    8: Routing number search from site API
        
        A) curl -X PUT 'http://127.0.0.1:9020/v1/store/routing-number?q=1' -H 'content-type: application/json'
              
    9: Sample crud API 
        
        A) Add a bank info 
            curl -X POST http://127.0.0.1:9020/v1/bank-info \
                 -H 'content-type: application/json' \
                 -d '{
                "routing_number": "122000030",
                "name": "Bank of America",
                "city": "SAN FRANCISCO",
                "state": "CA",
                "zip_code": "94137",
                "address": "PO BOX 37025"
               }'
           
        B) Get bank info by id
            curl -X GET http://127.0.0.1:9020/v1/bank-info/1
        
        C) Get bank info by routing number
            curl -X GET http://127.0.0.1:9020/v1/bank-info/search/122000030
        
        D) Search bank info by name
            curl -X GET 'http://127.0.0.1:9020/v1/bank-info/search?name=Bank%20of%20America&limit=10&offset=1'
            curl -X GET http://127.0.0.1:9020/v1/bank-info/search?routing_number=2
        
        E) Delete bank info by routing number 
           curl -X DELETE http://127.0.0.1:9020/v1/bank-info/122000030
        
        F) Update bank info by routing number 
            curl -X PUT \
              http://127.0.0.1:9020/v1/bank-info/122000030 \
              -H 'content-type: application/json' \
              -d '{
            	"routing_number": "122000030",
            	"name": "Bank of America 2",
            	"city": "SAN FRANCISCO",
            	"state": "CA",
            	"zip_code": "94137",
            	"address": "PO BOX 37025"
            }'
           
  