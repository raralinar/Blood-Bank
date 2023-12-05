package com.bloodbank.bloodbank.controllers.bank;

import java.util.List;

public interface IController<T, K> {
    List<T> getAll();
    List<T> add();
    List<T> delete();
    List<T> delete(Long id);
}
