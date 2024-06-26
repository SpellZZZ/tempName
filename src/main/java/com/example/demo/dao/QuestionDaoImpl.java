package com.example.demo.dao;


import com.example.demo.dto.QuestionDto;
import com.example.demo.model.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {


    private static final Logger logger = LoggerFactory.getLogger(QuestionDaoImpl.class);

    final private SessionFactory sessionFactory;

    @Autowired
    public QuestionDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addQuestion(Question q) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(q);
        logger.info("Question saved successfully");
    }
    @Override
    public void updateQuestion(Question q) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(q);
        logger.info("Question updated successfully");
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<Question> listQuestions() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Question> personsList = session.createQuery("from Question").list();
        for(Question q : personsList){
            logger.info("Question List::"+q);
        }
        return personsList;
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<QuestionDto> listQuestionsQA() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Object[]> questionAnswerList = session.createQuery("select q.question, q.answer from Question q").list();

        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Object[] qa : questionAnswerList) {
            QuestionDto questionDTO = new QuestionDto();
            questionDTO.setQuestion((String) qa[0]);
            questionDTO.setAnswer((String) qa[1]);
            questionDtoList.add(questionDTO);

            logger.info("Question: " + questionDTO.getQuestion() + ", Answer: " + questionDTO.getAnswer());
        }

        return questionDtoList;
    }
    @Override
    public Question getQuestionById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Question p = (Question) session.load(Question.class, id);
        logger.info("Question loaded successfully");
        return p;
    }
    @Override
    public void removeQuestion(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Question p = (Question) session.load(Question.class, id);
        if(null != p){
            session.delete(p);
        }
        logger.info("Question deleted successfully");
    }



    @Override
    public List<Question> findQuestionsByTopicNames(List<String> topicNames) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT q FROM Question q JOIN q.topics t WHERE t.topicName IN :topicNames";
            Query<Question> query = session.createQuery(hql, Question.class);
            query.setParameterList("topicNames", topicNames);
            return query.getResultList();
        }
    }
}
