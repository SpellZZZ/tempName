package com.example.demo;

import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.TopicDao;
import com.example.demo.dto.QuestionDto;
import com.example.demo.dto.QuestionFormDto;
import com.example.demo.dto.TopicDto;
import com.example.demo.model.Question;
import com.example.demo.model.Topic;

import com.example.demo.service.dbService.QuestionService;
import com.example.demo.service.dbService.QuestionServiceImpl;
import com.example.demo.service.dbService.TopicService;
import com.example.demo.service.dbService.TopicServiceImpl;
import com.example.demo.service.managementService.QuestionManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class QuestionManagementServiceImplTest {

    @Mock
    private QuestionDao questionDao;
    private QuestionServiceImpl questionService;
    @Mock
    private TopicDao topicDao;
    private TopicServiceImpl topicService;


    @InjectMocks
    private QuestionManagementServiceImpl questionManagementService;

    @BeforeEach
    public void setUp() {
        // Tworzymy instancję QuestionServiceImpl z mockiem QuestionDao
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionServiceImpl(questionDao);
        topicService = new TopicServiceImpl(topicDao);
        questionManagementService = new QuestionManagementServiceImpl(questionService, topicService );
    }


    @Test
    public void testGetQuestionsList() {
        Question question = new Question(); // przykładowy obiekt pytania
        List<Question> expectedQuestions = new ArrayList<>();
        //doNothing().
        when(questionService.listQuestions()).thenReturn(expectedQuestions);

        List<Question> result = questionManagementService.getQuestionsList();

        assertEquals(expectedQuestions, result);
    }


    @Test
    public void testGetTopicsFromList() {
        List<String> topicStringList = new ArrayList<>();
        List<Topic> expectedTopics = new ArrayList<>();
        when(topicService.getTopicsByNames(topicStringList)).thenReturn(expectedTopics);

        List<Topic> result = questionManagementService.getTopicsFromList(topicStringList);

        assertEquals(expectedTopics, result);
    }

    @Test
    public void testAddQuestion() {
        QuestionFormDto questionFormDto = new QuestionFormDto();
        List<String> topicStringList = new ArrayList<>();
        questionFormDto.setQuestion("Test Question");
        questionFormDto.setAnswer("Test Answer");
        questionFormDto.setTopics(topicStringList);

        questionManagementService.addQuestion(questionFormDto);

        verify(topicService).getTopicsByNames(topicStringList);
        verify(questionService).addQuestion(any(Question.class));
    }

    @Test
    public void testSingleQuestion() {

        List<Topic> topicList = new ArrayList<>() {
            {
                add(new Topic("test"));
            }
        };
        List<String> topicListString = new ArrayList<>() {
            {
                add("test");
            }
        };

        TopicDto topicDto = new TopicDto();
        topicDto.setTopics(topicListString);

        List<Question> questionList = new ArrayList<>(){
            {
                new Question("asd", "asd", topicList);
            }
        };

        when(questionService.findQuestionsByTopicNames(topicListString)).thenReturn(questionList);
        QuestionDto result = questionManagementService.singleQuestion(topicDto);

        assertEquals("Question 1", result.getQuestion());
        assertEquals("Answer 1", result.getAnswer());
    }
}
