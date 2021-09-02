package com.example.projectzti.shared;

import java.util.Set;

/**
 * Client request to create new Survey
 */
public class CreateSurveyRequest {
    public String title;
    public String description;
    public Set<CreateSurveyQuestion> questions;
}
