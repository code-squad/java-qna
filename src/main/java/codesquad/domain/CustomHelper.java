package codesquad.domain;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class CustomHelper {
    public String please() {
        return "please!!";
    }
}
