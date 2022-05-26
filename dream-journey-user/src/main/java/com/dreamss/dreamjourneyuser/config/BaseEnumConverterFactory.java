package com.dreamss.dreamjourneyuser.config;

import com.dreamss.dreamjourneycommon.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class BaseEnumConverterFactory implements ConverterFactory<String, BaseEnum<?>> {

    @Override
    public <T extends BaseEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            for (T t: targetType.getEnumConstants()) {
                if (t.getValue().toString().equals(source)) {
                    return t;
                }
            }
            return null;
        };
    }
}
