package
        com.banking.projectserver.service;

import com.banking.projectserver.entity.TradeRecordDisp;
import com.banking.projectserver.entity.TradeRecord;

import java.util.List;

/**
 * TODO:此处写TradeRecordService类的描述
 *
 * @author 孟繁霖
 * @date 2021/11/29 15:28
 */
public interface TradeRecordService {
    Integer addTradeRecord(TradeRecord tradeRecord);
    List<TradeRecordDisp> getTradeRecordByAccountID(String accountID);
    TradeRecord getTradeRecordByRecordID(Integer recordID);

}
