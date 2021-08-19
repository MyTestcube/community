package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag}")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value="offset") Integer offset, @Param(value="size")Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question while creator=#{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Integer userId,@Param(value="offset") Integer offset, @Param(value="size")Integer size);

    @Select("select count(1) from question while creator=#{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question while creator=#{userId} limit #{offset},#{size}")
    Question getById(@Param("id")Integer id);

    @Update("update question set title =#{title},description =#{description},gmt_modified=#{gmtModified},tag =#{tag}, where id#{id}")
    void update(Question question);
}
