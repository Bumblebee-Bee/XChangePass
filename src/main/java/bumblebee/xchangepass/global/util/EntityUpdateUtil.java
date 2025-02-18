package bumblebee.xchangepass.global.util;

import bumblebee.xchangepass.global.error.ErrorCode;
import com.querydsl.core.types.Path;
import com.querydsl.jpa.impl.JPAUpdateClause;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.BiConsumer;

public class EntityUpdateUtil {

    /**
     * 필드 값이 변경되었을 경우, 해당 필드를 업데이트하는 공통 로직
     *
     * @param existingValue 기존 값 (데이터베이스에서 조회된 값)
     * @param newValue 업데이트된 새로운 값
     * @param updateClause 필드 업데이트를 위한 JPAUpdateClause 객체
     * @param path 업데이트할 엔티티 필드를 나타내는 QueryDSL Path 객체
     * @param <T> 기존 값의 타입
     * @param <E> 새로운 값의 타입
     * @return 값이 변경되었을 경우 true, 그렇지 않으면 false
     */
    public static <T, E> boolean updateIfChanged(
            T existingValue,
            E newValue,
            JPAUpdateClause updateClause,
            Path<E> path
    ) {
        if (!Objects.equals(existingValue, newValue)) {
            updateClause.set(path, newValue);
            return true;
        }
        return false;
    }


    /**
     * 엔티티의 필드를 업데이트하는 공통 로직을 실행하는 메서드
     *
     * @param entity 엔티티 객체
     * @param updateRequest 업데이트 요청 객체 (업데이트할 값들이 포함된 객체)
     * @param updateClause 필드 업데이트를 위한 JPAUpdateClause 객체
     * @param updateExecutor 업데이트를 실행할 때 호출할 BiConsumer 함수
     */
    public static <T> void executeUpdateIfChanged(
            T entity,
            Object updateRequest,
            JPAUpdateClause updateClause,
            BiConsumer<Boolean, JPAUpdateClause> updateExecutor,
            Object qEntity
    ) {
        boolean isUpdated = false;

        Field[] fields = updateRequest.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                Object newValue = field.get(updateRequest);
                Method getterMethod = entity.getClass().getMethod("get" + capitalize(fieldName));
                Object existingValue = getterMethod.invoke(entity);

                // Q-type 클래스에서 Path 객체 가져오기
                Field qField = qEntity.getClass().getField(fieldName);
                Path<Object> path = (Path<Object>) qField.get(qEntity);

                isUpdated |= updateIfChanged(
                        existingValue,
                        newValue,
                        updateClause,
                        path
                );
            } catch (Exception e) {
                throw ErrorCode.ENTITY_FIELD_ACCESS_ERROR.commonException();
            }
        }

        if (isUpdated) {
            updateExecutor.accept(isUpdated, updateClause);
        }
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

