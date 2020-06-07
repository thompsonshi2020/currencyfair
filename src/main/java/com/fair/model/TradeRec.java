package com.fair.model;

import java.util.Arrays;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.fair.util.LoggerUtil;

/**
 * The table of trade messages
 */

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "trade_rec")
public class TradeRec implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "trade_rec_sequence";

    @ApiModelProperty(hidden = true)
    private long tradeId;   
    
    @ApiModelProperty(hidden = true)
    private boolean tradeSuccess; 
    
    @ApiModelProperty(hidden = true)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss") 
    private Date timeCreated;

    @ApiModelProperty(hidden = true)
    private String currencyPair;

    @NotEmpty (message = "userId is mandatory")
    @NotNull (message = "userId is mandatory")
    private String userId;
    
    @NotEmpty (message = "currencyFrom is mandatory")
    @NotNull (message = "currencyFrom is mandatory")
    private String currencyFrom;

    @NotEmpty (message = "currencyTo is mandatory")
    @NotNull (message = "currencyTo is mandatory")
    private String currencyTo;

    @NotNull (message = "amountSell is mandatory")
    private BigDecimal amountSell;

    @NotNull (message = "amountBuy is mandatory")
    private BigDecimal amountBuy;

    @NotNull (message = "rate is mandatory")
    private BigDecimal rate;

    @NotNull (message = "timePlaced is mandatory")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MMM-yy HH:mm:ss")
    private Date timePlaced;
    
    @NotEmpty (message = "originatingCountry is mandatory")
    @NotNull (message = "originatingCountry is mandatory")    
    private String originatingCountry;

        
    public TradeRec() {}

    public void setCurrencyPair(String currencyPair) {
      
      try {
        //if (currencyPair = null || currencyPair.isEmpty()) {
          if (this.currencyTo != null && this.currencyFrom != null) {
            String[] pair = {this.currencyTo, this.currencyFrom};
            Arrays.sort(pair);              
            this.currencyPair = pair[0] + "/" + pair[1];
        } else {
            this.currencyPair = currencyPair;
        }
      } catch (Exception e) {
        LoggerUtil.logError(TradeRec.class, "Error Occurred", e);
      }
    }

  public String toString() {
      return "userId:" + userId + ",timePlaced:" + timePlaced + ",originatingCountry:" + originatingCountry;
  }
}