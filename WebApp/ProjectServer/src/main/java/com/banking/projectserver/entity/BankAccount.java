package
        com.banking.projectserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * TODO:此处写BankAccount类的描述
 *
 * @author 孟繁霖
 * @date 2021/11/29 13:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private String accountID;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public BigDecimal getOpenMoney() {
        return openMoney;
    }

    public void setOpenMoney(BigDecimal openMoney) {
        this.openMoney = openMoney;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getIsReportLoss() {
        return isReportLoss;
    }

    public void setIsReportLoss(Integer isReportLoss) {
        this.isReportLoss = isReportLoss;
    }


    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date openDate;
    private BigDecimal openMoney;
    private String accountPassword;
    private BigDecimal balance;
    private Integer isReportLoss;
}
