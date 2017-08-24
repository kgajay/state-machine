This is sample maven test project for ui automation using selenium

Java lib =>
    
    DropWizard, Spring, Mysql
    
Steps to configure

    1: Install maven3
    
    2: Intall Java 1.8
    
    3: clone project to your local, git clone https://github.com/kgajay/state-machine.git
    
    4: go to state-machine
    
    5: download dependency => mvn clean package ( this will install all external lib's)
    
    6: start server => java -jar target/state-machine-0.0.1-SNAPSHOT.jar  ./src/main/resources/config.yml (this will start server on http://127.0.0.1:9020)
    
    7: To check run below command
        
        A)  curl -X GET http://127.0.0.1:9020/v1/app/name
        
        B)  curl -X GET http://127.0.0.1:9020/admin/ping
        
        C)  curl -X GET http://127.0.0.1:9020/admin/healthcheck    
        
    8: API
        A) Add state machine
            curl -X POST \
              http://127.0.0.1:9020/v1/state-machine \
              -H 'content-type: application/json' \
              -d '{
            	"name": "ABCD",
            	"description": "First state machine",
            }'
        
        B) Get state mahine curl -X GET http://127.0.0.1:9020/v1/state-machine/{id}
            eg: curl -X GET http://127.0.0.1:9020/v1/state-machine/1
               Response: {
                             "id": 1,
                             "name": "ABCD",
                             "description": null,
                             "start_node_id": 0,
                             "created_at": 1503568707000,
                             "updated_at": 1503568707000
                         }
          
        C) Update state machine
            curl -X PUT http://127.0.0.1:9020/v1/state-machine/1 \
                         -H 'content-type: application/json' \
                         -d '{
                       	"name": "ABCD",
                       	"description": "First state machine",
                       	"start_node_id": 1
                       }'
                        
        D) Add a node
            curl -X POST http://127.0.0.1:9020/v1/node \
              -H 'content-type: application/json' \
              -d '{
            	"name": "B",
            	"description": "state B"
            }'
            
            Response: {
                          "id": 2,
                          "name": "B",
                          "description": "state B",
                          "is_current": false,
                          "created_at": 1503570383000,
                          "updated_at": 1503570383000
                      }
                      
        E) GET a node by id curl -X GET http://127.0.0.1:9020/v1/node/{id}
            eg: curl -X GET http://127.0.0.1:9020/v1/node/1
            Response: {
                          "id": 1,
                          "name": "A",
                          "description": "state A",
                          "is_current": false,
                          "created_at": 1503569705000,
                          "updated_at": 1503569705000
                      }
        
        F) Add a transition
            curl -X POST http://127.0.0.1:9020/v1/transition \
              -H 'content-type: application/json' \
              -d '{
            	"path": 2,
            	"source_node_id": 2,
            	"destination_node_id": 1
            	
            }'
            
        G) execute (Move from one state to another via path )
            curl -X POST http://127.0.0.1:9020/v1/state-machine/execute/{nodeId}/{path}
        
            eg: curl -X POST http://127.0.0.1:9020/v1/state-machine/execute/2/2
                Response: {
                              "id": 1,
                              "name": "A",
                              "description": "state A",
                              "is_current": true,
                              "created_at": 1503569705000,
                              "updated_at": 1503572311000
                          }
      
        H) print (state machine)
            curl -X GET http://127.0.0.1:9020/v1/state-machine/{stateMachineId}/print
            
            eg: curl -X GET http://127.0.0.1:9020/v1/state-machine/1/print
                            
            Response: [
                          "*A -> B ( 1 )",
                          "B -> A ( 2 )"
                      ]