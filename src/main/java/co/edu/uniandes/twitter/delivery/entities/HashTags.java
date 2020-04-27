package co.edu.uniandes.twitter.delivery.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class HashTags {

    @Field("text")
    private String text;
}
