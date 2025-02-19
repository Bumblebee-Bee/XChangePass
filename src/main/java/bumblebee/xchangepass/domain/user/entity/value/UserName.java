package bumblebee.xchangepass.domain.user.entity.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class UserName {
    public static final String REGEX = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,5}$";
    public static final String ERR_MSG = "실명은 특수문자를 제외한 2~5자리여야 합니다.";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    @Column(name = "user_name", nullable = false, length = 10)
    private String value;

    public UserName(final String name) {
        if (!PATTERN.matcher(name).matches()) {
            log.error(ERR_MSG);
            throw new IllegalArgumentException(ERR_MSG);
        }
        this.value = name;
    }
}
