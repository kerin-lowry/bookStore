package com.fdmgroup.bookstore.data;

public interface OrderRepository<T, ID> extends Persistable<T>, Searchable<T, ID> {

}
