package com.sales.laboratorio12.services;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UseOfStrings {

    public void copyReversedStanzas(String cancion, OutputStream out) throws IOException {
        String invertedSong = reverseStanzas(cancion);
        StreamUtils.copy(invertedSong, StandardCharsets.UTF_8, out);
    }

    private String reverseStanzas(String str) {
        StringBuilder stanzas = new StringBuilder();
        StringBuilder song = new StringBuilder();
        Scanner in = new Scanner(str);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals("")) {
                stanzas.append("\n");
                song.insert(0, stanzas);
                stanzas = new StringBuilder();
            } else
                stanzas.append(line).append("\n");
        }
        stanzas.append("\n");
        song.insert(0, stanzas);
        return song.toString();
    }

    public String getFileString(InputStream input) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer, "UTF-8");
        return writer.toString();
    }

    public int countStanzas(String str) {
        int count = 0;
        Scanner in = new Scanner(str);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.equals("")) count++;
        }
        return count;
    }

    public String getMostRepeatedWord(String str, OutputStream out) throws IOException {
        Map<String, Integer> occurrences = new HashMap<>();
        String[] splitWords = str.split("[\\s,]+");
        for (String Word : splitWords) {
            Integer oldCount = occurrences.get(Word);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurrences.put(Word, oldCount + 1);
        }
        Integer value = occurrences.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getValue();
        String key = occurrences.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();

        String line = "Palabra: " + key + " Repeticion: " + value;
        StreamUtils.copy(line, StandardCharsets.UTF_8, out);
        return key;
    }

    public void replaceWordWith_YOU(InputStream in, String song, OutputStream out) throws IOException {
        String str = getFileString(in);
        String[] splitWords = str.split("[\\s,]+");
        String word = splitWords[1];
        String song_replace = song.replace(word, "you");
        StreamUtils.copy(song_replace, StandardCharsets.UTF_8, out);
    }
}

