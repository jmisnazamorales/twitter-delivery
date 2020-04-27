package co.edu.uniandes.twitter.delivery.repositories;

import co.edu.uniandes.twitter.delivery.entities.Tweet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TwitterRepository extends MongoRepository<Tweet, ObjectId> {

    Optional<Tweet> findById(ObjectId id);

    //List<Tweet> findByCreatedAtBetween(Date after, Date before);

    List<Tweet> findByEntitiesIsNotNull();

}
