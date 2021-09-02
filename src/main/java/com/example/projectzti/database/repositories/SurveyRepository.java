package com.example.projectzti.database.repositories;

import com.example.projectzti.database.models.Survey;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
/**
 * Spring Data Survey repository
 */
public interface SurveyRepository extends CrudRepository<Survey, UUID> {
}
