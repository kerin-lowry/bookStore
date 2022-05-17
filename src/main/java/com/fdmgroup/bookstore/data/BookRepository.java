package com.fdmgroup.bookstore.data;

public interface BookRepository<T, ID> extends Removable<T>, Persistable<T>, Searchable<T, ID> {

}
