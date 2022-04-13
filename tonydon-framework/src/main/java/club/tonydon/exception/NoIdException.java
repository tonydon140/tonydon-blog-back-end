package club.tonydon.exception;

import club.tonydon.enums.HttpCodeEnum;

/**
 * id 未找到异常
 */
public class NoIdException extends SystemException {
    public NoIdException() {
        super(HttpCodeEnum.NO_ID_ERROR);
    }
}
