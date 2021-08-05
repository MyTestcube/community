package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//ctrl + alt + o自动移除不必要的import

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);




}
