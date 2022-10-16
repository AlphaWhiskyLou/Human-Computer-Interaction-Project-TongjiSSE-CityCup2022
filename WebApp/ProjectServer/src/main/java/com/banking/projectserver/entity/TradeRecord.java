package
        com.banking.projectserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO:此处写TradeRecord类的描述
 *
 * @author 孟繁霖
 * @date 2021/11/29 15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeRecord {
    private String recordID;
    private String accountID;
    private Integer functionID;
    private BigDecimal tradeAmount;
    private Date tradeTime;
    private String payeeAccountID;
}
