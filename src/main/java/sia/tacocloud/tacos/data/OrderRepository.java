package sia.tacocloud.tacos.data;

import sia.tacocloud.tacos.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder order);
}
