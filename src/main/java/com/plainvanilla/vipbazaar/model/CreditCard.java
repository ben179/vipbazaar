package com.plainvanilla.vipbazaar.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ga2begs
 * Date: 02/09/13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name="CREDIT_CARD")
@PrimaryKeyJoinColumn(name="CREDIT_CARD_ID")
public class CreditCard extends BillingDetails {
          /*
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CREDIT_CARD_ID", nullable = false, insertable = false, updatable = false)
    private Long cardId;    */

    @Enumerated(EnumType.ORDINAL)
    @Column(name="CREDIT_CARD_TYPE", nullable = false)
    private CreditCardType type;

    @Column(name="CARD_NUMBER", nullable = false)
    private String number;

    @Column(name="CARD_EXP_MONTH", nullable = false)
    private String expMonth;

    @Column(name="CARD_EXP_YEAR", nullable = false)
    private String expYear;
  /*
    public Long getCardId() {
        return cardId;
    }

    private void setCardId(Long cardId) {
        this.cardId = cardId;
    }   */

    public CreditCardType getType() {
        return type;
    }

    public void setType(CreditCardType type) {
        this.type = type;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
