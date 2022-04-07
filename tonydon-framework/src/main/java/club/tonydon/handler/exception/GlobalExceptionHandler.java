package club.tonydon.handler.exception;

import club.tonydon.domain.ResponseResult;
import club.tonydon.enums.HttpCodeEnum;
import club.tonydon.exception.NoFilenameException;
import club.tonydon.exception.NoIdException;
import club.tonydon.exception.SystemException;;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult<Object> doSystemException(SystemException e){
        e.printStackTrace();
        // 从异常对象中获取信息并返回
        return ResponseResult.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<Object> doException(Exception e){
        e.printStackTrace();
        return ResponseResult.error(HttpCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoIdException.class)
    public ResponseResult<Object> deNoIdException(NoIdException e){
        return ResponseResult.error(HttpCodeEnum.NO_ID_ERROR);
    }

    @ExceptionHandler(NoFilenameException.class)
    public ResponseResult<Object> deNoFilenameException(NoFilenameException e){
        return ResponseResult.error(HttpCodeEnum.NO_FILENAME_ERROR);
    }

}
