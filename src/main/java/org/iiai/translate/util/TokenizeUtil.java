package org.iiai.translate.util;

import org.iiai.translate.constant.ErrorCode;
import org.iiai.translate.exception.TranslatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TokenizeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenizeUtil.class);

    private static final String SCRIPT =
            "from nltk.tokenize import sent_tokenize\n" +
            "import sys\n" +
            "if len(sys.argv) < 2:\n\t" +
                "print(\"Error: Please input document to tokenize\")\n\t" +
                "sys.exit(1)\n" +
            "else:\n\t" +
                "sentence = sys.argv[1]\n\t" +
                "sent_list = sent_tokenize(sentence)\n\t" +
                "for sent in sent_list:\n\t\t" +
                    "print(sent)\n\t" +
                "sys.exit(0)";

    public static List<String> getTokenizedSents(String sentence) {
        List<String> result = new ArrayList<>();

        Process process = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"python", "-c", SCRIPT, sentence});
            reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            process.waitFor();
        } catch (IOException e) {
            LOGGER.error("IOException during exec tokenization.", e);
            throw new TranslatorException(ErrorCode.INTERNAL_ERROR, e);
        } catch (InterruptedException e) {
            LOGGER.error("Interrupted during exec tokenization.", e);
            throw new TranslatorException(ErrorCode.INTERNAL_ERROR, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("IOException during close reade.", e);
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
        LOGGER.debug("token size: {}", result.size());
        return result;
    }
}
