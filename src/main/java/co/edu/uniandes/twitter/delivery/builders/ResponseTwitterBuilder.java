package co.edu.uniandes.twitter.delivery.builders;

import co.edu.uniandes.twitter.delivery.models.ResponseTwitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ResponseTwitterBuilder {

    public static ResponseTwitter convertMapToResponseTwitter(Map<String, Double> data){
        ResponseTwitter response = new ResponseTwitter();
        List<String> labels = new ArrayList();
        List<Double> values = new ArrayList();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            labels.add(entry.getKey());
            values.add(entry.getValue());
        }
        Collections.reverse(labels);
        List<String> top10Label = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if( i < labels.size()){
                top10Label.add(labels.get(i));
            }
        }
        response.setLabels(top10Label);
        Collections.reverse(values);
        List<Double> top10Value = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(  i < values.size()){
                top10Value.add(values.get(i));
            }
        }
        response.setValues(top10Value);
        return response;
    }
}
