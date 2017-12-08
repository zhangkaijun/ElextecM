package com.elextec.mdm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.elextec.mdm.entity.User;

public interface UserMapper {
	
	@Select("SELECT * FROM user WHERE user_name = #{userName}")
	@Results(id="userMap",
	value={
		@Result(id = true, property = "id", column = "id"),
		@Result(property = "userName",  column = "user_name"),
        @Result(property = "userPassword", column = "user_password"),
        @Result(property = "fullName", column = "full_name"),
        @Result(property = "status", column = "status"),
		@Result(property = "createTime", column = "create_time"),
        @Result(property = "creater", column = "creater"),
        @Result(property = "department", column = "department_id",
    		one = @One(select = "com.elextec.mdm.mapper.DepartmentMapper.findDepartmentById")
        		),
        @Result(property = "roles", column = "id",
        	many = @Many(select = "com.elextec.mdm.mapper.RoleMapper.findRolesByUserId")
        		)
    })
    List<User> findUserByName(String userName);
	
	@Select("SELECT * FROM user")
	@ResultMap("userMap")
    List<User> findAll();
	
	@Select("SELECT * FROM user WHERE id = #{userId}")
	@ResultMap("userMap")
	User findUserById(int userId);

    @Insert("INSERT INTO user(user_name,user_password,full_name,department_id,status,creater)"
    		+ " VALUES(#{userName}, #{userPassword}, #{fullName}, #{department.id}, #{status}, #{creater})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @ResultMap("userMap")
    void insert(User user);

    @Update("UPDATE user SET full_name=#{fullName},user_password=#{userPassword},"
    		+ "department_id=#{department.id},status=#{status} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);
    
    @Update("${sql}")
    void createTable(@Param("sql") String sql);
    
    /**
     * 新增用户的角色信息
     * @param user
     */
    @InsertProvider(type = MapperProvider.class,method = "addUserRoles")
    void addUserRoles(@Param("user")User user);
    
    /**
     * 根据用户ID，删除用户的角色信息
     * @param userId
     */
    @Delete("DELETE FROM user_role WHERE user_id=#{userId}")
    void delUserRoles(int userId);
    
}