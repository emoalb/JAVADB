package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemsXmlDto {
    @XmlElement(name = "item")
    private List<ItemXmlDto> itemXmlDtos;

    public ItemsXmlDto() {
    }

    public List<ItemXmlDto> getItemXmlDtos() {
        return itemXmlDtos;
    }

    public void setItemXmlDtos(List<ItemXmlDto> itemXmlDtos) {
        this.itemXmlDtos = itemXmlDtos;
    }
}
