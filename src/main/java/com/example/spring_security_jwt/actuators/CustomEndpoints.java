package com.example.spring_security_jwt.actuators;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id="my-custom-endpoint") // the endpoint will be available at /actuator/my-custom-endpoint
public class CustomEndpoints {

    Map<String,String> customMap = new HashMap<>();

    /**
     * Adding versions in the /actuator/my-custom-endpoint endpoint
     * @return Map<String,String> list of versions
     */
    @ReadOperation
    public Map<String,String> readCustomMap(){
        customMap.put("v0.1","spring boot actuator integrated");
        customMap.put("v0.2","spring security integrated");
        return customMap;
    }

    /**
     * fetching dynamic parameter
     * @param version
     * @return version
     */
    @ReadOperation
    public String readCustomMapWithPathvariable(@Selector String version){
        return customMap.get(version);
    }

    /**
     * modifying internal variables
     * @param version
     * @param description
     * @return ResponseEntity
     */
    @WriteOperation
    public ResponseEntity<Map<String,String>> addIntoCustomMap(String version, String description){
        customMap.put(version,description);
        return ResponseEntity.status(HttpStatus.CREATED).body(customMap);
    }

    /**
     * deleting an internal variable with dynamic parameter using @selector
     * @param version
     */
    @DeleteOperation
    public ResponseEntity<Map<String,String>> deleteFromCustomMap(@Selector String version){
        customMap.remove(version);
        return ResponseEntity.status(HttpStatus.OK).body(customMap);
    }
}
