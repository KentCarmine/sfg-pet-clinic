package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, Y extends Long> {

//    protected Map<Y, T> map = new HashMap<>();
    protected Map<Long, T> map;

    public AbstractMapService() {
        map = new HashMap<>();
    }

    public Set<T> findAll() {
        return new HashSet<>(map.values());
    }

    public T findById(Y id) {
        return map.get(id);
    }

    public T save(T object){
        if (object != null) {
            if (object.getId() == null) {
                object.setId(getNextId());
            }

            map.put(object.getId(), object);
        } else {
            throw new IllegalArgumentException("Object cannot be null");
        }

        return object;
    }

    public void deleteById(Y id){
        map.remove(id);
    }

    public void delete(T object){
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }

    private Long getNextId() {
//        Long nextId = null;
//        try {
//            nextId = Collections.max(map.keySet()) + 1;
//        } catch (NoSuchElementException e) {
//            nextId = 1L;
//        }
        
        Long nextId = null;
        if (map.keySet().isEmpty()) {
            nextId = 1L;
        } else {
            nextId = nextId = Collections.max(map.keySet()) + 1;
        }

        return nextId;
    }
}
