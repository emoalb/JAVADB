package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final RaceEntryRepository raceEntryRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final static String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CarRepository carRepository, RacerRepository racerRepository) {
        this.raceEntryRepository = raceEntryRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
    }

    @Override
    public Boolean raceEntriesAreImported() {

        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() {
        try {
            String content = this.fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importRaceEntries() {
        StringBuilder sb = new StringBuilder();

        try {
            RaceEntryImportRootDto raceEntryImportRootDto = this.xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);
            for (RaceEntryImportDto raceEntryImportDto : raceEntryImportRootDto.getRaces()) {
                Car car = this.carRepository.findById(raceEntryImportDto.getCarId()).orElse(null);
                Racer racer = this.racerRepository.findByName(raceEntryImportDto.getRacerName()).orElse(null);
                if (!validationUtil.isValid(raceEntryImportDto) || (car == null && raceEntryImportDto.getCarId() != null) || (racer == null && raceEntryImportDto.getRacerName() != null)) {
                    sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue;
                }
                RaceEntry raceEntry = this.modelMapper.map(raceEntryImportDto, RaceEntry.class);
                raceEntry.setRacer(racer);
                raceEntry.setCar(car);
                raceEntry.setRace(null);
                this.raceEntryRepository.saveAndFlush(raceEntry);
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, raceEntry.getClass().getSimpleName(), raceEntry.getId())).append(System.lineSeparator());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        return sb.toString();
    }
}
