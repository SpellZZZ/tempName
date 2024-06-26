package com.example.demo.controller;



import com.example.demo.dto.QuestionDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.model.Question;
import com.example.demo.dto.QuestionFormDto;
import com.example.demo.model.Topic;

import com.example.demo.service.managementService.QuestionManagementService;
import com.example.demo.service.managementService.TopicManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {


    private final QuestionManagementService questionManagementService;
    final private TopicManagementService topicManagementService;

    @Autowired
    public MainController(QuestionManagementService questionManagementService,
                   TopicManagementService topicManagementService
    ){
        this.questionManagementService = questionManagementService;
        this.topicManagementService = topicManagementService;
    }


    @GetMapping(value = "/allQuestions")
    public List<Question> allQuestions(){
        return questionManagementService.getQuestionsList();
    }


    @PostMapping(value = "/addQuestion")
    public void addQuestion(@RequestBody QuestionFormDto questionF){
        questionManagementService.addQuestion(questionF);
    }

    @PostMapping(value = "/singleQuestion")
    public QuestionDto singleQuestion(@RequestBody TopicDto topicDto) {
        return questionManagementService.singleQuestion(topicDto);
    }


    @GetMapping(value = "/allTopics")
    public List<String> allTopics(){

        List<Topic> topics = topicManagementService.getAllTopics();
        List<String> topicNames = topicManagementService.getOnlyTopicsNames(topics);

        return topicNames;
    }

    @PostMapping(value = "/addTopic")
    public void addTopics(@RequestBody String newTopic){
        try{
            topicManagementService.createTopic(newTopic);
        } catch(Exception ignored){

        }

    }


}
