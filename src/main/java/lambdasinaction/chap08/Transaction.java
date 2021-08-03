package lambdasinaction.chap08;

/**
 * @version 1.0
 * @Description:
 * @author: bingyu
 * @date: 2021/8/3
 */
public class Transaction {

    private String referenceCode;

    public Transaction() {
    }

    public Transaction(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "referenceCode='" + referenceCode + '\'' +
                '}';
    }
}
