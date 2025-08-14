## 使用方法

1. `pom.xml`引入依赖

   ```xml
   <dependency>
       <groupId>io.github.coreqaq</groupId>
       <artifactId>coreapi-sdk</artifactId>
       <version>0.0.2</version>
   </dependency>
   ```

2. 示例

   ```java
   import coreapi.client.CoreApiClient;
   import coreapi.exception.ApiException;
   import coreapi.model.entity.IpDetail;
   
   public class Demo {
       public static void main(String[] args) {
           String ak = "c8xxxxxxxxxxxxxxxxx8e";
           String sk = "e6xxxxxxxxxxxxxxxxxxxxc9";
           CoreApiClient client = new CoreApiClient(ak, sk);
   
           try {
               IpDetail ipDetail = client.getIpDetail("112.21.20.238");
               System.out.println(ipDetail);
           } catch (ApiException e) {
               e.printStackTrace();
           }
       }
   }
   
   ```



具体文档：http://1.15.122.131:8000/docs