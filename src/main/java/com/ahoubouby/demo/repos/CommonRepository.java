package com.ahoubouby.demo.repos;
/*
 * abdelwahed created on 06/04/2020
 * E-MAI: ahoubouby@gmail.com
 */

import java.util.Collection;

public interface CommonRepository<ID, T> {
    public T save (T domain);
    public Iterable<T> save(Collection<T> domains);
    public void delete(T domain);
    public T findById(ID id);
    public Iterable<T> findAll();
}
