package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("你找到的问题不在了，要不要换个试一试？");

    @Override
    public String getMessage(){
        return message;
    }
    private String message;

    CustomizeErrorCode(String message){
        this.message=message;
    }
}
