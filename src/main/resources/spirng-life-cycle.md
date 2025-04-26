bean
- 자바 객체 중 하나
- 제어의 역전 (IoC)
- 스프링 컨테이너에 의해서 생성, 삭제, 소멸이 되는 것

스프링 컨테이너
- 실행을 하려고 하면 스프링 컨테이너를 만듦

예를 들면 @RestController 붙으면 제어의 역전 컨테이너 
> new 키워드로 생성하지 않아도 알아서 사용
> 어플리케이션이 실행되면 자동으로 사용할 수 있음
> 컴포넌트가 스프링 빈으로 등록됨? - RestController 타고 들어가보면 component 어노테이션 사용하고 있음 

일반 객체 (클래스 등)
> 사용하지 않으면 클래스명 비활성화되어있음
> new 키워드로 생성해줘야함

컴포넌트 스캔
@Service, @RestController 등 빈을 등록하는 클래스??

BeanFactory (팩토리패턴)
- 가장 기본적인 컨테이너

ApplicationContext
- 빈 팩토리를 확장 할 수 있는...

# ApplicationContext와 스프링 컨테이너

## 1. 스프링 컨테이너의 개념

스프링 컨테이너는 스프링 프레임워크의 핵심으로, 빈(Bean)의 생명주기를 관리하고 의존성 주입(DI)을 담당하는 핵심 컴포넌트입니다. 주요 역할은 다음과 같습니다:

1. 빈 객체의 생성, 구성, 조립
2. 의존성 관리 및 주입
3. 빈 생명주기 관리
4. AOP(Aspect-Oriented Programming) 지원
5. 이벤트 발행 및 구독 메커니즘 제공

스프링에서는 두 가지 유형의 컨테이너를 제공합니다:

1. **BeanFactory**: 가장 기본적인 컨테이너로, 빈의 등록과 생성, 의존성 주입 등 핵심 기능만 제공
2. **ApplicationContext**: BeanFactory를 확장하여 더 많은 엔터프라이즈 기능을 제공

## 2. ApplicationContext 란

ApplicationContext는 BeanFactory의 확장된 버전으로, 보다 풍부한 기능을 제공하는 인터페이스입니다. 대부분의 스프링 애플리케이션에서는 BeanFactory 대신 ApplicationContext를 사용합니다.

### 2.1 ApplicationContext의 주요 특징

1. **BeanFactory 기능 포함**: ApplicationContext는 BeanFactory 인터페이스를 확장하므로 모든 빈 관리 기능을 포함합니다.

2. **리소스 접근**: 파일 시스템, 클래스패스 등 다양한 위치에서 리소스를 로드할 수 있습니다.

3. **국제화(i18n) 지원**: 다국어 메시지 처리를 지원합니다.

4. **이벤트 발행**: 애플리케이션 이벤트를 발행하고 구독하는 기능을 제공합니다.

5. **환경 추상화**: 프로파일 및 속성을 통한 환경 구성을 지원합니다.

6. **AOP 기능 통합**: 관점 지향 프로그래밍(AOP)를 손쉽게 통합할 수 있습니다.

## 3. ApplicationContext 구현체

스프링은 다양한 ApplicationContext 구현체를 제공하며, 각각 특정 용도나 환경에 최적화되어 있습니다.

### 3.1 ClassPathXmlApplicationContext

클래스패스에 있는 XML 파일에서 빈 정의를 로드합니다.

```java
ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
```

### 3.2 FileSystemXmlApplicationContext

파일 시스템의 XML 파일에서 빈 정의를 로드합니다.

```java
ApplicationContext context = new FileSystemXmlApplicationContext("/config/applicationContext.xml");
```

### 3.3 AnnotationConfigApplicationContext

자바 클래스에서 애너테이션을 기반으로 빈 정의를 로드합니다.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

### 3.4 GenericWebApplicationContext

웹 애플리케이션에서 사용하기 위한 범용 ApplicationContext입니다.

```java
GenericWebApplicationContext context = new GenericWebApplicationContext();
context.setServletContext(servletContext);
context.refresh();
```

### 3.5 AnnotationConfigServletWebServerApplicationContext

스프링 부트에서 사용하는 웹 서버용 ApplicationContext로, 내장 서버를 시작하고 웹 환경의 빈을 관리합니다.

```java
// Spring Boot에서 자동으로 생성됨
ApplicationContext context = SpringApplication.run(MyApplication.class, args);
```

## 4. ApplicationContext의 계층 구조

ApplicationContext는 다음과 같은 계층 구조를 가질 수 있습니다:

