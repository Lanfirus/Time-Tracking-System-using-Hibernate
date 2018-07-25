package ua.training.tts.model.util;

import ua.training.tts.model.entity.Task;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TaskStatusEnumConverter implements AttributeConverter<Task.Status, String>{

    @Override
    public String convertToDatabaseColumn(Task.Status attribute) {
        return attribute.toString().toLowerCase();
    }

    @Override
    public Task.Status convertToEntityAttribute(String dbData) {
        return Task.Status.valueOf(dbData.toUpperCase());
    }
}
