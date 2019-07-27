package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.races.EntryImportDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RaceServiceImpl implements RaceService {
    private final RaceRepository raceRepository;
    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;


    private final static String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, DistrictRepository districtRepository, RaceEntryRepository raceEntryRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.raceRepository = raceRepository;
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean racesAreImported() {

        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        try {
            String content = this.fileUtil.readFile(RACES_XML_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importRaces() {
        StringBuilder sb = new StringBuilder();

        try {
            RaceImportRootDto raceImportRootDto = this.xmlParser.parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);
            for (RaceImportDto raceImportDto : raceImportRootDto.getRaceImportDtos()) {
                if (!validationUtil.isValid(raceImportDto)) {
                    sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue;
                }
                Race race = this.modelMapper.map(raceImportDto, Race.class);
                District district = this.districtRepository.findByName(raceImportDto.getDistrictName()).orElse(null);
                race.setDistrict(district);
                this.raceRepository.saveAndFlush(race);
                for(EntryImportDto entryImportDto: raceImportDto.getEntryImportRootDto().getEntryImportDtos()){
                    RaceEntry raceEntry = this.raceEntryRepository.findById(entryImportDto.getId()).orElse(null);
                    if(raceEntry!=null){
                        raceEntry.setRace(race);
                        this.raceEntryRepository.saveAndFlush(raceEntry);
                    }
                }
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, race.getClass().getSimpleName(), race.getId())).append(System.lineSeparator());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return sb.toString();
    }
}