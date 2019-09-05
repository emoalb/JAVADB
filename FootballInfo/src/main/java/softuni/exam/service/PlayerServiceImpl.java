package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.players.PlayerJSONDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private static final String PLAYER_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/json/players.json";
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validationUtil;
    private final Gson gson;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validationUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public String importPlayers() {
        StringBuilder sb = new StringBuilder();
        try {
            String content = this.fileUtil.readFile(PLAYER_ENTRIES_XML_FILE_PATH);
            PlayerJSONDto[] playerJSONDtos = this.gson.fromJson(content, PlayerJSONDto[].class);
            for (PlayerJSONDto playerJSONDto : playerJSONDtos) {
                Team team = this.teamRepository.findByName(playerJSONDto.getTeam().getName()).orElse(null);
                Picture picture = this.pictureRepository.findByUrl(playerJSONDto.getPicture().getUrl()).orElse(null);
                if (team != null && !team.getPicture().getUrl().equals(playerJSONDto.getTeam().getPicture().getUrl())) {
                    team = null;
                }
                if (!validationUtil.isValid(playerJSONDto) || team == null || picture == null) {
                    sb.append(String.format(Constants.INCORRECT_DATA_MESSAGE, Player.class.getSimpleName().toLowerCase())).append(System.lineSeparator());
                    continue;
                }
                Player player = this.modelMapper.map(playerJSONDto, Player.class);
                player.setPicture(picture);
                player.setTeam(team);
                this.playerRepository.saveAndFlush(player);
                String fullName = playerJSONDto.getFirstName()+" "+ playerJSONDto.getLastName();
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,Player.class.getSimpleName().toLowerCase(),fullName)).append(System.lineSeparator());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() {
        try {
            String content = this.fileUtil.readFile(PLAYER_ENTRIES_XML_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        BigDecimal salary = new BigDecimal("10000");
     List<Player> playersWithSalary = this.playerRepository.findPlayersWithSalaryBiggerThan(salary);
     playersWithSalary.forEach(player -> {
         sb.append("Player name: ").append(player.getFirstName()).append(" ").append(player.getLastName()).append(System.lineSeparator());
         sb.append("Number: ").append(player.getNumber()).append(System.lineSeparator());
         sb.append("Salary: ").append(player.getSalary()).append(System.lineSeparator());
         sb.append("Team: ").append(player.getTeam().getName()).append(System.lineSeparator());
sb.append(System.lineSeparator());
     });
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
       String teamName = "North Hub";
       Team team = this.teamRepository.findByName(teamName).orElse(null);
        List<Player> playersInATeam = this.playerRepository.findAllByTeamOrderById(team);
        sb.append("Name: ").append(team.getName()).append(System.lineSeparator());
        playersInATeam.forEach(player -> {
            sb.append("Player name: ").append(player.getFirstName()).append(" ").append(player.getLastName()).append(" - ").append(player.getPosition()).append(System.lineSeparator());
            sb.append("Number: ").append(player.getNumber()).append(System.lineSeparator());
        });
        return sb.toString();
    }
}
