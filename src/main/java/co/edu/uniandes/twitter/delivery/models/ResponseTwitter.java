package co.edu.uniandes.twitter.delivery.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseTwitter {

    List<String> labels;
    List<Double> values;
}
