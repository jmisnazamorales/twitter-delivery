package co.edu.uniandes.twitter.delivery.controllers;

import co.edu.uniandes.twitter.delivery.entities.Tweet;
import co.edu.uniandes.twitter.delivery.models.ResponseTwitter;
import co.edu.uniandes.twitter.delivery.services.TwitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class TwitterController {

    private final TwitterService service;

    private TwitterController(TwitterService service){
        this.service = service;
    }


    @GetMapping("/tweets")
    public ResponseEntity<List<Tweet>> getAllTweets(){
        List<Tweet> values =  service.getAll();
        if (values.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
        }
    }

    @GetMapping("/tweets/count")
    public ResponseEntity<Integer> getCountTweets(){
        List<Tweet> values =  service.getAll();
        if (values.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(service.getAll().size(), HttpStatus.OK);
        }
    }

    @GetMapping("/tweets/one")
    public ResponseEntity<Tweet> getTweet(){
        Optional<Tweet> tweet =  service.findById("");
        return new ResponseEntity<>(tweet.get(),  HttpStatus.OK );
    }

    @GetMapping("/tweets/total/country/find")
    public ResponseEntity<ResponseTwitter> getAllTwitByCountry(){
        ResponseTwitter response =  service.countByCountry();
        return new ResponseEntity<>(response,  HttpStatus.OK );
    }

    @GetMapping("/tweets/{country}/find")
    public ResponseEntity<ResponseTwitter> getTweetsByCountry(@PathVariable(required = true)String country){
        ResponseTwitter response =  service.mostPopularHashTagByCountry(country);
        return new ResponseEntity<>(response,  HttpStatus.OK );
    }

    @GetMapping("/tweets/{country}/{person}/find")
    public ResponseEntity<ResponseTwitter> getTweetsByCountry(@PathVariable(required = true)String country, @PathVariable(required = true)String person){
        ResponseTwitter response =  service.mostPopularPersonByCountry(country, person);
        return new ResponseEntity<>(response,  HttpStatus.OK );
    }
}
