/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.samples.nested.shared;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> packing because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is note translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

  /**
   * Verifies that the specified name is valid for our service.
   *
   * In this example, we only require that the name is at least four characters.
   * In your application, you can use more complex checks to ensure that
   * usernames, passwords, email addresses, URLs, and other fields have the
   * proper syntax.
   *
   * @param name the name to validate
   * @return true if valid, false if invalid
   */
  public static boolean isValidName(String name) {
    if (name == null) {
      return false;
    }
    return name.length() > 3;
  }
  
  
  public static boolean isValidUserName(String name) {

	    if (name == null) {
	      return false;
	    }

	    return name.length() > 3;
	  }

	  /*

	  (                         # Start of group
	      (?=.*\d)              #   must contains one digit from 0-9
	      (?=.*[a-z])           #   must contains one lower case characters
	      (?=.*[A-Z])           #   must contains one upper case characters
	      (?=.*[@#$%])          #   must contains one special symbols in the list "@#$%"
	                  .         #     match anything with previous condition checking
	                    {8,32}  #        length at least 8 characters and maximum of 32
	    )                       # End of group

	    Passwords must contain at least 8 characters with at least one digit,
	    one upper case letter, one lower case letter and one special symbol (“@#$%”).

	  */

	  private static final String PASSWORD_VALIDATION_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,32})";

	  public static boolean isValidPassword(String password) {

	    if (password == null) {
	      return false;
	    }

	    return password.matches(PASSWORD_VALIDATION_REGEX);
	  }
}