### 4.1 부모-자식 컨텍스트

ApplicationContext는 계층 구조를 가질 수 있으며, 자식 컨텍스트는 부모 컨텍스트의 빈을 참조할 수 있습니다.

```java
// 부모 컨텍스트 생성
ApplicationContext parentContext = new ClassPathXmlApplicationContext("parent-context.xml");

// 자식 컨텍스트 생성
ClassPathXmlApplicationContext childContext = new ClassPathXmlApplicationContext(
    new String[] {"child-context.xml"}, parentContext);
```

### 4.2 웹 애플리케이션의 계층 구조

웹 애플리케이션에서는 일반적으로 다음과 같은 계층 구조를 가집니다:

1. **Root ApplicationContext**: 서비스, 레포지토리, 데이터 소스 등 공통 인프라스트럭처 빈
2. **DispatcherServlet ApplicationContext**: 컨트롤러, 뷰 리졸버 등 웹 관련 빈

```java
// web.xml 설정 예시
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>

<listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:dispatcher-servlet.xml</param-value>
    </init-param>
</servlet>
```

## 5. ApplicationContext의 내부 동작 원리

ApplicationContext는 크게 다음과 같은 단계를 거쳐 초기화됩니다.

### 5.1 초기화 과정

1. **빈 정의 로딩**: XML, 애너테이션, 자바 설정 등에서 빈 정의 정보를 읽어옵니다.
2. **빈 정의 등록**: 읽어온 빈 정의를 내부 레지스트리에 등록합니다.
3. **BeanFactoryPostProcessor 적용**: 빈 팩토리를 수정할 수 있는 후처리기를 실행합니다.
4. **빈 인스턴스 생성**: 등록된 빈 정의에 따라 빈 인스턴스를 생성합니다.
5. **의존성 주입**: 빈 간의 의존 관계를 설정합니다.
6. **BeanPostProcessor 적용**: 빈 후처리기를 통해 빈을 추가 가공합니다.
7. **초기화 콜백 실행**: 빈의 초기화 메서드를 호출합니다.
8. **애플리케이션 이벤트 발행**: 컨텍스트 시작 완료 이벤트를 발행합니다.

다음은 이러한 과정을 그림으로 나타낸 것입니다:

```
  ┌─────────────────────────────────────────────────────┐
  │                ApplicationContext                    │
  │                                                      │
  │ ┌───────────────────────────────────────────────┐   │
  │ │              Bean Definitions                  │   │
  │ └───────────────────────────────────────────────┘   │
  │                      │                               │
  │                      ▼                               │
  │ ┌───────────────────────────────────────────────┐   │
  │ │         BeanFactoryPostProcessors             │   │
  │ └───────────────────────────────────────────────┘   │
  │                      │                               │
  │                      ▼                               │
  │ ┌───────────────────────────────────────────────┐   │
  │ │              Bean Instances                    │   │
  │ └───────────────────────────────────────────────┘   │
  │                      │                               │
  │                      ▼                               │
  │ ┌───────────────────────────────────────────────┐   │
  │ │            BeanPostProcessors                  │   │
  │ └───────────────────────────────────────────────┘   │
  │                      │                               │
  │                      ▼                               │
  │ ┌───────────────────────────────────────────────┐   │
  │ │            Initialization Callbacks            │   │
  │ └───────────────────────────────────────────────┘   │
  │                      │                               │
  │                      ▼                               │
  │ ┌───────────────────────────────────────────────┐   │
  │ │           Ready-to-use Application             │   │
  │ └───────────────────────────────────────────────┘   │
  └─────────────────────────────────────────────────────┘
```

### 5.2 빈 검색 메커니즘

ApplicationContext는 빈을 다양한 방식으로 검색할 수 있습니다:

1. **이름으로 검색**:
   ```java
   UserService userService = (UserService) context.getBean("userService");
   ```

2. **타입으로 검색**:
   ```java
   UserService userService = context.getBean(UserService.class);
   ```

3. **이름과 타입으로 검색**:
   ```java
   UserService userService = context.getBean("userService", UserService.class);
   ```

4. **특정 타입의 모든 빈 검색**:
   ```java
   Map<String, UserService> userServices = context.getBeansOfType(UserService.class);
   ```

### 5.3 빈 생성 전략

ApplicationContext는 다음과 같은 방식으로 빈을 생성합니다:

1. **기본 생성자**: 매개변수가 없는 생성자를 사용하여 인스턴스 생성
2. **팩토리 메서드**: 정적 또는 인스턴스 팩토리 메서드를 사용하여 인스턴스 생성
3. **생성자 주입**: 생성자를 통해 의존성을 주입하면서 인스턴스 생성

