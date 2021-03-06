package com.elextec.mdm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.elextec.mdm.entity.MdmModel;

public interface MdmModelMapper {

	@Insert("INSERT INTO mdm_model(id,mdm_model,status,creater,create_time)"
		    + " VALUES(sys_guid(), #{mdmModel}, #{status}, #{creater}, sysdate)")
	void insert(MdmModel model);
	
	@Delete("DELETE FROM mdm_model WHERE id = #{id}")
	void del(String id);
	
	@Select("SELECT * FROM mdm_model")
    @Results(id = "modelMap",
    	value = { 
	    @Result(id = true, property = "id", column = "id"),
	    @Result(property = "mdmModel", column = "mdm_model"),
	    @Result(property = "status", column = "status"),
	    @Result(property = "createTime", column = "create_time"),
	    @Result(property = "creater", column = "creater") 
	})
	List<MdmModel> findAll();
	
	@Select("SELECT * FROM mdm_model WHERE id = #{id}")
	@ResultMap("modelMap")
	MdmModel findById(String id);
}
