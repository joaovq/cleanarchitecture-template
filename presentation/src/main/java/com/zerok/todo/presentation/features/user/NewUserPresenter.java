package com.zerok.todo.presentation.features.user;


import android.support.annotation.NonNull;

import com.zerok.todo.domain.Params;
import com.zerok.todo.domain.exception.DefaultErrorBundle;
import com.zerok.todo.domain.exception.ErrorBundle;
import com.zerok.todo.domain.features.user.AddUserUC;
import com.zerok.todo.domain.features.user.GetUserDetailsUC;
import com.zerok.todo.domain.features.user.User;
import com.zerok.todo.domain.interactor.DefaultObserver;
import com.zerok.todo.domain.interactor.UseCase;
import com.zerok.todo.presentation.exception.ErrorMessageFactory;
import com.zerok.todo.presentation.internal.di.PerActivity;
import com.zerok.todo.presentation.presenter.Presenter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class NewUserPresenter implements Presenter {

  private NewUserView newUserView;

  private final UseCase addUserUC;
  private final UserModelDataMapper userModelDataMapper;

  @Inject
  public NewUserPresenter(@Named(AddUserUC.NAME) UseCase addUserUC,
                              UserModelDataMapper userModelDataMapper) {
    this.addUserUC = addUserUC;
    this.userModelDataMapper = userModelDataMapper;
  }

  @Override public void resume() {}

  @Override public void pause() {}

  @Override public void destroy() {
    this.addUserUC.dispose();
    this.newUserView = null;
  }

  public void setView(@NonNull NewUserView view) {
    this.newUserView = view;
  }

  /**
   * Initializes the presenter by showing/hiding proper views
   * and retrieving user details.
   */
  public void initialize(int userId) {
    this.hideViewRetry();
    this.showViewLoading();
    this.getUserDetails(userId);
  }

  private void getUserDetails(int userId) {
    final Params params = Params.create();
    params.putInt(AddUserUC.PARAM_NEW_USER_KEY, userId);
    this.addUserUC.execute(new NewUserObserver(), params);
  }

  private void showViewLoading() {
    this.newUserView.showLoading();
  }

  private void hideViewLoading() {
    this.newUserView.hideLoading();
  }

  private void showViewRetry() {
    this.newUserView.showRetry();
  }

  private void hideViewRetry() {
    this.newUserView.hideRetry();
  }

  private void showErrorMessage(ErrorBundle errorBundle) {
    String errorMessage = ErrorMessageFactory.create(this.newUserView.context(),
            errorBundle.getException());
    this.newUserView.showError(errorMessage);
  }

  private void showSucessMessage(){
    this.newUserView.userAddedSucessfully();
  }

  private final class NewUserObserver extends DefaultObserver<User> {

    @Override public void onComplete() {
      NewUserPresenter.this.hideViewLoading();
    }

    @Override public void onError(Throwable e) {
      NewUserPresenter.this.hideViewLoading();
      NewUserPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
      NewUserPresenter.this.showViewRetry();
    }

    @Override public void onNext(User user) {
      NewUserPresenter.this.showSucessMessage();
    }
  }
}
