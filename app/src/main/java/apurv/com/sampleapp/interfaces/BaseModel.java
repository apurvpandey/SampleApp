package apurv.com.sampleapp.interfaces;

import org.json.JSONObject;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public interface BaseModel {
    /**
     * This method is useful to request with api.
     * It return the JSONObject which is forward to api for the POST request.
     *
     * @return JSONObject
     */

    public JSONObject toJSON();

    /**
     * This method is useful after getting the response from the server.
     * It is working for the GET and POST both.
     *
     * @param jsonString It take the jsonString and assign to each variable of this class.
     */

    public void fromJSON(String jsonString);
}
