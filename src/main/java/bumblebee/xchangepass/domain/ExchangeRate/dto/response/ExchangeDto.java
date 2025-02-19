package bumblebee.xchangepass.domain.ExchangeRate.dto.response;

import bumblebee.xchangepass.domain.ExchangeRate.entity.Exchange;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExchangeDto (
     Integer result,
     String cur_unit, // 통화코드
     String cur_nm, // 국가/통화명
     String ttb, // 전신환(송금) 받으실 때
     String tts, // 전신환(송금) 보내실 때
     String deal_bas_r, // 매매 기준율
     String bkpr, // 장부가격
     String yy_efee_r, // 년환가료율
     String ten_dd_efee_r, // 10일환가료율
     String kftc_bkpr, // 서울외국환중개 매매기준율
     String kftc_deal_bas_r, // 서울외국환중개장부가격
     LocalDateTime created_at){
    public static Exchange toEntity(ExchangeDto exchangeDto){
        return Exchange.builder()
                .currency(exchangeDto.cur_unit)
                .currencyName(exchangeDto.cur_nm)
                .rate(exchangeDto.deal_bas_r)
                .build();
    }
}
