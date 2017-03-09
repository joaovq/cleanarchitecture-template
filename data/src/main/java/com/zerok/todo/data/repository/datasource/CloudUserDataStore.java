/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zerok.todo.data.repository.datasource;

import com.zerok.todo.data.entity.UserEntity;
import com.zerok.todo.data.net.RestApi;
import com.zerok.todo.domain.features.user.User;

import io.reactivex.Observable;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link UserDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudUserDataStore implements UserDataStore {

  private final RestApi restApi;
  private DiskUserDataStore diskUserDataStore;

  /**
   * Construct a {@link UserDataStore} based on connections to the api (Cloud).
   *
   * @param restApi The {@link RestApi} implementation to use.
   * @param diskUserDataStore A {@link UserDataStore} to cache data retrieved from the api.
   */
  @Inject
  public CloudUserDataStore(RestApi restApi, DiskUserDataStore diskUserDataStore) {
    this.restApi = restApi;
    this.diskUserDataStore = diskUserDataStore;
  }

  @Override public Observable<List<UserEntity>> userEntityList() {
    return this.restApi.userEntityList();
  }

  @Override public Observable<UserEntity> userEntityDetails(final int userId) {
    return null;
    //return this.restApi.userEntityById(userId).doOnNext(diskUserDataStore.ad);
  }

  @Override
  public Observable<Void> addUserEntity(User user) {
    return null;
  }
}
