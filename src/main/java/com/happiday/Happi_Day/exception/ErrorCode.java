package com.happiday.Happi_Day.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public enum ErrorCode {
    NO_SUCH_ELEMENT(HttpStatus.NO_CONTENT, "해당 컨텐츠를 찾을 수 없습니다."),
    MOVED_PERMANENT(HttpStatus.MOVED_PERMANENTLY, "URI가 변경되었습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 컨텐츠가 존재하지 않습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 접근 방식입니다."),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "접근 요청시간이 만료되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부적인 서버 에러가 발생했습니다."),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "구현되지 않은 기능입니다."),

    // s3 file uploader
    FILE_DELETE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "S3 이미지 삭제에 실패하였습니다."),
    FILE_UPLOAD_BAD_REQUEST(HttpStatus.BAD_REQUEST, "S3 이미지 업로드에 실패하였습니다."),
    FILE_MISSING_EXTENSION(HttpStatus.BAD_REQUEST, "파일에 확장자가 없습니다."),
    FILE_INVALID_EXTENSION(HttpStatus.BAD_REQUEST, "잘못된 파일 확장자입니다. 이미지만 허용됩니다."),

    // artist
    ARTIST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 아티스트를 찾을 수 없습니다."),

    // team
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 팀을 찾을 수 없습니다."),

    // event
    EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이벤트를 찾을 수 없습니다."),
    EVENT_ALREADY_DELETED(HttpStatus.NOT_FOUND, "삭제된 이벤트입니다."),
    EVENT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이벤트 댓글을 찾을 수 없습니다."),
    EVENT_COMMENT_ALREADY_DELETED(HttpStatus.NOT_FOUND, "삭제된 댓글입니다."),

    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    USER_CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 유저입니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // chat
    CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 채팅방입니다."),
    CHATROOM_NOT_CREATED(HttpStatus.BAD_REQUEST, "채팅방을 만들 수 없습니다."),

    // article
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 글입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),

    // sales
    SALES_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 판매글입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 옵션입니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 주문입니다."),
    ORDER_FAILED(HttpStatus.BAD_REQUEST, "취소할 수 없는 주문입니다."),
    ORDER_CANT_DELETE(HttpStatus.UNAUTHORIZED, "삭제할 수 없는 주문입니다."),
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 배달방법입니다."),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "해당 상품은 품절입니다.")
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}