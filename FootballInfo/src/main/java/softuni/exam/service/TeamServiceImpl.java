package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.teams.TeamDto;
import softuni.exam.domain.dtos.teams.TeamRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAM_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/xml/teams.xml";
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validationUtil;
    private final XmlParser xmlParser;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validationUtil, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importTeams() {
        StringBuilder sb = new StringBuilder();
        try {
            TeamRootDto teamRootDto = this.xmlParser.parseXml(TeamRootDto.class, TEAM_ENTRIES_XML_FILE_PATH);
            for (TeamDto teamDto : teamRootDto.getTeamDtos()) {
                Picture picture = this.pictureRepository.findByUrl(teamDto.getTeamPictureDto().getUrl()).orElse(null);
                if (!validationUtil.isValid(teamDto) || picture == null) {
                    sb.append(String.format(Constants.INCORRECT_DATA_MESSAGE, Team.class.getSimpleName().toLowerCase())).append(System.lineSeparator());
                    continue;
                }
                Team team = this.modelMapper.map(teamDto, Team.class);
                team.setPicture(picture);
                this.teamRepository.saveAndFlush(team);
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,Team.class.getSimpleName().toLowerCase(),team.getName())).append(System.lineSeparator());
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        //TODO Implement me
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() {
        try {
            String content = this.fileUtil.readFile(TEAM_ENTRIES_XML_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
