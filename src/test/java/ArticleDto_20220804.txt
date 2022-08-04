import lombok.Data;

import java.time.LocalDateTime;


@Data
public class ArticleDto {
    private long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String title;
    private String body;
    private boolean isBlind;
}

/*@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private long id;
    private String title;
    private String body;
    private String writer;
    private LocalDate createDate;
    private LocalDate modifyDate;

}*/
