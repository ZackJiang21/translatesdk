package org.iiai.translate.model;

import org.iiai.translate.constant.SpecSymbolType;
import org.iiai.translate.filter.RegexFilter;
import org.iiai.translate.filter.SeparatorFilter;
import org.iiai.translate.model.type.SingleSentType;
import org.iiai.translate.model.type.SpecSymbol;
import org.iiai.translate.util.SentenceUtil;
import org.iiai.translate.util.TokenizeUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Document {
    private static final List<SeparatorFilter> sepFilterList = Arrays.asList(new SeparatorFilter[]{
            new RegexFilter("[\r\n\f]+"),
            new RegexFilter("\n*(-( *)){2,}\n*")
    });

    private String content;

    private List<Sentence> sentenceList;

    private List<String> transList;

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

    public List<String> getTransList() {
        return transList;
    }

    public void setTransList(List<String> transList) {
        this.transList = transList;
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

    public String getTranslation() {
        Deque<String> transDeque = new LinkedList<>(transList);
        StringBuffer sb = new StringBuffer();
        sentenceList.forEach(sentence -> {
            if (sentence.getType() instanceof SingleSentType) {
                sb.append(transDeque.poll());
            } else {
                sb.append(sentence.getSentContent());
            }
            sb.append(" ");
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
        splitBySpec(separatorList);

        List<Sentence> wordSentenceList = getSentenceByType(SingleSentType.class);
        List<String> sentContentList = wordSentenceList.stream().map(sentObj -> sentObj.getSentContent()).collect(Collectors.toList());
        List<List<String>> tokenizedList = TokenizeUtil.getBatchTokens(sentContentList);

        List<Sentence> result = new ArrayList<>();
        int index = 0;
        for (Sentence sentObj : sentenceList) {
            if (sentObj.getType() instanceof SpecSymbol) {
                result.add(sentObj);
            } else {
                List<String> tokenizedStrList = tokenizedList.get(index);
                tokenizedStrList.forEach(sent -> {
                    if (sent.length() > 0) {
                        result.add(new Sentence(sent, new SingleSentType()));
                    }
                });
                index++;
            }
        }

        this.sentenceList = result;
    }

    private void splitBySpec(List<Separator> separatorList) {
        List<Sentence> sentenceList = new ArrayList<>();

        int sentIndex = 0;
        for (Separator separator : separatorList) {
            String sentContent = this.content.substring(sentIndex, separator.getStart()).trim();
            sentenceList.add(new Sentence(sentContent, new SingleSentType()));
            sentenceList.add(new Sentence(separator.getContent(), new SpecSymbol(SpecSymbolType.SEPARATOR)));
            sentIndex = separator.getEnd();
        }
        String lastContent = this.content.substring(sentIndex).trim();
        sentenceList.add(new Sentence(lastContent, new SingleSentType()));
        this.sentenceList = sentenceList;
    }
}
