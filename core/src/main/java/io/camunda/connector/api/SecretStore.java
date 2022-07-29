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
package io.camunda.connector.api;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecretStore {

  private static final Pattern SECRET_PATTERN = Pattern.compile("^secrets\\.(\\S+)$");

  protected SecretProvider secretProvider;

  public SecretStore(SecretProvider secretProvider) {
    this.secretProvider = secretProvider;
  }

  public String replaceSecret(String value) {
    final Optional<String> secretName =
        Optional.ofNullable(value)
            .map(String::trim)
            .map(SECRET_PATTERN::matcher)
            .filter(Matcher::matches)
            .map(matcher -> matcher.group(1));

    if (secretName.isPresent()) {
      return secretName
          .map(secretProvider::getSecret)
          .orElseThrow(
              () ->
                  new IllegalArgumentException(
                      String.format("Secret with name '%s' is not available", secretName.get())));
    } else {
      return value;
    }
  }
}
