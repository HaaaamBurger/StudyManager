package org.studymanager.main.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class ExceptionResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 982759290875202314L;

    private String title;

    private int status;

    private String detail;

    private String instance;
}
