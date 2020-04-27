package co.edu.uniandes.twitter.delivery.services.impl;


import co.edu.uniandes.twitter.delivery.builders.ResponseTwitterBuilder;
import co.edu.uniandes.twitter.delivery.entities.Tweet;
import co.edu.uniandes.twitter.delivery.map.order.MapUtilOrder;
import co.edu.uniandes.twitter.delivery.models.ResponseTwitter;
import co.edu.uniandes.twitter.delivery.repositories.TwitterRepository;
import co.edu.uniandes.twitter.delivery.services.TwitterService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TwitterServiceImpl implements TwitterService {

    private final static String COLLECTION_NAME = "tweets";

    private final static String REDUCE_FUNCTION = "function(key,values){ return values.length;}";

    private final TwitterRepository repository;

    private final MongoTemplate mongoTemplate;

    public TwitterServiceImpl(TwitterRepository repository, MongoTemplate mongoTemplate){
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Tweet> findById(String id) {
        log.info("method findById params {}", id);
        try {
            return repository.findById(new ObjectId(id));
        }catch (Exception e ){
            log.error("Error finding tuple {}", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Tweet> getAll() {
        log.info("method getAll");
        return repository.findByEntitiesIsNotNull();
    }


    @Override
    public ResponseTwitter countByCountry() {
        log.info("method countByCountry");
        String map = "function () { var name = this.place.country; emit(name.toString(), 1);}";
        Query filter = Query.query(Criteria.where("_id").exists(true));
        MapReduceResults<Document> results =
                mongoTemplate.mapReduce(filter, COLLECTION_NAME ,  map, REDUCE_FUNCTION, Document.class);
        return readResponse(results);
    }


    @Override
    public ResponseTwitter mostPopularHashTagByCountry(String country){
        log.info("method mostPopularPersonByCountry params {}", country);
        String map = "function(){ for ( var i=0; i<this.entities.hashtags.length; i++ ){ emit( this.entities.hashtags[i].text , 1 ); } }";
        Query filter = Query.query(Criteria.where("place.country").is(country));
        MapReduceResults<Document> results =
                mongoTemplate.mapReduce(filter, COLLECTION_NAME,  map, REDUCE_FUNCTION, Document.class);
        return readResponse(results);
    }

    @Override
    public ResponseTwitter polarityByCountry(String country, String polarity, String twitt) {
        log.info("method polarityByCountry params {} , {}", country, polarity);
        String map = "function(){ for ( var i=0; i<this.entities.hashtags.length; i++ ){ emit( this.entities.hashtags[i].text , 1 ); } }";
        Query filter = Query.query(Criteria.where("place.country").is(country).andOperator(Criteria.where("place.country").is(polarity)));
        MapReduceResults<Document> results =
                mongoTemplate.mapReduce(filter, COLLECTION_NAME,  map, REDUCE_FUNCTION, Document.class);
        return readResponse(results);
    }

    @Override
    public ResponseTwitter mostPopularPersonByCountry(String country, String personId) {
        log.info("method mostPopularPersonByCountry params {}, {}", country, personId);
        String map = "function () { var name = this.user.name; emit(name.toString(), 1);}";
        Query filter = Query.query(Criteria.where("place.country").is(country).andOperator(Criteria.where("user.id_str").is(personId)));
        MapReduceResults<Document> results =
                mongoTemplate.mapReduce(filter, COLLECTION_NAME ,  map, REDUCE_FUNCTION, Document.class);
        return readResponse(results);
    }

    private ResponseTwitter readResponse(MapReduceResults<Document> results){
        Map<String, Double> map= new HashMap<>();
        for (Document counts : results){
            if(counts.get("_id") != null && counts.get("value") != null){
                String text = String.valueOf(counts.get("_id"));
                Double value = Double.parseDouble(counts.get("value").toString());
                map.put(text, value);
            }
        }
        return ResponseTwitterBuilder.convertMapToResponseTwitter(MapUtilOrder.sortByValue(map));
    }
}
