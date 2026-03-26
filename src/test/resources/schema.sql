-- H2 Test Schema for HUSTOJ
CREATE TABLE users (
    user_id VARCHAR(48) PRIMARY KEY,
    email VARCHAR(100),
    submit INT DEFAULT 0,
    solved INT DEFAULT 0,
    defunct CHAR(1) DEFAULT 'N',
    ip VARCHAR(46) DEFAULT '',
    accesstime TIMESTAMP,
    volume INT DEFAULT 1,
    language INT DEFAULT 1,
    password VARCHAR(32),
    reg_time TIMESTAMP,
    expiry_date DATE DEFAULT '2099-01-01',
    nick VARCHAR(20) DEFAULT '',
    school VARCHAR(20) DEFAULT '',
    group_name VARCHAR(16) DEFAULT '',
    activecode VARCHAR(16) DEFAULT '',
    starred INT DEFAULT 0
);

CREATE TABLE problem (
    problem_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL DEFAULT '',
    description TEXT,
    input TEXT,
    output TEXT,
    sample_input TEXT,
    sample_output TEXT,
    spj CHAR(1) DEFAULT '0',
    hint TEXT,
    source VARCHAR(100),
    in_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    time_limit DECIMAL(10,3) DEFAULT 0,
    memory_limit INT DEFAULT 0,
    defunct CHAR(1) DEFAULT 'N',
    accepted INT DEFAULT 0,
    submit INT DEFAULT 0,
    solved INT DEFAULT 0,
    remote_oj VARCHAR(16),
    remote_id VARCHAR(32)
);

CREATE TABLE solution (
    solution_id INT AUTO_INCREMENT PRIMARY KEY,
    problem_id INT DEFAULT 0,
    user_id CHAR(48) NOT NULL DEFAULT '',
    nick CHAR(20) DEFAULT '',
    time INT DEFAULT 0,
    memory INT DEFAULT 0,
    in_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    result SMALLINT DEFAULT 0,
    language INT DEFAULT 0,
    ip CHAR(46) NOT NULL DEFAULT '',
    contest_id INT DEFAULT 0,
    valid TINYINT DEFAULT 1,
    num TINYINT DEFAULT -1,
    code_length INT DEFAULT 0,
    judgetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    pass_rate DECIMAL(4,3) DEFAULT 0,
    first_time TINYINT DEFAULT 0,
    lint_error INT DEFAULT 0,
    judge CHAR(16) DEFAULT 'LOCAL',
    remote_oj CHAR(16) DEFAULT '',
    remote_id CHAR(32) DEFAULT ''
);

CREATE TABLE source_code (
    solution_id INT PRIMARY KEY,
    source TEXT NOT NULL
);

CREATE TABLE contest (
    contest_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    defunct CHAR(1) DEFAULT 'N',
    description TEXT,
    private TINYINT DEFAULT 0,
    langmask INT DEFAULT 0,
    password CHAR(16) DEFAULT '',
    contest_type SMALLINT DEFAULT 0,
    subnet VARCHAR(255) DEFAULT '',
    user_id VARCHAR(48) DEFAULT 'admin'
);

CREATE TABLE contest_problem (
    problem_id INT NOT NULL DEFAULT 0,
    contest_id INT,
    title CHAR(200) DEFAULT '',
    num INT NOT NULL DEFAULT 0,
    c_accepted INT DEFAULT 0,
    c_submit INT DEFAULT 0
);

CREATE TABLE news (
    news_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(48) DEFAULT '',
    title VARCHAR(200) DEFAULT '',
    content TEXT,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    importance TINYINT DEFAULT 0,
    menu INT DEFAULT 0,
    defunct CHAR(1) DEFAULT 'N'
);

CREATE TABLE mail (
    mail_id INT AUTO_INCREMENT PRIMARY KEY,
    to_user VARCHAR(48) DEFAULT '',
    from_user VARCHAR(48) DEFAULT '',
    title VARCHAR(200) DEFAULT '',
    content TEXT,
    new_mail TINYINT DEFAULT 1,
    reply INT DEFAULT 0,
    in_date TIMESTAMP,
    defunct CHAR(1) DEFAULT 'N'
);

CREATE TABLE reply (
    rid INT AUTO_INCREMENT PRIMARY KEY,
    author_id VARCHAR(48) NOT NULL,
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    content TEXT NOT NULL,
    topic_id INT NOT NULL,
    status INT DEFAULT 0,
    ip VARCHAR(46) NOT NULL
);

CREATE TABLE topic (
    tid INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(60) NOT NULL,
    status INT DEFAULT 0,
    top_level INT DEFAULT 0,
    cid INT,
    pid INT NOT NULL,
    author_id VARCHAR(48) NOT NULL
);

CREATE TABLE privilege (
    user_id CHAR(48) NOT NULL DEFAULT '',
    rightstr CHAR(30) NOT NULL DEFAULT '',
    valuestr CHAR(11) DEFAULT 'true',
    defunct CHAR(1) DEFAULT 'N'
);

CREATE TABLE loginlog (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(48) DEFAULT '',
    password VARCHAR(40),
    ip VARCHAR(46),
    time TIMESTAMP
);

CREATE TABLE compileinfo (
    solution_id INT NOT NULL DEFAULT 0,
    error TEXT
);

CREATE TABLE runtimeinfo (
    solution_id INT NOT NULL DEFAULT 0,
    error TEXT
);

CREATE TABLE sim (
    s_id INT NOT NULL,
    sim_s_id INT,
    sim INT
);

CREATE TABLE custominput (
    solution_id INT NOT NULL DEFAULT 0,
    input_text TEXT
);

CREATE TABLE balloon (
    balloon_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(48) NOT NULL,
    sid INT NOT NULL,
    cid INT NOT NULL,
    pid INT NOT NULL,
    status SMALLINT DEFAULT 0
);

CREATE TABLE printer (
    printer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id CHAR(48) NOT NULL,
    in_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status SMALLINT DEFAULT 0,
    worktime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    printer CHAR(16) DEFAULT 'LOCAL',
    content TEXT NOT NULL
);

CREATE TABLE share_code (
    share_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(48),
    title VARCHAR(32),
    share_code TEXT,
    language VARCHAR(32),
    share_time TIMESTAMP
);

CREATE TABLE online (
    hash VARCHAR(32) PRIMARY KEY,
    ip VARCHAR(46) NOT NULL DEFAULT '',
    ua VARCHAR(255) NOT NULL DEFAULT '',
    refer VARCHAR(4096),
    lastmove INT NOT NULL,
    firsttime INT,
    uri VARCHAR(255)
);
