package infrastructure.MemoryRepository;

import infrastructure.IRepository;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository<ID, T> implements IRepository<ID, T> {

    Map<ID, T> repository;

    public MemoryRepository() {
        this.repository = new HashMap<ID, T>();
    }

    public void add(ID id, T elem) {
        if (repository.put(id, elem) != null) throw new RuntimeException("Duplicate ID");
    }

    public void delete(ID id) {
        if (repository.remove(id) == null) throw new RuntimeException("ID doesn't exist");
    }

    public void modify(ID id, T elem) {
        if (repository.replace(id, elem) == null) throw new RuntimeException("ID doesn't exist");
    }

    public T findById(ID id) {
        return repository.get(id);
    }

    public Iterable<T> getAll() {
        return repository.values();
    }

    public String toString() {
        Iterable<T> Rit = getAll();
        StringBuilder s = new StringBuilder();

        for (T it: Rit) {
            s.append(it.toString());
        }

        return s.toString();
    }

}