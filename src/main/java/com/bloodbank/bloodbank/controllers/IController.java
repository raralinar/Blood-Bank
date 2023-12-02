package com.bloodbank.bloodbank.controllers;

import java.util.List;

public interface IController<T> {
    List<T> getAll();
    List<T> add();
    List<T> delete();
    List<T> delete(Long id);
}
