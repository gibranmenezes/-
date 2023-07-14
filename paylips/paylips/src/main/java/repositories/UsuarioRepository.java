package repositories;

import model.pais.Pais;
import model.usuario.Usuario;
import utils.Transacional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

public class UsuarioRepository extends GenericRepository<Usuario>{

    @Inject
    private EntityManager entityManager;

    @Inject
    public UsuarioRepository( EntityManager entityManager) {
        super(Usuario.class, entityManager);
    }


    public Optional<Usuario> findByName(String nome) {
        return buscaPorNome(nome);
    }

    @Transacional
    public Usuario save(Usuario usuario) {
        return salvar(usuario);
    }
}
