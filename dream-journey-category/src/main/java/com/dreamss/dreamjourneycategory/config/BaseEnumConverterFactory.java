package com.dreamss.dreamjourneycategory.config;

import com.dreamss.dreamjourneycommon.enums.BaseEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class BaseEnumConverterFactory implements ConverterFactory<String, BaseEnum<?>> {

    @NotNull
    @Override
    public <T extends BaseEnum<?>> Converter<String, T> getConverter(@NotNull Class<T> targetType) {
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
