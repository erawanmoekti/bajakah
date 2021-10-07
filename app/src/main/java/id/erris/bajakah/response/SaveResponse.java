package id.erris.bajakah.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveResponse {
    @SerializedName("result")
    @Expose
    private boolean result;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
