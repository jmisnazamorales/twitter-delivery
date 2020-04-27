package co.edu.uniandes.twitter.delivery.services;

import co.edu.uniandes.twitter.delivery.entities.Tweet;
import co.edu.uniandes.twitter.delivery.models.ResponseTwitter;

import java.util.List;
import java.util.Optional;

public interface TwitterService {

    Optional<Tweet> findById(String id);

    List<Tweet> getAll();

    ResponseTwitter countByCountry();

    ResponseTwitter mostPopularPersonByCountry(String country, String personId);

    ResponseTwitter mostPopularHashTagByCountry(String country);

    ResponseTwitter polarityByCountry(String country, String polarity, String twitt);
}
