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
package com.zerok.todo.data.repository.datasource;

import com.zerok.todo.data.entity.UserEntity;
import com.zerok.todo.data.entity.mapper.UserEntityDataMapper;
import com.zerok.todo.domain.features.user.User;

import io.reactivex.Observable;
import io.realm.Realm;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link UserDataStore} implementation based on file system data store.
 */
public class DiskUserDataStore implements UserDataStore {

  private Realm realmInstance;
  private UserEntityDataMapper userEntityDataMapper;

  /**
   * Construct a {@link UserDataStore} based file system data store.
   *
   * @param realm A {@link Realm} instance to add data into the cache
   * @param userEntityDataMapper A {@link UserEntityDataMapper} mapper to transform Domains's data
   *                             into Data's data
   */
  @Inject
  public DiskUserDataStore(Realm realm,
                    UserEntityDataMapper userEntityDataMapper ) {
    this.realmInstance = realm;
    this.userEntityDataMapper = userEntityDataMapper;
  }

  @Override public Observable<List<UserEntity>> userEntityList() {
    return Observable.create(emitter -> {
      UserEntity userEntity = new UserEntity();
      userEntity.setFullName("Test");
      userEntity.setEmail("test@test.com");
      userEntity.setDescription("User test");
      userEntity.setCoverUrl("http://www.test.com.br/");
      userEntity.setFollowers(10);

      emitter.onNext(Arrays.asList(userEntity));
      emitter.onComplete();
    });
  }

  @Override public Observable<UserEntity> userEntityDetails(final int userId) {
    return Observable.create(emitter -> {
      UserEntity userEntity = new UserEntity();
      userEntity.setFullName("Test");
      userEntity.setEmail("test@test.com");
      userEntity.setDescription("User test");
      userEntity.setCoverUrl("http://www.test.com.br/");
      userEntity.setFollowers(10);

      emitter.onNext(userEntity);
      emitter.onComplete();
    });
  }

  @Override
  public Observable<Void> addUserEntity(User user) {
    return Observable.create( emitter -> {
      realmInstance.beginTransaction();
      userEntityDataMapper.transform(user);
      realmInstance.commitTransaction();
    });
  }
}
