package vn.iotstar.cosmeticshopapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SliderData implements Serializable {

    // image url is used to
    // store the url of image
    private String imgUrl;

    // Constructor method.
    public SliderData(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

