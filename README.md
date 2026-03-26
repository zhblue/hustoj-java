# HUSTOJ Java - Servlet/JSP Reimplementation

A complete Java Servlet + JSP reimplementation of the HUSTOJ Online Judge system.

## Project Structure

```
hustoj-java/
├── pom.xml                          # Maven configuration
├── src/main/
│   ├── java/com/hustoj/
│   │   ├── db/DB.java               # HikariCP connection pool
│   │   ├── entity/                  # JPA-like entity classes
│   │   │   ├── User.java
│   │   │   ├── Problem.java
│   │   │   ├── Solution.java
│   │   │   ├── Contest.java
│   │   │   ├── ContestProblem.java
│   │   │   ├── News.java
│   │   │   ├── Mail.java
│   │   │   ├── Topic.java
│   │   │   ├── Reply.java
│   │   │   ├── Privilege.java
│   │   │   ├── LoginLog.java
│   │   │   ├── Balloon.java
│   │   │   ├── Printer.java
│   │   │   ├── ShareCode.java
│   │   │   ├── Sim.java
│   │   │   ├── Online.java
│   │   │   ├── SourceCode.java
│   │   │   ├── CompileInfo.java
│   │   │   ├── RuntimeInfo.java
│   │   │   └── CustomInput.java
│   │   ├── servlet/                 # Front-end servlets
│   │   │   ├── BaseServlet.java     # Base class with utilities
│   │   │   ├── LoginServlet.java
│   │   │   ├── LogoutServlet.java
│   │   │   ├── RegisterServlet.java
│   │   │   ├── LostPasswordServlet.java
│   │   │   ├── UserInfoServlet.java
│   │   │   ├── ModifyPasswordServlet.java
│   │   │   ├── ProblemSetServlet.java
│   │   │   ├── ProblemServlet.java
│   │   │   ├── SubmitServlet.java
│   │   │   ├── StatusServlet.java
│   │   │   ├── ShowSourceServlet.java
│   │   │   ├── ReinfoServlet.java
│   │   │   ├── CeInfoServlet.java
│   │   │   ├── ContestListServlet.java
│   │   │   ├── ContestServlet.java
│   │   │   ├── ContestRankServlet.java
│   │   │   ├── ContestRankOIServlet.java
│   │   │   ├── ContestStatisticsServlet.java
│   │   │   ├── DiscussServlet.java
│   │   │   ├── ThreadServlet.java
│   │   │   ├── MailServlet.java
│   │   │   ├── NewsServlet.java
│   │   │   ├── RanklistServlet.java
│   │   │   ├── BalloonServlet.java
│   │   │   ├── PrinterServlet.java
│   │   │   ├── CategoryServlet.java
│   │   │   ├── OnlineServlet.java
│   │   │   ├── ShareCodeServlet.java
│   │   │   ├── ShareCodeListServlet.java
│   │   │   ├── ExportAcCodeServlet.java
│   │   │   ├── ExportContestCodeServlet.java
│   │   │   ├── CompareSourceServlet.java
│   │   │   ├── GroupTotalServlet.java
│   │   │   └── GroupStatisticsServlet.java
│   │   ├── servlet/admin/           # Admin servlets
│   │   │   ├── RejudgeServlet.java
│   │   │   ├── UserManageServlet.java
│   │   │   ├── UserImportServlet.java
│   │   │   ├── ProblemAddServlet.java
│   │   │   ├── ProblemEditServlet.java
│   │   │   ├── ProblemDelServlet.java
│   │   │   ├── ProblemImportServlet.java
│   │   │   ├── ProblemExportServlet.java
│   │   │   ├── ProblemJudgeServlet.java
│   │   │   ├── ProblemCopyServlet.java
│   │   │   ├── ProblemChangeIdServlet.java
│   │   │   ├── ProblemDfChangeServlet.java
│   │   │   ├── ContestAddServlet.java
│   │   │   ├── ContestEditServlet.java
│   │   │   ├── ContestPrChangeServlet.java
│   │   │   ├── ContestDfChangeServlet.java
│   │   │   ├── NewsAddServlet.java
│   │   │   ├── NewsEditServlet.java
│   │   │   ├── NewsListServlet.java
│   │   │   ├── PrivilegeAddServlet.java
│   │   │   ├── PrivilegeDeleteServlet.java
│   │   │   ├── PrivilegeListServlet.java
│   │   │   ├── UpdateDbServlet.java
│   │   │   ├── BackupServlet.java
│   │   │   ├── SuspectListServlet.java
│   │   │   ├── SolutionStatisticsServlet.java
│   │   │   ├── WatchServlet.java
│   │   │   ├── TeamGenerateServlet.java
│   │   │   └── RanklistExportServlet.java
│   │   ├── service/                 # Business logic
│   │   │   ├── UserService.java
│   │   │   ├── ProblemService.java
│   │   │   ├── SolutionService.java
│   │   │   ├── ContestService.java
│   │   │   ├── NewsService.java
│   │   │   ├── MailService.java
│   │   │   └── TopicService.java
│   │   ├── filter/                  # Servlet filters
│   │   │   ├── AuthFilter.java      # Login/authentication
│   │   │   ├── CsrfFilter.java      # CSRF protection
│   │   │   └── EncodingFilter.java  # UTF-8 encoding
│   │   └── util/                    # Utilities
│   │       ├── DB.java              # Config & constants
│   │       ├── IpUtil.java          # IP address utilities
│   │       ├── SecurityUtil.java    # Security (XSS, password, etc.)
│   │       ├── JudgeUtil.java      # UDP judge triggering
│   │       └── FormatUtil.java      # Formatting utilities
│   ├── resources/
│   │   ├── oj.properties            # System configuration
│   │   ├── i18n.properties          # English translations
│   │   └── i18n_cn.properties       # Chinese translations
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── web.xml               # URL mappings & filters
│       │   └── views/               # JSP views
│       │       ├── header.jsp
│       │       ├── footer.jsp
│       │       ├── error.jsp
│       │       ├── user/            # User-related views
│       │       ├── problem/         # Problem views
│       │       ├── contest/        # Contest views
│       │       ├── status/         # Status views
│       │       ├── discuss/        # Discussion views
│       │       ├── mail/           # Mail views
│       │       ├── news/           # News views
│       │       └── admin/           # Admin views
│       ├── index.jsp
│       ├── ranklist.jsp
│       ├── balloon.jsp
│       ├── printer.jsp
│       ├── online.jsp
│       ├── sharecodepage.jsp
│       ├── sharecodelist.jsp
│       ├── category.jsp
│       ├── group_total.jsp
│       ├── group_statistics.jsp
│       ├── comparesource.jsp
│       └── css/hoj.css
└── src/test/
    ├── java/com/hustoj/service/
    │   ├── UserServiceTest.java
    │   ├── ProblemServiceTest.java
    │   ├── SolutionServiceTest.java
    │   └── ContestServiceTest.java
    └── resources/
        └── schema.sql              # H2 test schema
```

