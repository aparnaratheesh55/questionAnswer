package com.example.questionAnswer.Controller;


import com.example.questionAnswer.Model.Question;
import com.example.questionAnswer.Repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/questions")
public class JavaController {

    @Autowired
    public QuestionRepository questionRepository;


    @PostMapping("/post-questions")
    public ResponseEntity<String> uploadQuestions(@RequestParam("file") MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            List<Question> questions = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String questionText = parts[0].trim();
                List<String> answers = List.of(parts[1].split(","));

                Question question = new Question();
                question.setQuestion(questionText);
                question.setAnswers(answers);

                questions.add(question);
            }


            questionRepository.saveAll(questions);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
        }

        return ResponseEntity.ok("Questions uploaded successfully");
    }

    @GetMapping("/get-questions")
    public ResponseEntity<Question> getRandomQuestions(){
        List<Question> allQuestions = questionRepository.findAll();
        if(allQuestions.isEmpty()){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Question randomQuestion = allQuestions.get(new Random().nextInt(allQuestions.size()));
        return ResponseEntity.ok(randomQuestion);

    }
}
