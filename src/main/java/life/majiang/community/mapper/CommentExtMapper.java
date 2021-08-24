package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import life.majiang.community.model.CommentExample;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentExtMapper {

    int incCommentCount(Comment comment);
}