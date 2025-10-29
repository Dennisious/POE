/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * QuickChatForm.java
 * Final PoE Project — ST10500239
 * Author: [Dennisious Moima]
 * Description: GUI app for sending, storing, and managing chat messages.
 */
// JSON reading implemented with ChatGPT guidance (OpenAI, 2025)

package com.mycompany.mavenproject1;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class MessageStore {
    private static final String FILE_PATH = "stored_messages.json";

    // Save messages to JSON file
    public static void saveMessages(List<Message> messages) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load messages from JSON file
    public static List<Message> loadMessages() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<Message>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
}
