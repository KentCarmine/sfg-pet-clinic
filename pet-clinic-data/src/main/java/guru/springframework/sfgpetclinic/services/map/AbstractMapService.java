package guru.springframework.sfgpetclinic.services.map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapService<T, Y> {

//    protected Map<Y, T> map = new HashMap<>();
    protected Map<Y, T> map;

    public AbstractMapService() {
        map = new HashMap<>();
    }

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public T findById(Y id) {
        return map.get(id);
    }

    public T save(Y id, T object){
        map.put(id, object);
        return object;
    }

    public void deleteById(Y id){
        map.remove(id);
    }

    public void delete(T object){
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }
}
