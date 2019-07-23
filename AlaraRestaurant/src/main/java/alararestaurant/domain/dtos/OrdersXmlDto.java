package alararestaurant.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrdersXmlDto {
    @XmlElement(name="order")
    private List<OrderXmlDto> orderXmlDtos;

    public OrdersXmlDto() {
    }

    public List<OrderXmlDto> getOrderXmlDtos() {
        return orderXmlDtos;
    }

    public void setOrderXmlDtos(List<OrderXmlDto> orderXmlDtos) {
        this.orderXmlDtos = orderXmlDtos;
    }
}
