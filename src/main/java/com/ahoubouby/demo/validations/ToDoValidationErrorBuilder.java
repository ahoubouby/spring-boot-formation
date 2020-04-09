package com.ahoubouby.demo.validations;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
/*
 * abdelwahed created on 06/04/2020
 * E-MAI: ahoubouby@gmail.com
 */

/*

"Validation
                failed." + errors.getErrorCount() + " error(s)"
 */
public class ToDoValidationErrorBuilder {

    public static ToDoValidationError fromBindingErrors(Errors errors) {
        ToDoValidationError error = new ToDoValidationError("validation field. " + errors.getErrorCount() + " error (s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