## Build & Run

```bash
# Install Maven (if not available)
wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
tar xzf apache-maven-3.9.6-bin.tar.gz
export PATH=apache-maven-3.9.6/bin:$PATH

# Build
cd /home/zhblue/hustoj-java
mvn clean compile

# Run tests
mvn test

# Package WAR
mvn package

# Deploy to Tomcat/Jetty
# Copy target/hustoj-java.war to your servlet container
```

## Configuration

Edit `src/main/resources/oj.properties`:

```properties
db.host=localhost
db.name=jol
db.user=root
db.password=root
oj.name=HUSTOJ
oj.udp=true
oj.udp.server=127.0.0.1
oj.udp.port=1536
```

## Features

- **P0**: Core framework (Maven, HikariCP, Filters, BaseServlet)
- **P1**: User system (login, register, password, privileges)
- **P2**: Problem system (CRUD, import/export, FPS XML)
- **P3**: Contest system (ACM/OI modes, ranking, lockboard)
- **P4**: Submission system (UDP judge triggering, status tracking)
- **P5**: Discussion system (topics, replies, BBCode)
- **P6**: Mail system (internal messaging)
- **P7**: News/Announcements
- **P8**: Ranklist (global, group statistics)
- **P9**: Balloon & Printer services
- **PA**: Auxiliary (category, online, share code, export)
- **PB**: Full admin panel

## Key Implementation Notes

1. **UDP Judge Triggering**: `JudgeUtil.triggerJudge(solutionId)` sends the solution_id to 127.0.0.1:1536
2. **Session Keys**: `HUSTOJ_user_id`, `HUSTOJ_administrator`, `HUSTOJ_source_browser`, etc.
3. **IP Detection**: X-Forwarded-For > X-Real-IP > REMOTE_ADDR
4. **CSRF Protection**: Token stored in session, validated on POST
5. **Password**: SHA1+salt hashing compatible with PHP version
6. **Language Support**: Accept-Language detection, i18n.properties

## Database

Connects to existing HUSTOJ MySQL database (jol). Schema from original PHP system.

## License

Same as HUSTOJ (GPL v2)
