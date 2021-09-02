package com.example.projectzti.database.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;


/**
 * Basic database entity metadata
 */
@MappedSuperclass
public class Metadata {

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
