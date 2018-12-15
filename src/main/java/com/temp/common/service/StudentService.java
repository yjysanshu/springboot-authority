package com.temp.common.service;

import com.temp.admin.dto.StudentDTO;
import com.temp.admin.dto.list.StudentListDTO;
import com.temp.common.mapper.StudentMapper;
import com.temp.common.model.entity.Student;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StudentService {

    @Autowired
    private StudentMapper mapper;

    @Autowired
    private Mapper mapperTrans;

    public List<StudentListDTO> getPageList(StudentDTO container) {
        Student studentSearch = mapperTrans.map(container, Student.class);
        Map<String, Object> map = new HashMap<>();
        map.put("student", studentSearch);
        map.put("page", container.getCurrentPage());
        map.put("size", container.getLimit());
        List<Student> studentList = mapper.queryPageList(map);
        List<StudentListDTO> list = new ArrayList<>();
        for (Student student : studentList) {
            StudentListDTO dto = mapperTrans.map(student, StudentListDTO.class);
            list.add(dto);
        }
        return list;
    }

    public Integer getTotal(StudentDTO container) {
        Student studentSearch = mapperTrans.map(container, Student.class);
        return mapper.queryCount(studentSearch);
    }

    public Integer save(StudentDTO container) {
        Student student;
        if (container.getId() != null) {
            student = mapper.queryOne(container.getId());
            if (student == null) {
               student = new Student();
            }
        } else {
            student = new Student();
        }
        student.setNumber(container.getNumber());
        student.setName(container.getName());
        student.setSex(container.getSex());
        student.setAge(container.getAge());
        student.setGrade(container.getGrade());
        if (container.getId() != null) {
            return mapper.update(student);
        } else {
            return mapper.add(student);
        }
    }

    public Integer delete(Integer id) {
        return mapper.delete(id);
    }
}