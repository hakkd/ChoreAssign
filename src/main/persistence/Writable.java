package persistence;

import org.json.JSONObject;

//code for persistence implementation based on CPSC210 JsonSerializationDemo

public interface Writable {
    //returns this as JSON object
    JSONObject toJson();
}
