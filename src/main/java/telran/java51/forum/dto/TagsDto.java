package telran.java51.forum.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagsDto {
	LocalDate dateFrom;
	LocalDate dateTo;
}
