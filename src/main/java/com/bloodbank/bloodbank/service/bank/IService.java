package com.bloodbank.bloodbank.service.bank;

import java.util.List;

public interface IService<T, K> {
    List<T> getAll();
    void delete(Long id);
    void update(K dto);
    void add(K dto);
    T findById(Long id);
}
