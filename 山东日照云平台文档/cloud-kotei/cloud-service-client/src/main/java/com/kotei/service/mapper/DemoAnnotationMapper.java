package com.kotei.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kotei.entity.Demo;
@Mapper
public interface DemoAnnotationMapper {
	@Select({"select * from welfare_banner"})
	List<Demo> findAll();

}
