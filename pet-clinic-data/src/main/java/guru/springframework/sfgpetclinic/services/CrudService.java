package guru.springframework.sfgpetclinic.services;

import java.util.Set;

public interface CrudService<T, Y> {
    T findById(Y id);

    T save(T object);

    Set<T> findAll();

    void delete(T object);

    void deleteById(Y id);
}
