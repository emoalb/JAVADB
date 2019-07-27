package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final RacerRepository racerRepository;

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/cars.json";

    @Autowired
    public CarServiceImpl(CarRepository carRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, RacerRepository racerRepository) {
        this.carRepository = carRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;

        this.racerRepository = racerRepository;
    }

    @Override
    public Boolean carsAreImported() {
        //TODO: Implement me
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() {
        try {
            String content = this.fileUtil.readFile(CARS_JSON_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder sb = new StringBuilder();
        try {
            CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);
            for (CarImportDto carImportDto : carImportDtos) {
                Racer racer = this.racerRepository.findByName(carImportDto.getRacerName()).orElse(null);
                if (!this.validationUtil.isValid(carImportDtos) || (racer == null && carImportDto.getRacerName() != null)) {
                    sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue;
                }
                Car car = this.modelMapper.map(carImportDto, Car.class);
                car.setRacer(racer);
                this.carRepository.saveAndFlush(car);
                String message = car.getBrand() + " " + car.getModel() + " @ " + car.getYearOfProduction();
                sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, car.getClass().getSimpleName(), message)).append(System.lineSeparator());

            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return sb.toString();
    }
}
