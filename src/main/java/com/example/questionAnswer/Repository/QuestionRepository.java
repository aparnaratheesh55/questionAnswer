package com.example.questionAnswer.Repository;

import com.example.questionAnswer.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

}
