package com.otus.sessionManager;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);

}
