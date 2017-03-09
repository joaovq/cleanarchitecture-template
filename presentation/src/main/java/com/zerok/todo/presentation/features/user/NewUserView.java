package com.zerok.todo.presentation.features.user;

import com.zerok.todo.presentation.view.LoadDataView;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view for adding a new user.
 */
public interface NewUserView extends LoadDataView {
  /**
   * Show a message informing that the user's been saved sucessfully
   */
  void userAddedSucessfully();
}
