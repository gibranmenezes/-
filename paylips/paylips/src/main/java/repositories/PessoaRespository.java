package repositories;

import model.contato.Contato;
import model.pessoa.Pessoa;
import utils.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class PessoaRespository extends GenericRepository<Pessoa> {


    @Inject
    private EntityManager entityManager;

    @Inject
    public PessoaRespository( EntityManager entityManager) {
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
