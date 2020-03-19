package org.iiai.translate;

import org.iiai.translate.constant.ErrorCode;
import org.iiai.translate.constant.TranslateConst;
import org.iiai.translate.exception.TranslatorException;
import org.iiai.translate.model.Document;
import org.iiai.translate.util.TranslateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Translator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Translator.class);

    public static String getTranslation(String modeId, String input, String url, String token) {
        if (!TranslateConst.MODEL_LIST.contains(modeId)) {
            LOGGER.error("Model id {} is illegal", modeId);
            throw new TranslatorException(ErrorCode.INVALID_PARAM);
        }
        if (input.length() > TranslateConst.INPUT_LIMIT || input.length() == 0) {
            LOGGER.error("Input length is illegal: {}", input.length());
            throw new TranslatorException(ErrorCode.INVALID_PARAM);
        }
        Document document = new Document(input);
        String translation = TranslateUtil.getTranslation(document, modeId, url, token);
        return translation;
    }
}
