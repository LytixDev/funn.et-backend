package edu.ntnu.idatt2105.placeholder.filtering;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification for filtering and sorting.
 * @see https://blog.piinalpin.com/2022/04/searching-and-filtering-using-jpa-specification/
 * @author Callum G.
 * @version 1.0 - 18.3.2023
 */
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

  private static final long serialVersionUID = 987612345L;

  private final transient SearchRequest searchRequest;

  @Override
  public Predicate toPredicate(
    Root<T> root,
    CriteriaQuery<?> query,
    CriteriaBuilder builder
  ) {
    Predicate predicate = builder.equal(
      builder.literal(Boolean.TRUE),
      Boolean.TRUE
    );

    for (FilterRequest filter : this.searchRequest.getFilterRequests()) predicate =
      filter.getOperator().build(root, builder, filter, predicate);

    List<Order> orders = new ArrayList<>();
    for (SortRequest sort : this.searchRequest.getSortRequests()) orders.add(
      sort.getSortDirection().build(root, builder, sort)
    );

    query.orderBy(orders);
    return predicate;
  }

  public static Pageable getPageable(SearchRequest searchRequest) {
    return PageRequest.of(
      searchRequest.getPage() > 0 ? searchRequest.getPage() : 0,
      searchRequest.getSize() > 0 ? searchRequest.getSize() : 20
    );
  }
}
