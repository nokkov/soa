package ru.itmo.util;

import jakarta.ws.rs.core.UriInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class PersonFilter {
    private int page = 1;
    private int pageSize = 10;
    List<String> sortBy;
    List<String> filterBy;

    public PersonFilter(UriInfo uriInfo) {
        String pageParam = uriInfo.getQueryParameters().getFirst("page");
        if (pageParam != null) {
            this.page = Integer.parseInt(pageParam);
        }

        String sizeParam = uriInfo.getQueryParameters().getFirst("size");
        if (sizeParam != null) {
            this.pageSize = Integer.parseInt(sizeParam);
        }

        uriInfo.getQueryParameters().forEach((key, values) -> {
            if (key.startsWith("filter[") && key.endsWith("]")) {
                filterBy.add(key.substring("filter[".length(), key.length() - 1));
            }
        });

        //this.sort = uriInfo.getQueryParameters().getFirst("sort");
    }
}
