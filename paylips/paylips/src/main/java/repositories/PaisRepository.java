package repositories;

import model.pais.Pais;
import utils.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

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
