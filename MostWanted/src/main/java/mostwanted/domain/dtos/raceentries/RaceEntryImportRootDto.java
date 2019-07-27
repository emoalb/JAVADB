package mostwanted.domain.dtos.raceentries;

import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.entities.RaceEntry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryImportRootDto {
    @XmlElement(name="race-entry")
    private List<RaceEntryImportDto> races;

    public RaceEntryImportRootDto() {

    }
    public List<RaceEntryImportDto> getRaces() {
        return races;
    }

    public void setRaces(List<RaceEntryImportDto> races) {
        this.races = races;
    }
}