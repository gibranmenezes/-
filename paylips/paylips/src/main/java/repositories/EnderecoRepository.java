package repositories;

import lombok.NoArgsConstructor;
import model.cidade.Cidade;
import model.contato.Contato;
import model.endereco.Endereco;
import utils.Transacional;

import jakarta.persistence.criteria.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class EnderecoRepository extends GenericRepository<Endereco> {

    @Inject
    private EntityManager entityManager;

    @Inject
    public EnderecoRepository( EntityManager entityManager) {
        super(Endereco.class, entityManager);
    }

    @Transacional
    public Endereco save(Endereco endereco) {
        return salvar(endereco);
    }

    public Optional<Endereco> findByLogradouroECep(String nomeRua, String cep) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Endereco> cq = cb.createQuery(Endereco.class);
        Root<Endereco> root = cq.from(Endereco.class);

        Predicate nomeRuaPredicate = cb.equal(root.get("nomeRua"), nomeRua);
        Predicate cepPredicate = cb.equal(root.get("cep"), cep);
        cq.select(root).where(cb.and(nomeRuaPredicate, cepPredicate));

        TypedQuery<Endereco> query = entityManager.createQuery(cq);
        query.setMaxResults(1);
        List<Endereco> enderecos = query.getResultList();

        return enderecos.isEmpty() ? Optional.empty() : Optional.of(enderecos.get(0));
    }
}
