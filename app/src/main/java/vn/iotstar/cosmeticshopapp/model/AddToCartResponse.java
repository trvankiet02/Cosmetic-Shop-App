package vn.iotstar.cosmeticshopapp.model;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCartResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private CartItem body;
    private final static long serialVersionUID = 3202178625306740957L;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CartItem getBody() {
        return body;
    }

    public void setBody(CartItem body) {
        this.body = body;
    }

}
