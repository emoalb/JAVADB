package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RacerServiceImpl implements RacerService {
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final static String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";

    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean racersAreImported() {
        //TODO: Implement me
        return this.racerRepository.count()>0;
    }

    @Override
    public String readRacersJsonFile() {
        try {
            String content = this.fileUtil.readFile(RACERS_JSON_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder sb = new StringBuilder();
        try {
            RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent,RacerImportDto[].class);
            for(RacerImportDto racerImportDto : racerImportDtos) {
                Town homeTown = this.townRepository.findByName(racerImportDto.getHomeTown()).orElse(null);
                if (!this.validationUtil.isValid(racerImportDtos)||(homeTown==null&&racerImportDto.getHomeTown()!=null)) {
                    sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue;
                }
                Racer racer = this.modelMapper.map(racerImportDto,Racer.class);
                racer.setHomeTown(homeTown);
                if(this.racerRepository.findByName(racer.getName()).orElse(null)==null){
                    this.racerRepository.saveAndFlush(racer);
                    sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,racer.getClass().getSimpleName(),racer.getName())).append(System.lineSeparator());
                }else{
                    sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return sb.toString();
    }

    @Override
    public String exportRacingCars() {
        //TODO: Implement me
        return null;
    }
}
