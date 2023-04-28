package api.jpa.practice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //404 NOT_FOUND 잘못된 리소스 접근
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_FOUND_CONTAINER(HttpStatus.NOT_FOUND, "존재하지 않는 컨테이너입니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "존재하지 않는 포스트입니다."),
    NOT_FOUND_SHORT_CUT(HttpStatus.NOT_FOUND, "존재하지 않는 즐겨찾기 입니다."),

    CONFLICT_USERNAME(HttpStatus.CONFLICT, "이미 등록된 유저가 있습니다."),
    CONFLICT_CONTAINER(HttpStatus.CONFLICT, "같은 이름의 컨테이너가 있습니다."),
    CONFLICT_POST(HttpStatus.CONFLICT, "같은 이름의 포스트가 있습니다."),
    CONFLICT_SHORT_CUT(HttpStatus.CONFLICT, "같은 이름의 즐겨찾기가 있습니다."),

    NO_TARGET_USER(HttpStatus.NOT_FOUND, "삭제할 유저가 없습니다."),
    NO_TARGET_CONTAINER(HttpStatus.NOT_FOUND, "삭제할 컨테이너가 없습니다."),
    NO_TARGET_POST(HttpStatus.NOT_FOUND, "삭제할 포스트가 없습니다."),

    USER_HAVE_NOT_CONTAINER(HttpStatus.NOT_FOUND, "대상 유저가 컨테이너를 가지고 있지 않습니다."),
    CONTAINER_HAVE_NOT_POST(HttpStatus.NOT_FOUND, "대상 컨테이너가 포스트를 가지고 있지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
