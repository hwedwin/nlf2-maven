package com.nlf.bytecode.constant;

/**
 * 常量-MethodType
 * 
 * @author 6tail
 *
 */
public class MethodTypeConstant extends AbstractConstant{
  private int descriptorIndex;

  public int getDescriptorIndex() {
    return descriptorIndex;
  }

  public void setDescriptorIndex(int descriptorIndex) {
    this.descriptorIndex = descriptorIndex;
  }

  public MethodTypeConstant toMethodTypeConstant(){
    return this;
  }
}