```java
// 기본 생성자
@Component
public class DefaultUserService {
    // 기본 생성자 사용
}

// 팩토리 메서드
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}

// 생성자 주입
@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

## 6. ApplicationContext의 주요 기능

### 6.1 이벤트 발행과 구독

ApplicationContext는 이벤트 발행-구독 메커니즘을 제공합니다.

**이벤트 정의**:
```java
public class UserCreatedEvent extends ApplicationEvent {
    private final User user;
    
    public UserCreatedEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
}
```

**이벤트 발행**:
```java
@Service
public class UserService {
    private final ApplicationEventPublisher eventPublisher;
    
    @Autowired
    public UserService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    public User createUser(String username) {
        User user = new User(username);
        // 사용자 저장 로직
        
        // 이벤트 발행
        eventPublisher.publishEvent(new UserCreatedEvent(this, user));
        
        return user;
    }
}
```

**이벤트 구독**:
```java
@Component
public class UserEventListener {
    
    @EventListener
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        System.out.println("사용자 생성됨: " + event.getUser().getUsername());
        // 이벤트 처리 로직
    }
}
```

### 6.2 국제화(i18n) 지원

ApplicationContext는 메시지 소스를 통한 국제화를 지원합니다.

**메시지 소스 설정**:
```java
@Configuration
public class AppConfig {
    
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
```

**메시지 파일 (messages_ko.properties)**:
```
greeting=안녕하세요, {0}님!
```

**메시지 파일 (messages_en.properties)**:
```
greeting=Hello, {0}!
```

**메시지 사용**:
```java
@Controller
public class GreetingController {
    
    private final MessageSource messageSource;
    
    @Autowired
    public GreetingController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @GetMapping("/greet")
    public String greet(Model model, Locale locale) {
        String message = messageSource.getMessage("greeting", new Object[]{"스프링"}, locale);
        model.addAttribute("greeting", message);
        return "greeting";
    }
}
```

### 6.3 프로파일(Profiles) 지원

ApplicationContext는 프로파일을 통해 환경별 구성을 지원합니다.

**프로파일 설정**:
```java
@Configuration
public class DataSourceConfig {
    
    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        // 개발 환경용 데이터 소스 구성
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
    
    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        // 운영 환경용 데이터 소스 구성
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://prod-server:3306/mydb");
        dataSource.setUsername("prod-user");
        dataSource.setPassword("prod-password");
        return dataSource;
    }
}
```

**프로파일 활성화**:
```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MyApplication.class);
        app.setAdditionalProfiles("dev");
        app.run(args);
    }
}
```

### 6.4 환경 추상화(Environment)

ApplicationContext는 환경 추상화를 통해 다양한 소스의 속성을 통합 관리합니다.

**환경 속성 사용**:
```java
@Component
public class DatabaseConfig {
    
    private final Environment env;
    
    @Autowired
    public DatabaseConfig(Environment env) {
        this.env = env;
    }
    
    @PostConstruct
    public void init() {
        String url = env.getProperty("database.url");
        String username = env.getProperty("database.username");
        String password = env.getProperty("database.password");
        
        System.out.println("Database URL: " + url);
        System.out.println("Database Username: " + username);
    }
}
```

**속성 소스 등록**:
```java
@Configuration
@PropertySource("classpath:db.properties")
public class AppConfig {
    // 설정 코드
}
```

## 7. ApplicationContext와 디자인 패턴

ApplicationContext는 여러 디자인 패턴을 활용하여 구현되어 있습니다.

### 7.1 팩토리 패턴

ApplicationContext는 빈을 생성하고 관리하는 팩토리 역할을 합니다. 클라이언트 코드는 구체적인 빈 생성 방법을 알 필요 없이 컨텍스트에서 빈을 요청하기만 하면 됩니다.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
UserService userService = context.getBean(UserService.class); // 팩토리 패턴
```

### 7.2 싱글턴 패턴

기본적으로 ApplicationContext는 빈을 싱글턴으로 관리합니다. 즉, 하나의 빈 정의에 대해 하나의 인스턴스만 생성하고 공유합니다.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
UserService service1 = context.getBean(UserService.class);
UserService service2 = context.getBean(UserService.class);
System.out.println(service1 == service2); // true (같은 인스턴스)
```

### 7.3 옵저버 패턴

ApplicationContext의 이벤트 발행-구독 메커니즘은 옵저버 패턴을 기반으로 구현되어 있습니다. 이벤트 발행자는 이벤트를 발행하기만 하고, 구독자에 대한 직접적인 참조 없이도 이벤트를 전달할 수 있습니다.

```java
// 이벤트 발행 (Subject)
eventPublisher.publishEvent(new UserCreatedEvent(this, user));

