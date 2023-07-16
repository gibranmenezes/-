package repositories;

import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import model.cidade.Cidade;
import utils.Transacional;

import jakarta.inject.Inject;
import java.util.Optional;

@NoArgsConstructor
public class CidadeRepository extends GenericRepository<Cidade>{


    @Inject
    private EntityManager entityManager;

    @Inject
    public CidadeRepository( EntityManager entityManager) {
        super(Cidade.class, entityManager);
    }

    public Optional<Cidade> findByName(String nome) {
        return buscaPorNome(nome);
    }

    @Transacional
    public Cidade save(Cidade cidade){
        return salvar(cidade);
    }
}
