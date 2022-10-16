package
        com.banking.projectserver.serviceImpl;

import com.banking.projectserver.entity.TradeRecordDisp;
import com.banking.projectserver.entity.TradeRecord;
import com.banking.projectserver.mapper.TradeRecordMapper;
import com.banking.projectserver.service.TradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO:此处写TradeRecordServiceImpl类的描述
 *
 * @author 孟繁霖
 * @date 2021/11/29 15:29
 */
@Service
public class TradeRecordServiceImpl implements TradeRecordService {

    @Autowired
    private TradeRecordMapper tradeRecordMapper;
    @Override
    public Integer addTradeRecord(TradeRecord tradeRecord) {
        return tradeRecordMapper.addTradeRecord(tradeRecord);
    }

    @Override
    public List<TradeRecordDisp> getTradeRecordByAccountID(String accountID){
        return tradeRecordMapper.getTradeRecordByAccountID(accountID);
    }

    @Override
    public TradeRecord getTradeRecordByRecordID(Integer recordID){
        return tradeRecordMapper.getTradeRecordByRecordID(recordID);
    }
}
