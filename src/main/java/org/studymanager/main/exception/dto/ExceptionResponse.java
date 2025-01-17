package org.studymanager.main.exception.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * A class representing the response body for exception.
 *
 * <p>This class encapsulates the details of an exception response, providing
 * structured information such as the title, status, detail, and instance.
 * It implements {@link Serializable} to allow the object to be serialized.
 *
 * @author Melnyk Oleksandr
 * @version 1.0
 */
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
