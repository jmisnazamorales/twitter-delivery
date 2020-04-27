package co.edu.uniandes.twitter.delivery.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class User {

    @Field("id")
    private String id;
    @Field("id_str")
    private String id_str;
    @Field("name")
    private String name;
    @Field("location")
    private String location;
}
