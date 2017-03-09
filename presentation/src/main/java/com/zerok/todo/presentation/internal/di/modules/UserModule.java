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
package com.zerok.todo.presentation.internal.di.modules;

import com.zerok.todo.domain.executor.PostExecutionThread;
import com.zerok.todo.domain.executor.ThreadExecutor;
import com.zerok.todo.domain.features.user.GetUserDetailsUC;
import com.zerok.todo.domain.features.user.GetUserListUC;
import com.zerok.todo.domain.interactor.UseCase;
import com.zerok.todo.domain.repository.UserRepository;
import com.zerok.todo.presentation.internal.di.PerActivity;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class UserModule {

  public UserModule() {}

  @Provides @PerActivity
  @Named(GetUserListUC.NAME) UseCase provideGetUserListUseCase(
      GetUserListUC getUserList) {
    return getUserList;
  }

  @Provides @PerActivity @Named(GetUserDetailsUC.NAME) UseCase provideGetUserDetailsUseCase(
      UserRepository userRepository, ThreadExecutor threadExecutor,
      PostExecutionThread postExecutionThread) {
    return new GetUserDetailsUC(userRepository, threadExecutor, postExecutionThread);
  }
}
