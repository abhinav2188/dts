package com.example.art.exceptions;

import com.example.art.utils.MessageUtils;

public class DocumentStorageException extends Throwable {

    public DocumentStorageException() {
        super(MessageUtils.failureFileStorage());
    }
}
