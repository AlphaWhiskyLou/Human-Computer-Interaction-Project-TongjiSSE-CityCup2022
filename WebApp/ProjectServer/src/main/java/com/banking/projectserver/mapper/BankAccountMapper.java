package
        com.banking.projectserver.mapper;

import com.banking.projectserver.entity.BankAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TODO:此处写BankAccountMapper类的描述
 *
 * @author 孟繁霖
 * @date 2021/11/29 13:45
 */
@Mapper
@Repository
public interface BankAccountMapper {
    @Select("select * from bankingDB.bankAccount where accountID=#{accountId}")
    BankAccount getAccountInfoById(String accountId);

    @Select("select * from bankingDB.bankAccount where userID=#{userId}")
    List<BankAccount> getAllAccountByUserId(String userId);

    /**
    * 更新账户余额
    * @param accountId 账号ID
    * @param money 存款+，取款-
    * @return : java.lang.Integer
    * @author Covenant
    * @date 2021-11-29 15:03
    */
    @Update("update bankingDB.bankAccount set balance=balance+#{money} where accountID=#{accountId}")
    Integer updateMoney(String accountId, BigDecimal money);

    @Select("select accountPassword from bankingDB.bankAccount where accountID=#{accountId}")
    String getAccountPassword(String accountId);

    @Insert("insert into bankingDB.bankAccount(accountID,userID,openDate,openMoney,accountPassword,balance,isReportLoss) values(#{accountId},#{userId},#{openDate},#{openMoney},#{accountPassword},#{balance},#{isReportLoss})")
    void openAccount(String accountId, String userId, Date openDate, BigDecimal openMoney, String accountPassword, BigDecimal balance, Integer isReportLoss);
}
