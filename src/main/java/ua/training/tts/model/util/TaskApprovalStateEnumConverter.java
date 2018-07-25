package ua.training.tts.model.util;

import ua.training.tts.model.entity.Task;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TaskApprovalStateEnumConverter implements AttributeConverter<Task.ApprovalState, String>{

    @Override
    public String convertToDatabaseColumn(Task.ApprovalState attribute) {
        return attribute.toString().toLowerCase();
    }

    @Override
    public Task.ApprovalState convertToEntityAttribute(String dbData) {
        return Task.ApprovalState.valueOf(dbData.toUpperCase());
    }
}
