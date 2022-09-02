package com.example.MC3;

import com.example.MC3.dto.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import java.util.Date;

@Controller
public class BaseController
{

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Value("${my.mc1.scheme}://${my.mc1.host}:${my.mc1.port}/${my.mc1.post-path}")
    private String mc1URLPath;

    @Autowired
    public BaseController( ObjectMapper aObjectMapper, RestTemplate aRestTemplate )
    {
        objectMapper = aObjectMapper;
        restTemplate = aRestTemplate;
    }

    @KafkaListener(topics = "${my.kafka.topic}", groupId = "${my.kafka.group-id}")
    public void listenGroupFoo( String message ) throws JsonProcessingException
    {
        var messageDTO = objectMapper.readValue( message, MessageDTO.class );
        messageDTO.setMC3Timestamp( new Date() );
        restTemplate.postForObject( mc1URLPath, messageDTO, Void.class );
    }
}
