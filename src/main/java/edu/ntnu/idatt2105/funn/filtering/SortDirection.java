package edu.ntnu.idatt2105.funn.filtering;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

/**
 * Enum for sorting direction.
 * @see https://blog.piinalpin.com/2022/04/searching-and-filtering-using-jpa-specification/
 * @author Callum G.
 * @version 1.0 - 18.3.2023
 */
public enum SortDirection {
  ASC {
    public <T> Order build(Root<T> root, CriteriaBuilder builder, SortRequest request) {
      return builder.asc(root.get(request.getKeyWord()));
    }
  },
  DESC {
    public <T> Order build(Root<T> root, CriteriaBuilder builder, SortRequest request) {
      return builder.desc(root.get(request.getKeyWord()));
    }
  };

  public abstract <T> Order build(Root<T> root, CriteriaBuilder builder, SortRequest request);
}
