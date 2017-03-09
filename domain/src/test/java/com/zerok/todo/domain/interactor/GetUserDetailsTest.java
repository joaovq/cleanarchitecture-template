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
package com.zerok.todo.domain.interactor;

import com.fernandocejas.template.todo.domain.executor.PostExecutionThread;
import com.fernandocejas.template.todo.domain.executor.ThreadExecutor;
import com.zerok.todo.domain.Params;
import com.zerok.todo.domain.features.user.GetUserDetailsUC;
import com.zerok.todo.domain.repository.UserRepository;
import com.fernandocejas.arrow.optional.Optional;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetUserDetailsTest {

  private static final int USER_ID = 123;

  private GetUserDetailsUC getUserDetails;

  @Mock private UserRepository mockUserRepository;
  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;

  @Before
  public void setUp() {
    getUserDetails = new GetUserDetailsUC(mockUserRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetUserDetailsUseCaseObservableHappyCase() {
    final Params params = Params.create();
    params.putInt(GetUserDetailsUC.PARAM_USER_ID_KEY, USER_ID);

    getUserDetails.buildUseCaseObservable(Optional.of(params));

    verify(mockUserRepository).user(USER_ID);
    verifyNoMoreInteractions(mockUserRepository);
    verifyZeroInteractions(mockPostExecutionThread);
    verifyZeroInteractions(mockThreadExecutor);
  }

  @Test
  public void testShouldReturnEmptyObservableWhenNoParameters() {
    final Observable observable = getUserDetails.buildUseCaseObservable(Optional.<Params>absent());

    assertThat(observable).isEqualTo(Observable.empty());
    verifyZeroInteractions(mockUserRepository);
    verifyZeroInteractions(mockPostExecutionThread);
    verifyZeroInteractions(mockThreadExecutor);
  }

  @Test
  public void testShouldUseDefaultUserIdValueWhenNoUserIdParameter() {
    getUserDetails.buildUseCaseObservable(Optional.of(Params.create()));

    verify(mockUserRepository).user(GetUserDetailsUC.PARAM_USER_ID_DEFAULT_VALUE);
    verifyNoMoreInteractions(mockUserRepository);
    verifyZeroInteractions(mockPostExecutionThread);
    verifyZeroInteractions(mockThreadExecutor);
  }
}
