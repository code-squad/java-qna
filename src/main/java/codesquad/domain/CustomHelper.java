package codesquad.domain;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.io.IOException;

@HandlebarsHelper
public class CustomHelper{
    public String post() {
        return "please!!";
    }
}
