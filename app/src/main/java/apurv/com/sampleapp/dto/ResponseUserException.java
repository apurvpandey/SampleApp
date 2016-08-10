package apurv.com.sampleapp.dto;

import org.json.JSONException;
import org.json.JSONObject;

import apurv.com.sampleapp.interfaces.BaseModel;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class ResponseUserException extends Exception implements BaseModel {
    private int httpErrorCode = -1;
    private int appErrorCode = -1;
    private String messageTitle = "";
    private String messageDetail = "";

    public ResponseUserException() {
    }

    public ResponseUserException(String messageDetail) {
        this.messageDetail = messageDetail;
    }


    @Override
    public JSONObject toJSON() {
        return null;
    }

    @Override
    public void fromJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jMain = jsonObject.getJSONObject("app42Fault");
            httpErrorCode = jMain.getInt("httpErrorCode");
            appErrorCode = jMain.getInt("appErrorCode");
            messageTitle = jMain.getString("message");
            messageDetail = jMain.getString("details");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public int getAppErrorCode() {
        return appErrorCode;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public String getMessageDetail() {
        return messageDetail;
    }
}
