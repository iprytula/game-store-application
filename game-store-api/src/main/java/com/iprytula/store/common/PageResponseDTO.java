package com.iprytula.store.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDTO<T> {
	private List<T> content;
	private Long totalElements;
	private Integer totalPages;
	private Boolean isFirst;
	private Boolean isLast;
	private Integer size;
	private Integer number;
}
