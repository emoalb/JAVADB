package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService{

    private final DistrictRepository districtRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final static String DISTRICT_JSON_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/files/districts.json";

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.districtRepository = districtRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count()>0;
    }

    @Override
    public String readDistrictsJsonFile() {
        try {
            String content = this.fileUtil.readFile(DISTRICT_JSON_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder sb = new StringBuilder();
        try {
            DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent,DistrictImportDto[].class);
            for(DistrictImportDto districtImportDto : districtImportDtos) {
                Town districtTown = this.townRepository.findByName(districtImportDto.getTownName()).orElse(null);
                if (!this.validationUtil.isValid(districtImportDtos)||(districtTown==null&&districtImportDto.getTownName()!=null)) {
                    sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                    continue;
                }
                District district = this.modelMapper.map(districtImportDto,District.class);
                district.setTown(districtTown);
                if(this.districtRepository.findByName(district.getName()).orElse(null)==null){
                    this.districtRepository.saveAndFlush(district);
                    sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,district.getClass().getSimpleName(),district.getName())).append(System.lineSeparator());
                }else{
                    sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return sb.toString();
    }
}
