package softuni.exam.domain.dtos.teams;

import softuni.exam.domain.entities.Team;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamRootDto {
    @XmlElement(name="team")
    private List<TeamDto> teamDtos;

    public TeamRootDto() {
    }

    public List<TeamDto> getTeamDtos() {
        return teamDtos;
    }

    public void setTeamDtos(List<TeamDto> teamDtos) {
        this.teamDtos = teamDtos;
    }
}
