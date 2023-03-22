package edu.ntnu.idatt2105.funn.filtering;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest implements Serializable {

  private static final long serialVersionUID = 543216789L;

  private List<FilterRequest> filterRequests;

  private List<SortRequest> sortRequests;

  private int page;

  private int size;

  public List<FilterRequest> getFilterRequests() {
    if (filterRequests == null) {
      filterRequests = new ArrayList<>();
    }
    return filterRequests;
  }

  public List<SortRequest> getSortRequests() {
    if (sortRequests == null) {
      sortRequests = new ArrayList<>();
    }
    return sortRequests;
  }
}
