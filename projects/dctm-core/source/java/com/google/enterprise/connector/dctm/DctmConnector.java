// Copyright 2006 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.enterprise.connector.dctm;

import com.google.enterprise.connector.dctm.dfcwrap.IClientX;
import com.google.enterprise.connector.spi.Connector;
import com.google.enterprise.connector.spi.RepositoryException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DctmConnector implements Connector {
  private static final Logger logger =
      Logger.getLogger(DctmConnector.class.getName());

  private String login;

  private String password;

  private String docbase;

  private IClientX clientX;

  private String webtopDisplayUrl;

  private final List<String> whereClause = new ArrayList<String>();

  private String isPublic;

  private Set<String> includedMeta;

  private Set<String> excludedMeta;

  private Set<String> includedObjectType;

  private String rootObjectType;

  private String globalNamespace;

  private String localNamespace;

  private String windowsDomain;

  public DctmConnector() {
  }

  public DctmConnector(String googleConnectorWorkDir) {
  }

  @Override
  public DctmSession login() throws RepositoryException {
    logger.log(Level.CONFIG, "login in the docbase " + docbase + " and user "
        + login + " " + clientX + " " + docbase + " "
        + webtopDisplayUrl + " " + whereClause + " "
        + isPublic.equals("on"));

    return new DctmSession(this);
  }

  public void setClientX(String clientX) throws RepositoryException {
    logger.fine("clientX was " + clientX);
    try {
      this.clientX = (IClientX) Class.forName(clientX).newInstance();
    } catch (InstantiationException e) {
      throw new RepositoryException(e);
    } catch (IllegalAccessException e) {
      throw new RepositoryException(e);
    } catch (ClassNotFoundException e) {
      throw new RepositoryException(e);
    } catch (NoClassDefFoundError e) {
      throw new RepositoryException(e);
    }
    logger.config("clientX set to " + this.clientX.getClass().getName());
    logger.config("DFC " + this.clientX.getDFCVersion());
  }

  IClientX getClientX() {
    return clientX;
  }

  public void setAuthentication_type(String authenticationType) {
    logger.log(Level.CONFIG, "authenticationType set to " + authenticationType);
  }

  /*
   * Setters for the data retrieved from Spring
   */
  public void setLogin(String login) {
    this.login = login;
    logger.log(Level.CONFIG, "login set to " + login);
  }

  String getLogin() {
    return login;
  }

  public void setPassword(String password) {
    this.password = password;
    logger.log(Level.CONFIG, "password set to [...]");
  }

  String getPassword() {
    return password;
  }

  public void setDocbase(String docbase) {
    this.docbase = docbase;
    logger.log(Level.CONFIG, "docbase set to " + docbase);
  }

  String getDocbase() {
    return docbase;
  }

  public void setWebtop_display_url(String wsu) {
    this.webtopDisplayUrl = wsu;
    logger.log(Level.CONFIG, "webtop_display_url set to " + wsu);
  }

  String getWebtopDisplayUrl() {
    return webtopDisplayUrl;
  }

  public void setGoogleGlobalNamespace(String globalNamespace) {
    this.globalNamespace = globalNamespace;
    logger.log(Level.CONFIG, "globalnamespace set to " + globalNamespace);
  }

  String getGoogleGlobalNamespace() {
    return globalNamespace;
  }

  public void setGoogleLocalNamespace(String localNamespace) {
    this.localNamespace = localNamespace;
    logger.log(Level.CONFIG, "localnamespace set to " + localNamespace);
  }

  String getGoogleLocalNamespace() {
    return localNamespace;
  }

  public void setWindows_domain(String windowsDomain) {
    this.windowsDomain = windowsDomain;
    logger.log(Level.CONFIG, "windows domain set to: " + windowsDomain);
  }

  /** Gets the Windows domain name to be used for user authentication. */
  String getWindowsDomain() {
    return windowsDomain;
  }

  /*
   * Spring supports converting single values to a list, which handles
   * backward compatibility.
   */
  public void setWhere_clause(List<String> additionalWhereClause) {
    if (additionalWhereClause != null) {
      for (String raw : additionalWhereClause) {
        String clean = DqlUtils.stripLeadingAnd(raw);
        if (clean.length() > 0) {
          whereClause.add(clean);
        }
      }
    }
    if (logger.isLoggable(Level.FINE)
        && !whereClause.equals(additionalWhereClause)) {
      logger.log(Level.FINE, "where_clause was " + additionalWhereClause);
    }
    logger.log(Level.CONFIG, "where_clause set to " + whereClause);
  }

  List<String> getWhereClause() {
    return whereClause;
  }

  public void setIs_public(String isPublic) {
    this.isPublic = isPublic;
    logger.log(Level.CONFIG, "is_public set to " + isPublic);
  }

  boolean isPublic() {
    return isPublic.equals("on");
  }

  public void setRoot_object_type(String rootObjectType) {
    this.rootObjectType = rootObjectType;
    logger.log(Level.CONFIG, "root_object_type set to " + rootObjectType);
  }

  String getRootObjectType() {
    return rootObjectType;
  }

  @SuppressWarnings("unchecked")
  public void setIncluded_object_type(Object includedObjectType) {
    if (includedObjectType instanceof String) {
      this.includedObjectType = csvToSet((String) includedObjectType);
    } else if (includedObjectType instanceof Set) {
      this.includedObjectType = (Set<String>) includedObjectType;
    } else {
      throw new ClassCastException(includedObjectType.getClass().getName());
    }
    logger.log(Level.CONFIG, "included_object_type set to "
        + this.includedObjectType);
  }

  Set<String> getIncludedObjectType() {
    return includedObjectType;
  }

  /**
   * Sets the names of the properties to include.
   *
   * @param includedMeta the object properties to include; it may be a
   * comma-separated string, or for backward compatibility a
   * <code>Set&lt;String&gt;</code>
   */
  @SuppressWarnings("unchecked")
  public void setIncluded_meta(Object includedMeta) {
    if (includedMeta instanceof String) {
      this.includedMeta = csvToSet((String) includedMeta);
    } else if (includedMeta instanceof Set) {
      this.includedMeta = (Set<String>) includedMeta;
    } else {
      throw new ClassCastException(includedMeta.getClass().getName());
    }
    logger.log(Level.CONFIG, "included_meta set to " + this.includedMeta);
  }

  /**
   * Gets the included object properties as a <code>Set&lt;String&gt;</code>.
   *
   * @return a <code>Set&lt;String&gt;</code>
   */
  Set<String> getIncludedMeta() {
    return includedMeta;
  }

  public void setExcluded_meta(Set<String> excludedMeta) {
    this.excludedMeta = excludedMeta;
    logger.log(Level.CONFIG, "excluded_meta set to " + this.excludedMeta);
  }

  Set<String> getExcludedMeta() {
    return excludedMeta;
  }

  /**
   * Converts a comma-separated string into a set of trimmed strings.
   *
   * @param csv a comma-separated string
   * @return a set of strings, each of which is trimmed of leading and
   * trailing whitespace
   */
  private Set<String> csvToSet(String csv) {
    String[] values = csv.split(",");
    Set<String> set = new HashSet<String>(values.length);
    for (String value : values) {
      set.add(value.trim());
    }
    return set;
  }
}
