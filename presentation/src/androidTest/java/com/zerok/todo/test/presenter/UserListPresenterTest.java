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
package com.zerok.todo.test.presenter;

import android.content.Context;
import com.zerok.todo.domain.features.user.GetUserListUC;
import com.zerok.todo.domain.Params;
import com.zerok.todo.presentation.features.user.UserModelDataMapper;
import com.zerok.todo.presentation.features.user.UserListPresenter;
import com.zerok.todo.presentation.features.user.UserListView;
import io.reactivex.observers.DisposableObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserListPresenterTest {

  private UserListPresenter userListPresenter;

  @Mock private Context mockContext;
  @Mock private UserListView mockUserListView;
  @Mock private GetUserListUC mockGetUserList;
  @Mock private UserModelDataMapper mockUserModelDataMapper;

  @Before
  public void setUp() {
    userListPresenter = new UserListPresenter(mockGetUserList, mockUserModelDataMapper);
    userListPresenter.setView(mockUserListView);
  }

  @Test
  public void testUserListPresenterInitialize() {
    given(mockUserListView.context()).willReturn(mockContext);

    userListPresenter.initialize();

    verify(mockUserListView).hideRetry();
    verify(mockUserListView).showLoading();
    verify(mockGetUserList).execute(any(DisposableObserver.class), any(Params.class));
  }
}
