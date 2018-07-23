package ua.training.tts.model.util;

import ua.training.tts.model.entity.Employee;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EnumConverter implements AttributeConverter<Employee.AccountRole, String>{

    @Override
    public String convertToDatabaseColumn(Employee.AccountRole attribute) {
        System.out.println("in converter Enum " + attribute);
        return attribute.toString().toLowerCase();
    }

    @Override
    public Employee.AccountRole convertToEntityAttribute(String dbData) {
        System.out.println("in converter String " + dbData);
        return Employee.AccountRole.valueOf(dbData.toUpperCase());
    }
}
