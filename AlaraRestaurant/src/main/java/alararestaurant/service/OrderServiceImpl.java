package alararestaurant.service;

import alararestaurant.domain.dtos.*;
import alararestaurant.domain.entities.*;
import alararestaurant.repository.*;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private static final String ROOT = System.getProperty("user.dir");
    private static final String ORDERS_IMPORT_PATH = "\\src\\main\\resources\\files\\orders.xml";
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final OrderItemRepository orderItemRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, OrderItemRepository orderItemRepository, FileUtil fileUtil, ValidationUtil validationUtil, EmployeeRepository employeeRepository, ItemRepository itemRepository, PositionRepository positionRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.orderItemRepository = orderItemRepository;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public Boolean ordersAreImported() {
        // TODO : Implement me
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() {
        try {
            String content = this.fileUtil.fileContent(ROOT + ORDERS_IMPORT_PATH);
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OrdersXmlDto.class);
            File file = new File(ROOT + ORDERS_IMPORT_PATH);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            OrdersXmlDto ordersXmlDto = (OrdersXmlDto) unmarshaller.unmarshal(file);

            for (OrderXmlDto orderXmlDto : ordersXmlDto.getOrderXmlDtos()) {
                if (!validationUtil.isValid(orderXmlDto)) {
                    sb.append("Invalid data format.").append(System.lineSeparator());
                    continue;
                }
                Employee currentEmployee = this.employeeRepository.getEmployeeByName(orderXmlDto.getEmployeeName()).orElse(null);
                if (currentEmployee == null) {
                    continue;
                }

                List<ItemXmlDto> itemXmlDtos = orderXmlDto.getItemXmlDto().getItemXmlDtos();
                boolean hasNull = false;
                //
                //    this.modelMapper.addMappings(orderXmlDtoOrderPropertyMap);
                Order currentOrder = new Order();//this.modelMapper.map(orderXmlDto,Order.class);//
                currentOrder.setType(orderXmlDto.getOrderType());
                currentOrder.setCustomer(orderXmlDto.getCustomerName());
                currentOrder.setDateTime(LocalDateTime.parse(orderXmlDto.getDateTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                currentOrder.setEmployee(currentEmployee);
                List<OrderItem> orderItemList = new ArrayList<>();
                for (ItemXmlDto itemXmlDto : itemXmlDtos) {
                    Item item = this.itemRepository.getItemByName(itemXmlDto.getName()).orElse(null);
                    if (item == null) {
                        hasNull = true;
                        break;
                    }
                    OrderItem currentOrderItem = new OrderItem();
                    currentOrderItem.setItem(item);
                    currentOrderItem.setOrder(currentOrder);
                    currentOrderItem.setQuantity(itemXmlDto.getQuantity());
                    orderItemList.add(currentOrderItem);
                }
                if (!hasNull) {
                    this.orderRepository.saveAndFlush(currentOrder);
                    orderItemList.forEach(this.orderItemRepository::saveAndFlush);
                    sb.append("Order for ").append(currentOrder.getCustomer()).append(" on").append(currentOrder.getDateTime().toString()).append(" added").append(System.lineSeparator());
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return sb.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        Position position = this.positionRepository.findPositionByName("Burger Flipper").orElse(null);
        List<Order> orders = this.orderRepository.findAllByEmployee_PositionOrderByEmployeeId(position);
        List<Order> otherOrders = this.orderRepository.findAllByEmployee_Position_NameOrderByEmployee_Name(position.getName());
        return null;
    }
}
