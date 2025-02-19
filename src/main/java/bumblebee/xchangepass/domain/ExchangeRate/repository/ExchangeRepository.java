package bumblebee.xchangepass.domain.ExchangeRate.repository;


import bumblebee.xchangepass.domain.ExchangeRate.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    Optional<Exchange> findByCurrency(String currency);
}
