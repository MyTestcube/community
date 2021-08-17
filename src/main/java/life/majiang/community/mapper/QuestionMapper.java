package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    List<Question> listByUserId(@Param(value = "userId") Integer userId,@Param(value="offset") Integer offset, @Param(value="size")Integer size);

    @Select("select count(1) from question while creator=#{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);
}
