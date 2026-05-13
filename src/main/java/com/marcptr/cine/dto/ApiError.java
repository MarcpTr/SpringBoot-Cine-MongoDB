package com.marcptr.cine.dto;

public record ApiError<T> (
    String code,
    String message,
    Object details
){}
