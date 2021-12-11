package sia.tacocloud.tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacocloud.tacos.Taco;
import sia.tacocloud.tacos.TacoOrder;
import sia.tacocloud.tacos.User;
import sia.tacocloud.tacos.data.OrderProps;
import sia.tacocloud.tacos.data.OrderRepository;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final OrderRepository orderRepository;

    private OrderProps orderProps;

    @Autowired
    public  OrderController(OrderRepository orderRepository, OrderProps orderProps){
        this.orderRepository = orderRepository;
        this.orderProps = orderProps;
    }

    @GetMapping("/current")
    public String orderForm(Model model){
        return "orderForm";
    }

    public String orderForUser(@AuthenticationPrincipal User user, Model model){

        Pageable pageable = PageRequest.of(0, orderProps.getPageSize());
        model.addAttribute("orders",
                orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderlist";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
        if(errors.hasErrors()){
            return "orderForm";
        }
        order.setUser(user);

        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
