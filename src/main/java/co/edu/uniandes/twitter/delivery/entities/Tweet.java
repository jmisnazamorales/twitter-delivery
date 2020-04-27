package co.edu.uniandes.twitter.delivery.entities;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@Document(collection = "tweets")
public class Tweet{

    @Id
    public ObjectId _id;

    @Field("created_at")
    private Date createdAt;

    @Field("id")
    private String identification;

    @Field("id_str")
    private String id_str;

    @Field("text")
    private String text;

    @Field("user")
    private User user;

    @Field("display_text_range")
    private String locationConvertedAudio;

    @Field("source")
    private String source;

    @Field("place")
    private String place;

    @Field("entities")
    private Selection entities;

    public Tweet(ObjectId _id) {
        this._id = _id;
    }
}
