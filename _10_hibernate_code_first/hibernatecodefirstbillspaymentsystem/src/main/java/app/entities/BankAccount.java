package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank-accounts")
public class BankAccount extends BillingDetails {
    private String bankName;
    private String swiftCode;

    public BankAccount() {
    }

    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "swift_code")
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
