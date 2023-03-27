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
public class SearchRequestValidation extends BaseValidation {

  /**
   * Validate a key word.
   * Checks if a key word matches a variable name.
   * @param keyWord The key word to validate.
   * @return True if the key word is valid, false otherwise.
   */
  public static boolean validateJavaVariableName(String keyWord) {
    return (isNotNullOrEmpty(keyWord) && keyWord.matches(RegexPattern.JAVA_VARIABLE.getPattern()));
  }

  /**
   * Validate page size.
   * Checks if the page size is between the minimum and maximum values.
   * @param pageSize The page to validate.
   * @return True if the page is valid, false otherwise.
   */
  public static boolean validatePageSize(int pageSize) {
    return (
      isBetween(
        pageSize,
        SearchRequestValidationRules.PAGE_MIN_SIZE.getValue(),
        SearchRequestValidationRules.PAGE_MAX_SIZE.getValue()
      )
    );
  }

  /**
   * Validate page number.
   * Checks if the page number is larger than or equal to the minimum value.
   * @param pageNumber The page to validate.
   * @return True if the page is valid, false otherwise.
   */
  public static boolean validatePageNumber(int pageNumber) {
    return (
      isBetween(
        pageNumber,
        SearchRequestValidationRules.PAGE_MIN_SIZE.getValue(),
        SearchRequestValidationRules.PAGE_MAX_SIZE.getValue()
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

    valid &= validatePageSize(request.getSize());
    valid &= validatePageNumber(request.getPage());

    return valid;
  }
}
