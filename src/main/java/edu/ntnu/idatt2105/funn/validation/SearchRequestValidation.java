package edu.ntnu.idatt2105.funn.validation;

import edu.ntnu.idatt2105.funn.filtering.FilterRequest;
import edu.ntnu.idatt2105.funn.filtering.SearchRequest;
import edu.ntnu.idatt2105.funn.filtering.SortRequest;
import edu.ntnu.idatt2105.funn.validation.rules.SearchRequestValidationRules;

/**
 * Class for search request validation.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class SearchRequestValidation extends Validation {

  /**
   * Validate a key word.
   * Checks if a key word matches a variable name.
   * @param keyWord The key word to validate.
   * @return True if the key word is valid, false otherwise.
   */
  public static boolean validateJavaVariableName(String keyWord) {
    return (
      isNotNullOrEmpty(keyWord) &&
      keyWord.matches(RegexPattern.JAVA_VARIABLE.getPattern()) &&
      isBetween(
        keyWord,
        SearchRequestValidationRules.JAVA_VARIABLE_MIN_LENGTH.getValue(),
        SearchRequestValidationRules.JAVA_VARIABLE_MAX_LENGTH.getValue()
      )
    );
  }

  /**
   * Validate search request.
   * @param request The request to validate.
   * @return True if the request is valid, false otherwise.
   */
  public static boolean validateSearchRequest(SearchRequest request) {
    boolean valid = true;

    for (FilterRequest filter : request.getFilterRequests()) valid &=
      validateJavaVariableName(filter.getKeyWord());

    for (SortRequest sort : request.getSortRequests()) valid &=
      validateJavaVariableName(sort.getKeyWord());

    valid &=
      isBetween(
        request.getSize(),
        SearchRequestValidationRules.PAGE_MIN_SIZE.getValue(),
        SearchRequestValidationRules.PAGE_MAX_SIZE.getValue()
      );
    valid &=
      isLargerThanOrEqual(
        request.getPage(),
        SearchRequestValidationRules.PAGE_MIN_NUMBER.getValue()
      );

    return valid;
  }
}
