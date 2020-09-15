# SpringBoot使用@Validation校验实体参数

> 我们在做前后端分离时，需要提供接口给前端，那么接口的字段实体校验就显得尤为重要了



+ 在需要实体校验的实体类前加上`@Validated @RequestBody `注解

+ 是校验的实体类的字段上加入相关的校验注解

  + | 注解         | 功能                                                         |
    | ------------ | ------------------------------------------------------------ |
    | @AssertFalse | 可以为null,如果不为null的话必须为false                       |
    | @AssertTrue  | 可以为null,如果不为null的话必须为true                        |
    | @DecimalMax  | 设置不能超过最大值                                           |
    | @DecimalMin  | 设置不能超过最小值                                           |
    | @Digits      | 设置必须是数字且数字整数的位数和小数的位数必须在指定范围内   |
    | @Future      | 日期必须在当前日期的未来                                     |
    | @Past        | 日期必须在当前日期的过去                                     |
    | @Max         | 最大不得超过此最大值                                         |
    | @Min         | 最大不得小于此最小值                                         |
    | @NotNull     | 不能为null，可以是空                                         |
    | @Null        | 必须为null                                                   |
    | @Pattern     | 必须满足指定的正则表达式                                     |
    | @Size        | 集合、数组、map等的size()值必须在指定范围内                  |
    | @Email       | 必须是email格式                                              |
    | @Length      | 长度必须在指定范围内                                         |
    | @NotBlank    | 字符串不能为null,字符串trim()后也不能等于“”                  |
    | @NotEmpty    | 不能为null，集合、数组、map等size()不能为0；字符串trim()后可以等于“” |
    | @Range       | 值必须在指定范围内                                           |
    | @URL         | 必须是一个URL                                                |

+ 实体校验异常捕获，在有`@RestControllerAdvice`注解的类里创建异常处理方法

  + ```java
    @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(value = MethodArgumentNotValidException.class)
        public Result handler(MethodArgumentNotValidException e){
            log.error("实体校验异常：============={}");
            BindingResult bindingResult = e.getBindingResult();
            ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
            return Result.fail(objectError.getDefaultMessage());
        }
    ```

## @NotEmpty和@NotBlank和@NotNull

+ @NotNull：不能为null，但可以为empty，用在基本类型上
+ @NotEmpty：不能为null，而且长度必须大于0，用在集合类上面
+ @NotBlank：只能作用在String上，不能为null

