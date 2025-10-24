
package com.example.demo.dto;
import lombok.Data;

@Data
public class BorrowedRequest {

    private Integer bookId;
    private Integer memberId;
}
