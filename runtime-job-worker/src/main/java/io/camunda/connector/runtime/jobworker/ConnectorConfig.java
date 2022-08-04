/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.camunda.connector.runtime.jobworker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** Configuration class holding information of a connector. */
public class ConnectorConfig {

  /** Pattern describing the property TYPE of a connector. */
  public static final Pattern ZEEBE_CONNECTOR_PATTERN =
      Pattern.compile("^ZEEBE_CONNECTOR_([^_]+)_TYPE$");

  private String name;
  private String type;
  private final String[] variables;
  private String function;

  /**
   * Create a connector configuration.
   *
   * @param name the name of connector
   * @param type the type of the connector
   * @param variables the variables the connector needs as input
   * @param className the connector function class
   */
  public ConnectorConfig(String name, String type, String[] variables, String className) {
    this.name = name;
    this.type = type;
    this.variables = variables;
    this.function = className;
  }

  /**
   * Parses the connector definitions provided externally, e.g. via environment variables.
   *
   * @return the list of connector configurations
   */
  public static List<ConnectorConfig> parse() {

    var connectors = new ArrayList<ConnectorConfig>();

    for (var entry : System.getenv().entrySet()) {

      var key = entry.getKey();

      var match = ZEEBE_CONNECTOR_PATTERN.matcher(key);

      if (match.matches()) {
        connectors.add(parseConnector(match.group(1)));
      }
    }

    return connectors;
  }

  private static ConnectorConfig parseConnector(String name) {

    var type = getEnv(name, "TYPE");
    var function = getEnv(name, "FUNCTION");
    var variables = getEnv(name, "VARIABLES").split(",");

    return new ConnectorConfig(name, type, variables, function);
  }

  private static String getEnv(String name, String detail) {
    return System.getenv("ZEEBE_CONNECTOR_" + name + "_" + detail);
  }

  /**
   * @return the name of connector
   */
  public String getName() {
    return name;
  }

  /**
   * @return the type of the connector
   */
  public String getType() {
    return type;
  }

  /**
   * @return the connector function class
   */
  public String getFunction() {
    return function;
  }

  /**
   * @return the variables the connector needs as input
   */
  public String[] getVariables() {
    return variables;
  }
}
