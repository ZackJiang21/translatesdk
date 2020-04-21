package org.iiai.translate.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface TranslateConst {
    int INPUT_LIMIT = 5000;

    String AR2EN_ID = "ar2en";

    String EN2AR_ID = "en2ar";

    List<String> MODEL_LIST = Collections.unmodifiableList(Arrays.asList(AR2EN_ID, EN2AR_ID));

    String TOKEN_HEADER = "X-Auth-Token";
}
