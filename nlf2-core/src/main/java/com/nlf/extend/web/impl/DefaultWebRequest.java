package com.nlf.extend.web.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.Client;
import com.nlf.core.IFileUploader;
import com.nlf.core.ISession;
import com.nlf.core.Statics;
import com.nlf.core.UploadFile;
import com.nlf.extend.serialize.obj.OBJ;
import com.nlf.extend.web.AbstractWebRequest;
import com.nlf.extend.web.AbstractWebSession;
import com.nlf.extend.web.IWebFileUploader;
import com.nlf.extend.web.WebStatics;

/**
 * 默认WEB请求
 * 
 * @author 6tail
 *
 */
public class DefaultWebRequest extends AbstractWebRequest{
  /** 代理标识 */
  public static final String[] PROXY_HEADER = {"X-Real-IP","X-Forwarded-For","Proxy-Client-IP","WL-Proxy-Client-IP","HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR"};

  protected String getIP(){
    String r = servletRequest.getRemoteAddr();
    Enumeration<String> em = servletRequest.getHeaderNames();
    out:while(em.hasMoreElements()){
      String k = em.nextElement();
      for(String s:PROXY_HEADER){
        if(s.equalsIgnoreCase(k)){
          String p = servletRequest.getHeader(k);
          if(null!=p&&p.length()>0&&!"unknown".equalsIgnoreCase(p)){
            r = p;
            break out;
          }
        }
      }
    }
    if(null!=r){
      if(r.indexOf(",")>-1){
        String[] rs = r.split(",");
        for(String s:rs){
          if(s.length()>0&&!"unknown".equalsIgnoreCase(s)){
            r = s;
            break;
          }
        }
      }
      if("0:0:0:0:0:0:0:1".equals(r)){
        r = "127.0.0.1";
      }
    }
    if(null==r) r = "";
    return r;
  }

  public void init(){
    try{
      initParam();
    }catch(UnsupportedEncodingException e){
      throw new RuntimeException(e);
    }
    String contentType = servletRequest.getContentType();
    if(null!=contentType&&contentType.contains("multipart/form-data")){
      IFileUploader uploader = App.getProxy().newInstance(IWebFileUploader.class.getName());
      List<UploadFile> files = uploader.getFiles();
      param.set(Statics.PARAM_FILES,files);
    }
    initPaging();
  }

  protected void initParam() throws UnsupportedEncodingException{
    // 获取AJAX请求标识
    String headAjax = servletRequest.getHeader("x-requested-with");
    // 请求方式：GET、POST等
    String reqMethod = servletRequest.getMethod();
    Enumeration<String> en = servletRequest.getParameterNames();
    while(en.hasMoreElements()){
      String key = en.nextElement();
      String value = servletRequest.getParameter(key);
      String[] values = servletRequest.getParameterValues(key);
      if(null==value){
        value = "";
      }
      if(null==values){
        values = new String[]{};
      }
      if(null==headAjax){
        if("GET".equalsIgnoreCase(reqMethod)){
          value = new String(value.getBytes("ISO-8859-1"),Statics.ENCODE);
          for(int i=0,j=values.length;i<j;i++){
            values[i] = new String(values[i].getBytes("ISO-8859-1"),Statics.ENCODE);
          }
        }
      }else{
        value = URLDecoder.decode(value,Statics.ENCODE);
        for(int i=0,j=values.length;i<j;i++){
          values[i] = URLDecoder.decode(values[i],Statics.ENCODE);
        }
      }
      param.set(key,value);
      if(values.length>1){
        param.set(key,values);
      }
    }
  }

  protected void initPaging(){
    String s = param.getString(Statics.PARAM_PAGE_PARAM);
    Bean pageParam = null;
    try{
      pageParam = OBJ.toBean(s);
    }catch(Exception e){
      pageParam = new Bean();
    }
    for(String key:param.keySet()){
      if(Statics.PARAM_PAGE_PARAM.equals(key)||Statics.PARAM_FILES.equals(key)) continue;
      pageParam.set(key,param.get(key));
    }
    String uri = servletRequest.getServletPath();
    pageParam.set(WebStatics.PARAM_PAGE_URI,uri);
    int pageNumber = param.getInt(Statics.PARAM_PAGE_NUMBER,this.pageNumber);
    int pageSize = param.getInt(Statics.PARAM_PAGE_SIZE,this.pageSize);
    pageParam.set(Statics.PARAM_PAGE_NUMBER,pageNumber);
    pageParam.set(Statics.PARAM_PAGE_SIZE,pageSize);
    setPageNumber(pageNumber);
    setPageSize(pageSize);
    param.set(Statics.PARAM_PAGE_PARAM,OBJ.fromObject(pageParam));
  }

  public Client getClient(){
    if(null==client){
      client = new Client();
      client.setIp(getIP());
      client.setLocale(servletRequest.getLocale());
    }
    return client;
  }

  public ISession getSession(){
    if(null==session){
      boolean autoCreate = Boolean.parseBoolean(App.getProperty("nlf.session.auto_create"));
      AbstractWebSession webSession = new DefaultWebSession();
      webSession.setSession(servletRequest.getSession(autoCreate));
      session = webSession;
    }
    return session;
  }

  public List<UploadFile> getFiles(){
    List<UploadFile> files = param.get(Statics.PARAM_FILES);
    return null==files?new ArrayList<UploadFile>(0):files;
  }
}