package sia.tacocloud.tacos.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import sia.tacocloud.tacos.Taco;

import java.util.Optional;

public interface TacoRepository extends PagingAndSortingRepository<Taco, String> {
    Optional<Taco> findById(Long id);
}
