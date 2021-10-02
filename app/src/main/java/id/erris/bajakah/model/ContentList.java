package id.erris.bajakah.model;

import androidx.annotation.NonNull;

public class ContentList {
    public String code;
    public String name;
    public String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentList(String code, String name, String url) {
        this.code = code;
        this.name = name;
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
