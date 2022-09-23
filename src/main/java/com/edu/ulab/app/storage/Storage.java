package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.AbstractEntity;

import java.util.Map;
import java.util.TreeMap;

public abstract class Storage<E extends AbstractEntity> {

    //todo создать хранилище в котором будут содержаться данные +
    // сделать абстракции через которые можно будет производить операции с хранилищем +
    // продумать логику поиска и сохранения +
    // продумать возможные ошибки +
    // учесть, что при сохранеии юзера или книги, должен генерироваться идентификатор +
    // продумать что у узера может быть много книг и нужно создать эту связь +
    // так же учесть, что методы хранилища принимают друго тип данных - учесть это в абстракции +

    private final Map<Long, E> entityMap = new TreeMap<>();
    private long counter = 0;

    public E save(E entity) {
        long id = generateId();
        entity.setId(id);
        entityMap.put(id, entity);
        return get(id);
    }

    public E update(Long id, E entity) {
        if (get(id) == null) {
            return null;
        }
        entity.setId(id);
        entityMap.put(id, entity);
        return get(id);
    }

    public E get(Long id) {
        return entityMap.get(id);
    }

    public void delete(Long id) {
        entityMap.remove(id);
    }

    private Long generateId() {
        return counter += 1;
    }
}
