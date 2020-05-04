package com.kotei.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kotei.entity.Demo;
@Mapper
public interface DemoXMLMapper {
	List<Demo> findAll();

}
