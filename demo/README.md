# Java project

Property file:

Smart Contract 
                    
                        config:
                    
    smart.localhost = ETH node (example: myetherapi.com, etherscan.io, infura.io, giveth.io)
    smart.private.key = owner private key
    smart.from = owner address
    smart.to = contract address
    smart.gas.price = gas price for transaction in WEI
    smart.gas.limit = gas limit for transaction in WEI
    
Swagger 

                         config:
                         
    
    swagger.host = application host. 
        (entrance from browser <host>/swagger-ui.html)
        for example - http://localhost:8080/swagger-ui.html
    swagger.api.version = your text optional
    swagger.license.text = your text optional
    swagger.title= your text optional
    swagger.description = your text optional
    
    
HTTP code:

    200 - Success
    
        /api/send-sha
            {tx_id: "1"}
        
        /api/read-tx/{txId}
        
            {
              "id_message": "2",
              "id_from": "2",
              "id_to": "2",
              "sha": "2"
            }
        
        or {}
        
        /api/read-sha/{sha}
        
            {
              "id_message": "1",
              "id_from": "1",
              "id_to": "1",
              "tx_id": 2
            }
        
            or {}
           
    
    400 - Failed to connect for node or other unexpected exceptions
    2007 - Failed to process (transaction fail)
    2008 - Not enough money to create blockchain tx or not enough gas_price/gas_limit
