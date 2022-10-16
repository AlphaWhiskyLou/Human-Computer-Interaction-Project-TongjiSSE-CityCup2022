package
        com.banking.projectserver.mapper;

import com.banking.projectserver.entity.TradeRecordDisp;
import com.banking.projectserver.entity.TradeRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO:此处写TradeRecordMapper类的描述
 *
 * @author 孟繁霖
 * @date 2021/11/29 15:25
 */
@Mapper
@Repository
public interface TradeRecordMapper {

    @Insert("insert into tradeRecord(recordID,accountID,functionID,tradeAmount,tradeTime,payeeAccountID)" +
            "values(#{recordID},#{accountID},#{functionID},#{tradeAmount},#{tradeTime},#{payeeAccountID})")
    Integer addTradeRecord(TradeRecord tradeRecord);

    @Select("select * from bankingDB.tradeRecord natural join bankingDB.systemFunction where accountID=#{accountID} order by tradeTime desc")
    List<TradeRecordDisp> getTradeRecordByAccountID(String accountID);

    @Select("select * from bankingDB.tradeRecord where recordID=#{recordID}")
    TradeRecord getTradeRecordByRecordID(Integer recordID);
}
