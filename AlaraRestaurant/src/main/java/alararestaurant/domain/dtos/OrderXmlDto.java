package alararestaurant.domain.dtos;

import alararestaurant.domain.enums.OrderType;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderXmlDto {
    @XmlElement(name = "customer")
    private String customerName;
    @XmlElement(name="employee")
    private String employeeName;
    @XmlElement(name="type")
    private OrderType orderType;
    @XmlElement(name = "date-time")
    private String dateTime;
    @XmlElement(name = "items")
    private ItemsXmlDto itemXmlDto;

    public OrderXmlDto() {
    }
    @NotNull()
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    @NotNull()
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    @NotNull()
    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    @NotNull()
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ItemsXmlDto getItemXmlDto() {
        return itemXmlDto;
    }

    public void setItemXmlDto(ItemsXmlDto itemXmlDto) {
        this.itemXmlDto = itemXmlDto;
    }

}
