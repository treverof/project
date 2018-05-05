package po;

import lombok.Data;

/**
 * Created by lenovo on 2018/5/6.
 */
@Data
public class BookInfo {

    private String bookName;

    private String bookUrl;

    private String bookAuthor;

    private String publisher;

    private String publishTime;

    private Double price;

    private Double rateNum;

    private String description;

}
