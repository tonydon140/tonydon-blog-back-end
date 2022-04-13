package club.tonydon.exception;

import club.tonydon.enums.HttpCodeEnum;

/**
 * 没有文件名异常
 */
public class NoFilenameException extends SystemException {
    public NoFilenameException(){
        super(HttpCodeEnum.NO_FILENAME_ERROR);
    }
}
