package ua.training.tts.model.dao;

import ua.training.tts.model.entity.Project;

import java.util.List;

public interface ProjectDao extends GeneralDao<Project, Integer> {

    List<Project> findAllActive();

    List<Project> findAllByStatus(String status);

    List<Project> findAllArchived();

}
