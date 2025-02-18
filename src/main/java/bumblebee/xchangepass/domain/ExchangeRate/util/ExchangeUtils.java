package bumblebee.xchangepass.domain.ExchangeRate.util;

import bumblebee.xchangepass.domain.ExchangeRate.dto.response.ExchangeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ExchangeUtils {

    @Value("${api.key}")
    private String authkey;

    @Value("${api.data}")
    private String data;

    private final String searchdate = getSearchdate();

    WebClient webClient;

    public JsonNode getExchangeDataSync() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        // HttpClient 설정 (타임아웃)
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)  // 연결 타임아웃 설정
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5, TimeUnit.SECONDS)))  // 읽기 타임아웃 설정
                .responseTimeout(Duration.ofMillis(5000));  // 전체 응답 타임아웃 설정

        // WebClient 설정
        webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .clientConnector(new ReactorClientHttpConnector(httpClient))  // WebClient에 HttpClient 적용
                .build();

        try {
            // WebClient를 사용하여 동기적으로 데이터를 요청하고, 바로 parseJson 함수를 호출합니다.
            String responseBody = webClient.get()
                    .uri(builder -> builder
                            .scheme("https")
                            .host("www.koreaexim.go.kr")
                            .path("/site/program/financial/exchangeJSON")
                            .queryParam("authkey", authkey)
                            .queryParam("searchdate", searchdate)
                            .queryParam("data", data)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .retry(3)  // 3번 재시도 설정
                    .block(); // 동기적으로 결과를 얻음

            return parseJson(responseBody);
        } catch (WebClientResponseException e) {
            // HTTP 응답 예외 처리 (예: 4xx, 5xx 오류)
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // 다른 예외 처리 (네트워크 오류 등)
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode parseJson(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ExchangeDto> getExchangeDataAsDtoList() {
        JsonNode jsonNode = getExchangeDataSync();

        if (jsonNode != null && jsonNode.isArray()) {
            List<ExchangeDto> exchangeDTOList = new ArrayList<>();

            for (JsonNode node : jsonNode) {
                ExchangeDto exchangeDTO = convertJsonToExchangeDto(node);
                exchangeDTOList.add(exchangeDTO);
            }

            return exchangeDTOList;
        }

        return Collections.emptyList();
    }

    public ExchangeDto convertJsonToExchangeDto(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.treeToValue(jsonNode, ExchangeDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSearchdate() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        // 토요일
        if (dayOfWeek.getValue() == 6)
            return currentDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 일요일
        if (dayOfWeek.getValue() == 7)
            return currentDate.minusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