// 이벤트 구독 (Observer)
@EventListener
public void handleUserCreatedEvent(UserCreatedEvent event) {
    // 이벤트 처리
}
```

### 7.4 템플릿 메서드 패턴

ApplicationContext의 초기화 과정은 템플릿 메서드 패턴을 따릅니다. AbstractApplicationContext 클래스의 refresh() 메서드는 컨텍스트 초기화의 전체 흐름을 정의하고, 구체적인 단계는 하위 클래스에서 구현합니다.

```java
// AbstractApplicationContext의 refresh() 메서드 (템플릿 메서드)
public void refresh() throws BeansException, IllegalStateException {
    synchronized (this.startupShutdownMonitor) {
        prepareRefresh();
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        prepareBeanFactory(beanFactory);
        // ... 다른 초기화 단계들
    }
}
```

## 8. 스프링 부트에서의 ApplicationContext

스프링 부트는 ApplicationContext의 초기화와 구성을 자동화합니다.

### 8.1 자동 구성(Auto-configuration)

스프링 부트는 `@EnableAutoConfiguration` 어노테이션을 통해 클래스패스, 속성 설정 등을 기반으로 ApplicationContext를 자동으로 구성합니다.

```java
@SpringBootApplication // @EnableAutoConfiguration 포함
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### 8.2 내장 웹 서버

스프링 부트는 내장 웹 서버를 시작하고 웹 애플리케이션 컨텍스트를 구성합니다.

```java
// 스프링 부트 애플리케이션 시작
ConfigurableApplicationContext context = SpringApplication.run(MyApplication.class, args);

// 내장 웹 서버 관련 빈 확인
WebServer webServer = context.getBean(WebServerApplicationContext.class).getWebServer();
System.out.println("웹 서버 포트: " + webServer.getPort());
```

### 8.3 조건부 빈 등록

스프링 부트는 `@Conditional` 어노테이션을 통해 조건에 따라 빈을 등록합니다.

```java
@Configuration
public class DataSourceConfig {
    
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "mysql")
    public DataSource mysqlDataSource() {
        // MySQL 데이터 소스 구성
        return new MysqlDataSource();
    }
    
    @Bean
    @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "postgresql")
    public DataSource postgresqlDataSource() {
        // PostgreSQL 데이터 소스 구성
        return new PGSimpleDataSource();
    }
}
```

## 9. ApplicationContext 관련 문제 해결

### 9.1 빈 정의 충돌

빈 이름 충돌이 발생할 경우 다음과 같이 해결할 수 있습니다:

```java
@Configuration
public class AppConfig {
    
    @Bean
    @Primary // 동일한 타입의 여러 빈 중 우선 사용
    public UserService userService() {
        return new UserServiceImpl();
    }
    
    @Bean
    @Qualifier("legacyUserService") // 특정 이름으로 빈 식별
    public UserService legacyUserService() {
        return new LegacyUserServiceImpl();
    }
}

// 사용 시
@Autowired
@Qualifier("legacyUserService")
private UserService userService;
```

### 9.2 순환 참조 감지

스프링은 순환 참조를 감지하고 예외를 발생시킵니다. 해결 방법은 다음과 같습니다:

```java
// 해결 방법 1: Setter 주입 사용
@Service
public class ServiceA {
    
    private ServiceB serviceB;
    
    @Autowired
    public void setServiceB(ServiceB serviceB) {
        this.serviceB = serviceB;
    }
}

// 해결 방법 2: @Lazy 사용
@Service
public class ServiceA {
    
    private final ServiceB serviceB;
    
    @Autowired
    public ServiceA(@Lazy ServiceB serviceB) {
        this.serviceB = serviceB;
    }
}
```

### 9.3 빈 초기화 실패

빈 초기화에 실패한 경우 다음과 같이 대체 빈을 제공할 수 있습니다:

```java
@Configuration
public class AppConfig {
    
    @Bean
    public DataSource primaryDataSource() {
        try {
            // 주 데이터 소스 구성
            return createPrimaryDataSource();
        } catch (Exception e) {
            logger.warn("주 데이터 소스 초기화 실패, 대체 데이터 소스 사용", e);
            return fallbackDataSource();
        }
    }
    
    private DataSource fallbackDataSource() {
        // 대체 데이터 소스 구성
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
```