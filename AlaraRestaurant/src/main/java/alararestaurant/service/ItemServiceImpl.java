package alararestaurant.service;

import alararestaurant.domain.dtos.ItemFromJSONDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
    private static final String ROOT = System.getProperty("user.dir");
    private static final String ITEMS_IMPORT_PATH = "\\src\\main\\resources\\files\\items.json";

    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final Gson gson;

    @Autowired
    public ItemServiceImpl(FileUtil fileUtil, ItemRepository itemRepository, ValidationUtil validationUtil, ModelMapper modelMapper, CategoryRepository categoryRepository, Gson gson) {
        this.fileUtil = fileUtil;
        this.itemRepository = itemRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
    }

    @Override
    public Boolean itemsAreImported() {

        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() {
        try {
            String content = this.fileUtil.fileContent(ROOT + ITEMS_IMPORT_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importItems(String items) {
      StringBuilder sb = new StringBuilder();
      try {
          ItemFromJSONDto[] itemFromJSONDtos = this.gson.fromJson(items,ItemFromJSONDto[].class);
          for(ItemFromJSONDto itemFromJSONDto : itemFromJSONDtos){
              if(!this.validationUtil.isValid(itemFromJSONDto)){
                  sb.append("Invalid data format.").append(System.lineSeparator());
                  continue;
              }
              String categoryName = itemFromJSONDto.getCategory();
              Category category = this.categoryRepository.findCategoryByName(categoryName).orElse(null);
              if(category==null){
                  category = new Category();
                  category.setName(categoryName);
                  this.categoryRepository.saveAndFlush(category);
              }
              Item item  = this.modelMapper.map(itemFromJSONDto,Item.class);
              item.setCategory(category);
              if(this.itemRepository.getItemByName(item.getName()).orElse(null)==null) {
                  this.itemRepository.saveAndFlush(item);
                  sb.append("Record ").append(item.getName()).append(" successfully imported.").append(System.lineSeparator());
              }
          }

      }catch (Exception e){
          return e.getMessage();
      }
        return sb.toString();
    }
}
