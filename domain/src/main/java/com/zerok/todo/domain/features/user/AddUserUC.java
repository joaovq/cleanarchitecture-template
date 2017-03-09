package com.zerok.todo.domain.features.user;

import com.fernandocejas.arrow.optional.Optional;
import com.zerok.todo.domain.executor.PostExecutionThread;
import com.zerok.todo.domain.executor.ThreadExecutor;
import com.zerok.todo.domain.Params;
import com.zerok.todo.domain.interactor.UseCase;
import com.zerok.todo.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * adding an new {@link User}.
 */
public class AddUserUC extends UseCase {
  private final UserRepository userRepository;

  public static final String NAME = "newUser";
  public static final String PARAM_NEW_USER_KEY = "newUserId";

  @Inject
  public AddUserUC(UserRepository userRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.userRepository = userRepository;
  }

  @Override
  protected Observable buildUseCaseObservable(Optional<Params> params) {
    final User user = (User) params.get().getModel(PARAM_NEW_USER_KEY);
    return this.userRepository.add(user);
  }
}
