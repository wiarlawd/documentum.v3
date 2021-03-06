// Copyright 2013 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.enterprise.connector.dctm.dctmmockwrap;

import com.google.enterprise.connector.dctm.dfcwrap.IUser;
import com.google.enterprise.connector.spi.RepositoryDocumentException;
import com.google.enterprise.connector.spi.RepositoryException;

public class MockDmUser implements IUser {
  private final String username;
  private final String userSource;
  private final String userLdapDn;
  private final boolean isGroup;

  public MockDmUser(String username, String userSource,
      String userLdapDn, boolean isGroup) {
    this.username = username;
    this.userSource = userSource;
    this.userLdapDn = userLdapDn;
    this.isGroup = isGroup;
  }

  @Override
  public String getUserName() throws RepositoryException {
    return this.username;
  }

  @Override
  public String getUserLoginName() throws RepositoryException {
    return this.username;
  }

  @Override
  public String getUserSourceAsString() throws RepositoryDocumentException {
    return userSource;
  }

  @Override
  public String getUserDistinguishedLDAPName()
      throws RepositoryDocumentException {
    return userLdapDn;
  }

  @Override
  public boolean isGroup() throws RepositoryException {
    return isGroup;
  }
}
