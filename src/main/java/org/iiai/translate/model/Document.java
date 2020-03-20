package org.iiai.translate.model;

import org.iiai.translate.constant.SpecSymbolType;
import org.iiai.translate.filter.RegexFilter;
import org.iiai.translate.filter.SeparatorFilter;
import org.iiai.translate.model.type.SingleSentType;
import org.iiai.translate.model.type.SpecSymbol;
import org.iiai.translate.util.SentenceUtil;
import org.iiai.translate.util.TokenizeUtil;

import java.util.*;

public class Document {
    private static final List<SeparatorFilter> sepFilterList = new ArrayList<>();

    static {
        sepFilterList.add(new RegexFilter("[\r\n\f]+"));
        sepFilterList.add(new RegexFilter("\n*(-( *)){2,}\n*"));
    }

    private String content;

    private List<Sentence> sentenceList;

    public Document(String content) {
        this.content = content;
        List<Separator> separatorList = getSeparatorList(content);
        splitDocument(separatorList);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Sentence> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<Sentence> sentenceList) {
        this.sentenceList = sentenceList;
    }

    public List<Sentence> getSentenceByType(Class className) {
        List<Sentence> result = new ArrayList<>();
        this.sentenceList.forEach(sentence -> {
            if (sentence.getType().getClass().equals(className)) {
                result.add(sentence);
            }
        });
        return result;
    }

    public String getTranslation(List<String> transList) {
        Deque<String> transDeque = new LinkedList<>(transList);
        StringBuffer sb = new StringBuffer();
        sentenceList.forEach(sentence -> {
            if (sentence.getType() instanceof SingleSentType) {
                sb.append(transDeque.poll());
            } else {
                sb.append(sentence.getSentContent());
            }
        });
        return sb.toString();
    }

    private List<Separator> getSeparatorList(String content) {
        Set<Separator> separatorSet = new HashSet<>();
        sepFilterList.forEach(separatorFilter -> separatorSet.addAll(separatorFilter.getSeparators(content)));
        List<Separator> separatorList = SentenceUtil.mergeSeparator(new ArrayList<>(separatorSet), content);
        return separatorList;
    }

    private void splitDocument(List<Separator> separatorList) {
        List<Sentence> sentenceList = new ArrayList<>();

        int sentIndex = 0;
        for (Separator separator :  separatorList) {
            String sentContent = this.content.substring(sentIndex, separator.getStart()).trim();
            tokenizeSentence(sentContent, sentenceList);
            sentenceList.add(new Sentence(separator.getContent(), new SpecSymbol(SpecSymbolType.SEPARATOR)));
            sentIndex = separator.getEnd();
        }
        String lastContent = this.content.substring(sentIndex).trim();
        tokenizeSentence(lastContent, sentenceList);
        this.sentenceList = sentenceList;
    }

    private void tokenizeSentence(String sentence, List<Sentence> sentenceList) {
        if (sentence.length() > 0) {
            List<String> tokenizedList = TokenizeUtil.getTokenizedSents(sentence);
            tokenizedList.forEach(sent -> {
                if (sent.length() > 0) {
                    sentenceList.add(new Sentence(sent, new SingleSentType()));
                }
            });
        }
    }
}
