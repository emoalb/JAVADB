package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.pictures.PictureDto;
import softuni.exam.domain.dtos.pictures.PictureRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {
    private static final String PICTURE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/xml/pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validationUtil;
    private final XmlParser xmlParser;
    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidatorUtil validationUtil, XmlParser xmlParser) {

        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public String importPictures() {
        StringBuilder sb = new StringBuilder();
        try {
            PictureRootDto pictureRootDto = this.xmlParser.parseXml(PictureRootDto.class, PICTURE_ENTRIES_XML_FILE_PATH);
       for(PictureDto pictureDto : pictureRootDto.getPictureDtos())
       {
           if(!validationUtil.isValid(pictureDto)){
               sb.append(String.format(Constants.INCORRECT_DATA_MESSAGE,Picture.class.getSimpleName())).append(System.lineSeparator());
               continue;
           }
           Picture picture = this.modelMapper.map(pictureDto,Picture.class);
           this.pictureRepository.saveAndFlush(picture);
           sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE_PICTURE,Picture.class.getSimpleName().toLowerCase(),picture.getUrl())).append(System.lineSeparator());
       }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() {
        try {
            String content = this.fileUtil.readFile(PICTURE_ENTRIES_XML_FILE_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}
