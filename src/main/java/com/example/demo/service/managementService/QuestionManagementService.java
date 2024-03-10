package com.example.demo.service.managementService;

import com.example.demo.dto.QuestionDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionForm;

import java.util.List;

public interface QuestionManagementService {
    public QuestionDto singleQuestion(TopicDto topicDto);
    List<Question> getQuestionsList();
    void addQuestion(QuestionForm questionF);

}
