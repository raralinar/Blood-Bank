package com.bloodbank.bloodbank.service.bank;

import java.util.List;

public interface IService<T> {
    List<T> getAll();
}
