package repositories;

import lombok.NoArgsConstructor;
import model.pais.Pais;
import utils.Transacional;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Optional;

@NoArgsConstructor
public class PaisRepository extends GenericRepository<Pais>{

    @Inject
    private EntityManager entityManager;

    @Inject
    public PaisRepository( EntityManager entityManager) {
        super(Pais.class, entityManager);
    }

    public Optional<Pais> findByName(String nome) {
        return buscaPorNome(nome);
    }

    @Transacional
    public Pais save(Pais pais){
        return salvar(pais);
    }
}
