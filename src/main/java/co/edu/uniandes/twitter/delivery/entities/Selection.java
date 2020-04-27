package co.edu.uniandes.twitter.delivery.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Getter
@Setter
public class Selection {

    @Field("hashtags")
    private ArrayList<HashTags> hashTags;
}
