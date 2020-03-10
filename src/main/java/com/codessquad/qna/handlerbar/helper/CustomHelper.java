package com.codessquad.qna.handlerbar.helper;

import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.TagType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.io.IOException;

@HandlebarsHelper
public class CustomHelper {
    public Object eq(final Object a, final Options options) throws IOException {
        Object b = options.param(0, null);
        boolean result = new EqualsBuilder().append(a, b).isEquals();
        if (options.tagType == TagType.SECTION) {
            return result ? options.fn() : options.inverse();
        }
        return result
                ? options.hash("yes", true)
                : options.hash("no", false);
    }
}
