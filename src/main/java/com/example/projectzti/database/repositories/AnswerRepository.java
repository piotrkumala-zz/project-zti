package com.example.projectzti.database.repositories;

import com.example.projectzti.database.models.Answer;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AnswerRepository extends CrudRepository<Answer, UUID> {
}
