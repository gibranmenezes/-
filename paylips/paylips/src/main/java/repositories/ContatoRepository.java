package repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import model.contato.Contato;
import utils.Transacional;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class ContatoRepository extends GenericRepository<Contato> {


    @Inject
    private EntityManager entityManager;
    @Inject
    public ContatoRepository(EntityManager entityManager) {
        super(Contato.class, entityManager);
    }

    public Contato findById(Long id) {
        return buscarPorId(id);
    }

    public Optional<Contato> findByEmailAndTelefone(String email, String telefone) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contato> cq = cb.createQuery(Contato.class);
        Root<Contato> root = cq.from(Contato.class);

        Predicate emailPredicate = cb.equal(root.get("email"), email);
        Predicate telefonePredicate = cb.equal(root.get("telefone"), telefone);

        cq.select(root).where(cb.and(emailPredicate, telefonePredicate));

        TypedQuery<Contato> query = entityManager.createQuery(cq);
        query.setMaxResults(1); // Define o máximo de resultados como 1
        List<Contato> contatos = query.getResultList();

        return contatos.isEmpty() ? Optional.empty() : Optional.of(contatos.get(0));
    }

    @Transacional
    public Contato save(Contato contato){
        return salvar(contato);

    }


}
