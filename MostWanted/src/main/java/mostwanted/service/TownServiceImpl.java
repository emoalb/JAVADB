package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        //TODO: Implement me
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() {
        try {
            String content = this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder sb = new StringBuilder();
        try {
            TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent,TownImportDto[].class);
          for(TownImportDto townImportDto : townImportDtos) {
              if (!this.validationUtil.isValid(townImportDtos)) {
                  sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                  continue;
              }
              Town town = this.modelMapper.map(townImportDto,Town.class);
              if(this.townRepository.findByName(town.getName()).orElse(null)==null){
                  this.townRepository.saveAndFlush(town);
                  sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,town.getClass().getSimpleName(),town.getName())).append(System.lineSeparator());
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
    public String exportRacingTowns() {
        //TODO: Implement me
        return null;
    }
}
