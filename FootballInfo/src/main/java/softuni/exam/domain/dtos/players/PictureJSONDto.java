package softuni.exam.domain.dtos.players;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PictureJSONDto implements Serializable {
    @Expose
    private String url;

    public PictureJSONDto() {
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
