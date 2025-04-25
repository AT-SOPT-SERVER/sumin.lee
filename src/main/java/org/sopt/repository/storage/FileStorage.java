package org.sopt.repository.storage;

import java.util.List;

public interface  FileStorage<T> {
    void save(List<T> data);
    List<T> load();
}
