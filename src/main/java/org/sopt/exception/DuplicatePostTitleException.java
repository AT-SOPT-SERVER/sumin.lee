package org.sopt.exception;

public class DuplicatePostTitleException extends RuntimeException{
    public DuplicatePostTitleException(){
        super("중복된 제목의 게시물이 존재합니다.");
    }
}
