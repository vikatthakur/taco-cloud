package sia.tacocloud.tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
import sia.tacocloud.tacos.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    @Autowired
    public  OrderController(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm(Model model){
        if(!model.containsAttribute("order")) {
            log.info("model attribute order is not present");
            model.addAttribute("order", new TacoOrder());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if(errors.hasErrors()){
            return "orderForm";
        }

        log.info("Order submitted: " + order);
        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
