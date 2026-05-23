package com.marcptr.cine.dto;

import com.marcptr.cine.model.enums.ErrorCode;

public record ApiError<T> (
    ErrorCode code,
    String message,
    T details
){}
