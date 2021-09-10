package com.sales.laboratorio12.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

@SpringBootTest
public class UseOfStringsTest {

    @Autowired
    private UseOfStrings useOfStrings;

    private final String original = "src/test/resources/files/original.txt";
    private final String inverso = "src/test/resources/files/estrofasEnOrdenInverso.txt";
    private final String statics = "src/test/resources/files/statics.txt";
    private final String finalOut = "src/test/resources/files/finaloutput.txt";

    @Test
    void copyStanzasReverseOrder() throws IOException {
        InputStream in = new FileInputStream(original);
        OutputStream out = new FileOutputStream(inverso);
        String song = useOfStrings.getFileString(in);
        useOfStrings.copyReversedStanzas(song,out);
        assertNotEquals(song, useOfStrings.getFileString(new FileInputStream(inverso)));
    }

    @Test
    void countStanzas() throws IOException {
        InputStream in = new FileInputStream(inverso);
        String song = useOfStrings.getFileString(in);
        int count = useOfStrings.countStanzas(song);
        assertEquals(17,count);
    }

    @Test
    void getMostRepeatedWord() throws IOException {
        InputStream in = new FileInputStream(original);
        OutputStream out = new FileOutputStream(statics);
        String str = useOfStrings.getFileString(in);
        String value = useOfStrings.getMostRepeatedWord(str,out);
        assertEquals("beggin'",value);
    }

    @Test
    void replaceGreatestOccurrence() throws IOException {
        InputStream in = new FileInputStream(statics);
        OutputStream out = new FileOutputStream(finalOut);
        InputStream inSong = new FileInputStream(inverso);
        String song = useOfStrings.getFileString(inSong);
        useOfStrings.replaceWordWith_YOU(in,song, out);

        String songFinal = useOfStrings.getFileString(new FileInputStream(finalOut));
        String value = useOfStrings.getMostRepeatedWord(songFinal,new FileOutputStream(statics));
        assertEquals("you",value);
    }
}
