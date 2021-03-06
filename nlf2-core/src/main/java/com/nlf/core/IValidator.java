package com.nlf.core;

import com.nlf.exception.ValidateException;

/**
 * 验证器接口
 * 
 * @author 6tail
 *
 */
public interface IValidator{
  /** 0到9的数字 */
  String NUMBER = "number";
  /** 整数 */
  String INTEGER = "integer";
  /** 浮点数 */
  String DECIMAL = "decimal";
  /** 字母 */
  String LETTER = "letter";
  /** 大写字母 */
  String LETTER_UPPER = "letter_upper";
  /** 小写字母 */
  String LETTER_LOWER = "letter_lower";
  /** 电子邮件地址 */
  String EMAIL = "email";
  /** 手机号码 */
  String MOBILE = "mobile";
  /** 身份证号码 */
  String ID_CARD = "id_card";
  /** 正整数 */
  String INTEGER_POSITIVE = "integer_positive";
  /** 负整数 */
  String INTEGER_NEGTIVE = "integer_negtive";
  /** 正浮点数 */
  String DECIMAL_POSITIVE = "decimal_positive";
  /** 负浮点数 */
  String DECIMAL_NEGTIVE = "decimal_negtive";
  /** 空 */
  String EMPTY = "empty";
  /** 不能为空 */
  String NOT_EMPTY = "not_empty";
  /** 等于 */
  String IS = "is";
  /** 不等于 */
  String NOT = "not";
  /** 包含 */
  String CONTAINS = "contains";
  /** 不含 */
  String NOT_CONTAINS = "not_contains";
  /** 结尾是 */
  String END_WITH = "end_with";
  /** 开头是 */
  String START_WITH = "start_with";
  /** 结尾不是 */
  String NOT_END_WITH = "not_end_with";
  /** 开头不是 */
  String NOT_START_WITH = "not_start_with";
  /** 最小长度 */
  String MIN_LENGTH = "min_length";
  /** 最大长度 */
  String MAX_LENGTH = "max_length";
  /** 固定长度 */
  String FIX_LENGTH = "fix_length";
  /** 被包含 */
  String IN = "in";
  /** 不被包含 */
  String NOT_IN = "not_in";
  /** 自定义正则表达式 */
  String REGEX = "regex";
  /** 验证规则分隔符：或 */
  String TAG_OR = "||";
  /** 验证规则分隔符：且 */
  String TAG_AND = "&&";

  /**
   * 数据验证
   * 
   * @param name 数据项名称
   * @param value 值
   * @param rules 验证规则，多个规则之间使用分隔符
   * @throws ValidateException 验证失败异常
   */
  void validate(String name,String value,String rules) throws ValidateException;
}