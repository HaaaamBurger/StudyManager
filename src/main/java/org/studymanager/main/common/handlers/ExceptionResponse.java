package org.studymanager.main.common.handlers;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
public class ExceptionResponse implements Serializable {
    @Serial
    private static final Long serialVersionUID = 982759290875202314L;

    private String title;

    private int status;

    private String detail;

    private String instance;
}
