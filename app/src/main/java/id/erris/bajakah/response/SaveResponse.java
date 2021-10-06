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

    public boolean getStatus() {
        return result;
    }

    public void setStatus(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }
}
