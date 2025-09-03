package com.example.neoappclientesapi.util;

import com.example.neoappclientesapi.dto.APIResponse;
import com.example.neoappclientesapi.dto.ErrorDTO;

import java.util.List;

public class ResponseUtils {
    private static final String ERROR = "ERROR";

    public static APIResponse<Void> buildErrorResponse(List<ErrorDTO> errors) {
        return APIResponse.<Void>builder()
                .status(ERROR)
                .errors(errors)
                .build();
    }

    public static APIResponse<Void> buildSingleError(String message) {
        return buildErrorResponse(List.of(new ErrorDTO(null, message)));
    }
}
