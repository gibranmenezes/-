package repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import model.pessoa.Pessoa;
import utils.Transacional;

import java.util.List;

@NoArgsConstructor
public class PessoaRepository extends GenericRepository<Pessoa> {
    @Inject
    private EntityManager entityManager;
    @Inject
    public PessoaRepository(EntityManager entityManager) {
        super(Pessoa.class, entityManager);
    }

    public List<Pessoa> findAll(){
        List<Pessoa> pessoas =  buscaTodos();
        return  pessoas;
    }

    @Transacional
    public Pessoa save(Pessoa pessoa){
        return salvar(pessoa);
    }

}
