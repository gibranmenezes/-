package repositories;

import lombok.NoArgsConstructor;
import model.cargo.Cargo;
import model.pais.Pais;
import utils.Transacional;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Optional;

@NoArgsConstructor
public class CargoRepository extends GenericRepository<Cargo>{

    @Inject
    private EntityManager entityManager;

    @Inject
    public CargoRepository( EntityManager entityManager) {
            super(Cargo.class, entityManager);
    }

    public Optional<Cargo> findByName(String nome) {
        return buscaPorNome(nome);
    }

    @Transacional
    public Cargo save(Cargo cargo){
        return salvar(cargo);
    }
}
