package com.example.projectzti.database.repositories;

import com.example.projectzti.database.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
