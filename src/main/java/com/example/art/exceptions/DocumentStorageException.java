package com.example.art.exceptions;

import com.example.art.utils.MessageUtils;

public class DocumentStorageException extends Throwable {

    private String fileName;

    public DocumentStorageException(String fileName) {
        super(MessageUtils.failureFileStorage(fileName));
        this.fileName = fileName;
    }
}
