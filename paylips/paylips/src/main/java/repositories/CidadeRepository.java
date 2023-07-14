package repositories;

import model.cidade.Cidade;
import model.pais.Pais;
import model.pessoa.Pessoa;
import utils.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
