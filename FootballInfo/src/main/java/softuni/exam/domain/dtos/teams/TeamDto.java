package softuni.exam.domain.dtos.teams;


import softuni.exam.domain.dtos.pictures.PictureRootDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamDto {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "picture")
    private TeamPictureDto teamPictureDto;

    public TeamDto() {
    }

    @NotNull
    @Size(min = 3, max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamPictureDto getTeamPictureDto() {
        return teamPictureDto;
    }

    public void setTeamPictureDto(TeamPictureDto teamPictureDto) {
        this.teamPictureDto = teamPictureDto;
    }
}
