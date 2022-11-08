# Spring boot with Redis
## window os에 redis 실행
1. Redis for window(.msi) 설치
2. [redis.windows.conf] 설정파일에서 포트, 패스워드 등 설정
3. Redis 실행
![image](https://user-images.githubusercontent.com/101205543/200497773-2f3960ad-84a7-4aaa-8044-0e4d4f7b4bda.png)

## Spring boot 프로젝트에 Redis 설정
1. build.gradle Redis 라이브러리 추가
```groovy
configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-redis'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
}
```
2. 스타터 클래스에 @EnableCaching 적용
- Redis Cache를 사용하기 위한 어노테이션 지정
```java
@SpringBootApplication
@EnableCaching
public class RedisSampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisSampleApplication.class, args);
	}
}
```

3. Redis 설정 커스터마이징
- Redis 서버에 연결할 호스트, 포트 등 
```java
@Configuration
public class RedisConfig {
  
  @Value("${spring.redis.port}")
  public int port;
  
  @Value("${spring.redis.host}")
  public String host;
  
  @Value("${spring.redis.password}")
  public String password;
  
  @Autowired
  public ObjectMapper objectMapper;
  
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setConnectionFactory(connectionFactory);
    return redisTemplate;
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(port);
    redisStandaloneConfiguration.setPassword(password);
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
    return connectionFactory;
  }
}
```

4. API + Redis Cache 테스트
값 저장

![image](https://user-images.githubusercontent.com/101205543/200498273-2efe594a-5535-4d38-b1cb-a96a83a3cc2c.png)
![image](https://user-images.githubusercontent.com/101205543/200498121-8ad08005-a508-4cca-b5bd-dfbeb9715c07.png)

값 조회
![image](https://user-images.githubusercontent.com/101205543/200497990-287aacf8-0996-420b-8bf5-6b27bf1e733d.png)

