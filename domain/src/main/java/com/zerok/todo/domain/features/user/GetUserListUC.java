/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zerok.todo.domain.features.user;

import com.zerok.todo.domain.executor.PostExecutionThread;
import com.zerok.todo.domain.executor.ThreadExecutor;
import com.zerok.todo.domain.Params;
import com.zerok.todo.domain.interactor.UseCase;
import com.zerok.todo.domain.repository.UserRepository;
import com.fernandocejas.arrow.optional.Optional;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link User}.
 */
public class GetUserListUC extends UseCase {

  public static final String NAME = "userList";

  private final UserRepository userRepository;

  @Inject
  GetUserListUC(UserRepository userRepository, ThreadExecutor threadExecutor,
                PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.userRepository = userRepository;
  }

  @Override public Observable buildUseCaseObservable(Optional<Params> params) {
    return this.userRepository.users();
  }
}
