package com.example.codechallenge.storage;

import com.example.codechallenge.validation.Validation;

public interface StorageService {

    String loadAsResource(String filename) throws StorageException;

    void store(Validation validation) throws StorageException;
}