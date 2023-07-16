package repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public abstract class GenericRepository<T> {

    @Inject
    protected EntityManager entityManager;

    private Class<T> entityClass;

    @Inject
    public GenericRepository(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }

   protected T salvar(T entityClass){
        return entityManager.merge(entityClass);
    }

    protected T buscarPorId(Long id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
    protected List<T> buscaTodos() {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> rootEntry = cq.from(entityClass);
            CriteriaQuery<T> all = cq.select(rootEntry);
            TypedQuery<T> allQuery = entityManager.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected Optional<T> buscaPorNome(String nome) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            Root<T> root = cq.from(entityClass);
            cq.select(root).where(cb.equal(root.get("nome"), nome));
            TypedQuery<T> query = entityManager.createQuery(cq);
            return Optional.ofNullable(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
