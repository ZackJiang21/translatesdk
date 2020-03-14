package org.iiai.translate.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface TranslateConst {
    int INPUT_LIMIT = 5000;

    List<String> MODEL_LIST = Collections.unmodifiableList(Arrays.asList("ar2en", "en2ar"));
}
