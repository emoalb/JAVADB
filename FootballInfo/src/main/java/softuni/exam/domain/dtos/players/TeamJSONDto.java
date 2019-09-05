package softuni.exam.domain.dtos.players;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TeamJSONDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private PictureJSONDto picture;

    public TeamJSONDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public PictureJSONDto getPicture() {
        return picture;
    }

    public void setPicture(PictureJSONDto picture) {
        this.picture = picture;
    }
}
