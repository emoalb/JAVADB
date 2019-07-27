package alararestaurant.service;

import alararestaurant.domain.dtos.EmployeeFromJSONDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String ROOT = System.getProperty("user.dir");
    private static final String EMPLOYEES_IMPORT_PATH = "\\src\\main\\resources\\files\\employees.json";

    private final FileUtil fileUtil;
    private final EmployeeRepository employeeRepository;
    private final ValidationUtil validationUtil;
    private final PositionRepository positionRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(FileUtil fileUtil, EmployeeRepository employeeRepository, ValidationUtil validationUtil, PositionRepository positionRepository, Gson gson, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.employeeRepository = employeeRepository;
        this.validationUtil = validationUtil;
        this.positionRepository = positionRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean employeesAreImported() {
        // TODO : Implement me

        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() {
        try {
            String content = this.fileUtil.fileContent(ROOT + EMPLOYEES_IMPORT_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder sb = new StringBuilder();
        try {
            EmployeeFromJSONDto[] employeeFromJSONDtos = this.gson.fromJson(employees, EmployeeFromJSONDto[].class);
            for (EmployeeFromJSONDto employeeFromJSONDto : employeeFromJSONDtos) {
                if (!this.validationUtil.isValid(employeeFromJSONDto)) {
                    sb.append("Invalid data format.").append(System.lineSeparator());
                    continue;
                }
                String positionName = employeeFromJSONDto.getPosition();
                Position position = this.positionRepository.findPositionByName(positionName).orElse(null);
                if (position == null) {
                    position = new Position();
                    position.setName(positionName);
                    this.positionRepository.saveAndFlush(position);
                }
                Employee employee = this.modelMapper.map(employeeFromJSONDto, Employee.class);
                employee.setPosition(position);
                this.employeeRepository.saveAndFlush(employee);
                sb.append("Record ").append(employee.getName()).append(" successfully imported.").append(System.lineSeparator());
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return sb.toString();
    }
}
