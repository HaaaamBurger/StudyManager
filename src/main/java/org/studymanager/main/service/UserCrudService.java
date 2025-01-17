package org.studymanager.main.service;

import java.util.List;

public interface UserCrudService<R, E> {
  /**
   * Retrieves a list of all users in the system.
   *
   * @return a list of R objects representing all users.
   */
  List<R> getAll();

  /**
   * Creates a new user in the system.
   *
   * @param e the request object containing the user data to be created.
   * @return the created R object.
   */
  R create(E e);

  /**
   * Deletes a user from the system by their unique identifier.
   *
   * @param id the unique identifier of the user to be deleted.
   */
  void delete(String id);

  /**
   * Updates the details of an existing user.
   *
   * @param id the unique identifier of the user to be updated.
   * @param e  the request object containing the updated user data.
   * @return the updated R object.
   */
  R update(String id, E e);

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param id the unique identifier of the user to be fetched.
   * @return the R object representing the user.
   */
  R getById(String id);
}
