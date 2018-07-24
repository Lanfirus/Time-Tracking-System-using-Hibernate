package ua.training.tts.model.util;

import ua.training.tts.model.entity.Project;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusEnumConverter implements AttributeConverter<Project.Status, String>{

    @Override
    public String convertToDatabaseColumn(Project.Status attribute) {
        return attribute.toString().toLowerCase();
    }

    @Override
    public Project.Status convertToEntityAttribute(String dbData) {
        return Project.Status.valueOf(dbData.toUpperCase());
    }
}
